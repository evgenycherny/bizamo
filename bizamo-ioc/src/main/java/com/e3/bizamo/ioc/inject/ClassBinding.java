package com.e3.bizamo.ioc.inject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.List;

import com.e3.bizamo.ioc.inject.annotations.Inject;
import com.e3.bizamo.ioc.inject.annotations.Key;
import com.e3.bizamo.ioc.inject.annotations.Selector;

public class ClassBinding implements IBinding {
	private Class<?> bindingClass;
	private LifeCycle lifeCycle = LifeCycle.TRANSIENT;
	private Object instance = null;

	public ClassBinding(Class<?> bindingClass) {
		this.setBindingClass(bindingClass);
	}
	public Class<?> getBindingClass() {
		return bindingClass;
	}

	void setBindingClass(Class<?> bindingClass) {
		this.bindingClass = bindingClass;
	}
	public ClassBinding setLifeCycle(LifeCycle lifeCycle) {
		this.lifeCycle = lifeCycle;
		return this;
	}
	@Override
	public String getRegistrationKey() {
		return getCanonicalName();
	}
	@Override
	public Class<?> getImplementationClass() {
		return getBindingClass();
	}
	@Override
	public String getCanonicalName() {
		return this.getBindingClass().getCanonicalName();
	}
	@Override
	public Object instanciate(Resolver resolver) {
		if (lifeCycle.equals(LifeCycle.SINGLETON) && instance!=null) {
			return instance;
		}
		try {
			Constructor<?> selectedCtr = findInjectionConstructor();
			if (selectedCtr==null)selectedCtr = findConstructorWithLongestParameterList();
			Object[] params = resolveConstructorParameters(resolver, selectedCtr);
			Object newInstance = selectedCtr.newInstance(params);
			if (lifeCycle.equals(LifeCycle.SINGLETON)) {
				this.instance = newInstance;
			}
			return newInstance;
		} catch (Throwable e) {
			throw new ResolutionException(e);
		}
	}
	private Constructor<?> findInjectionConstructor() {
		Constructor<?>[] ctrs = this.getBindingClass().getConstructors();
		for(Constructor<?> ctr: ctrs) {
			if (ctr.isAnnotationPresent(Inject.class)) {
				return ctr;
			}
		}
		return null;
	}
	private Constructor<?> findConstructorWithLongestParameterList() {
		Constructor<?>[] ctrs = this.getBindingClass().getConstructors();
		int maxParamCount = 0;
		Constructor<?> selectedCtr = null;
		for(Constructor<?> ctr: ctrs) {
			if (ctr.getParameterCount()>=maxParamCount) {
				maxParamCount=ctr.getParameterCount();
				selectedCtr = ctr;
			}
		}
		return selectedCtr;
	}
	private Object[] resolveConstructorParameters(Resolver resolver, Constructor<?> selectedCtr) {
		Class<?>[] paramTypes = selectedCtr.getParameterTypes();
		Object[] params = new Object[paramTypes.length];
		Annotation[][] parametersAnnotations = selectedCtr.getParameterAnnotations();
		for (int i=0; i<paramTypes.length; i++ ) {
			if (parametersAnnotations[i].length>0) {
				for (Annotation anno: parametersAnnotations[i]) {
					if (anno instanceof Key) {
						params[i] = resolver.resolve(((Key) anno).value(), paramTypes[i]);
					}
					else 
					if (anno instanceof Selector) {
						ISelector selector = createSelectorInstance(anno);
						params[i] = resolver.resolve(paramTypes[i], selector);
					}
				}
			}
			else if (paramTypes[i].isArray()) {
				Class<?> componentType = paramTypes[i].getComponentType();
				List<?> list = resolver.resolveAll(componentType);
				params[i] = Array.newInstance(componentType, list.size());
				
				for (int j=0; j<list.size(); j++) {
					Array.set(params[i], j, list.get(j));
				}
				
			}
			else params[i] = resolver.resolve(paramTypes[i]);
		}
		return params;
	}
	private ISelector createSelectorInstance(Annotation anno) {
		try {
			return (ISelector)((Selector) anno).value().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new ResolutionException(String.format("Unable to instanciate selector [%s]", ((Selector) anno).value().getName()),e);
		}
	}

}
