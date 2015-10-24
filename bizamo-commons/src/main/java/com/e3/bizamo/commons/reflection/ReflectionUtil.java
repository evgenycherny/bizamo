package com.e3.bizamo.commons.reflection;


public class ReflectionUtil {
	public static Class<?> forName(String typeName) {
		try {
			return Class.forName(typeName);
		} catch (ClassNotFoundException e) {
			throw new ClassNotFoundApplicationException(e);
		}
	}
	public static Class<?> forName(String typeName, ClassLoader loader) {
		try {
			return Class.forName(typeName, true, loader);
		} catch (ClassNotFoundException e) {
			throw new ClassNotFoundApplicationException(e);
		}
	}
}
