package com.e3.bizamo.commons.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Cacheable {
	Cacheable() {}
	private static Map<Integer, Object> cache = new HashMap<Integer, Object>();
	
	
	public static <T> T get(Supplier<T> supplier) {
		Integer hash = new HashCodeBuilder().append(supplier.getClass()).toHashCode();
		return getFromCacheOrExecute(supplier, hash);
	}
	public static <T> T get(Supplier<T> supplier, Object... args) {
		Integer hash = new HashCodeBuilder().append(args).append(supplier.getClass()).toHashCode();
		return getFromCacheOrExecute(supplier, hash);
	}
	@SuppressWarnings("unchecked")
	private static <T> T getFromCacheOrExecute(Supplier<T> supplier,
			Integer hash) {
		if (cache.containsKey(hash)) {
			return (T)cache.get(hash);
		}
		T result = supplier.get();
		cache.put(hash, result);
		return result;
	}

	public static void invalidate() {
		cache.clear();
	}
}