package com.e3.bizamo.ioc.module;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.bytecode.ClassFile;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.e3.bizamo.commons.reflection.ReflectionUtil;
import com.e3.bizamo.ioc.inject.IoC;
import com.e3.bizamo.ioc.inject.Resolver;
import com.e3.bizamo.ioc.inject.annotations.Export;

public class ModuleScanner {
	private static Logger logger = LogManager.getLogger();

	public ClassLoader scan(String classpath) {
		logger.debug(String.format("Scanning path [%s] for attachable modules",
				classpath));

		URLClassLoader loader = createClassLoader(classpath);

		URL[] urls = loader.getURLs();
		for (URL url : urls) {
			initializeModule(loader, url);
		}
		return loader;
	}

	private void initializeModule(URLClassLoader loader, URL url) {
		try {
			JarFile jar = new JarFile(url.getFile());
			logger.info(String.format("Scanning jar entries for [%s]", jar.getName()));
			findAndRunModuleInitializerAndExports(loader, jar);
			jar.close();
		} catch (IOException | ClassNotFoundException | InstantiationException
				| IllegalAccessException e) {
			logger.error(String.format("Failed to initialize module [%s]", url));
			logger.catching(Level.ERROR, e);
			return;
		}
	}

	private void findAndRunModuleInitializerAndExports(URLClassLoader loader,
			JarFile jar) throws IOException, ClassNotFoundException,
			InstantiationException, IllegalAccessException {

		Enumeration<JarEntry> entries = jar.entries();
		while (entries.hasMoreElements()) {
			JarEntry entry = entries.nextElement();
			if (entry.getName().endsWith(".class")) {
				CtClass ctClass = getClassInformation(jar, entry);
				checkEntryAndExecuteIfInitializerFound(loader, ctClass);
				checkAndRegisterExports(loader, ctClass);
			}

		}
	}

	private void checkEntryAndExecuteIfInitializerFound(URLClassLoader loader,
			CtClass ctClass) throws IOException, ClassNotFoundException,
			InstantiationException, IllegalAccessException {

		if (doesImplementModuleInitializer(ctClass)) {
			logger.info(String.format("Initializer %s was detected. Executing initializer", ctClass.getName()));
			runModuleInitializer(loader, ctClass.getClassFile().getName());
		}

	}

	private void checkAndRegisterExports(URLClassLoader loader, CtClass ctClass)
			throws IOException, ClassNotFoundException {
		Export anno = (Export) ctClass.getAnnotation(Export.class);
		if (anno != null) {
			registerExport(ctClass, anno, loader);
		}
	}

	private void registerExport(CtClass ctClass, Export anno, ClassLoader loader) {
		String implementationName = ctClass.getClassFile().getName();
		Class<?> serviceImpl = ReflectionUtil.forName(implementationName,
				loader);
		Class<?> serviceType = getServiceTypeFromExportAnnotation(anno, loader);

		Resolver resolver = IoC.getResolver();
		resolver.register(serviceType, serviceImpl);
		
		logger.info(String.format("Export: (%s,%s)",serviceType.getName(), serviceImpl.getName()));
	}

	private Class<?> getServiceTypeFromExportAnnotation(Export anno,
			ClassLoader loader) {
		try {
			String s = anno.toString();
			int startIdx = s.indexOf("(serviceType=")
					+ "(serviceType=".length();
			int endIdx = s.indexOf(".class)", startIdx);
			String serviceTypeName = s.substring(startIdx, endIdx);
			Class<?> serviceType = ReflectionUtil.forName(serviceTypeName,
					loader);
			return serviceType;
		} catch (Throwable e) {
			String s = anno.toString();
			int startIdx = s.indexOf("(serviceType=")
					+ "(serviceType=".length();
			int endIdx = s.indexOf(".class)", startIdx);
			String serviceTypeName = s.substring(startIdx, endIdx);
			Class<?> serviceType = ReflectionUtil.forName(serviceTypeName,
					loader);
			return serviceType;
		}
	}

	private boolean doesImplementModuleInitializer(CtClass ctClass) {
		String[] interfaceNames = getImplementedInterfaces(ctClass);

		for (String iface : interfaceNames) {
			if (iface.equals(IModuleInitializer.class.getName())) {
				return true;
			}
		}
		return false;
	}

	private String[] getImplementedInterfaces(CtClass ctClass) {
		ClassFile cf = ctClass.getClassFile2();
		String[] interfaceNames = cf.getInterfaces();
		return interfaceNames;
	}

	private CtClass getClassInformation(JarFile jar, JarEntry entry)
			throws IOException {
		ClassPool pool = ClassPool.getDefault();
		InputStream is = jar.getInputStream(entry);
		CtClass ctClass = pool.makeClassIfNew(is);
		return ctClass;

	}

	private void runModuleInitializer(URLClassLoader loader,
			String initializerName) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		@SuppressWarnings("unchecked")
		Class<IModuleInitializer> clazz = (Class<IModuleInitializer>) loader
				.loadClass(initializerName);

		try {
			IModuleInitializer initializer = clazz.newInstance();
			initializer.initModule();
		} catch (ClassCastException e) {
			logger.error(String
					.format("Specified initializer [%s] does not implement IModuleInitializer",
							initializerName));
			logger.catching(Level.ERROR, e);
			return;
		}
	}

	private URLClassLoader createClassLoader(String classpath) {
		String[] classpathLocations = classpath.split(";");

		List<URL> urls = new ArrayList<URL>();
		for (String location : classpathLocations) {
			urls.addAll(collectJarsFromClasspathLocation(location));
		}
		URLClassLoader loader = new URLClassLoader(urls.toArray(new URL[0]),
				IoC.class.getClassLoader());
		loader.setDefaultAssertionStatus(true);
		return loader;
	}

	private List<URL> collectJarsFromClasspathLocation(String location) {
		File dir = new File(location);
		if (!dir.exists() || !dir.isDirectory())
			return new ArrayList<URL>();

		List<URL> urls = collectJarsUrlsToList(dir);
		logScannedJars(urls);
		return urls;
	}

	private List<URL> collectJarsUrlsToList(File dir) {
		String[] libs = dir.list();
		List<URL> urls = new ArrayList<URL>();
		for (String lib : libs) {
			if (lib.endsWith(".jar")) {
				File file = new File(dir.getAbsolutePath() + "/" + lib);
				addFileToUrlList(urls, file);
			}
		}
		return urls;
	}

	private void addFileToUrlList(List<URL> urls, File file) {
		try {
			urls.add(file.toURI().toURL());
		} catch (MalformedURLException e) {
			// Never happens because we build URL from existing file that was
			// just scanned
		}
	}

	private void logScannedJars(List<URL> urls) {
		logger.info("Scanned jars are below:");
		for (URL url : urls) {
			logger.info(url);
		}
	}
}
