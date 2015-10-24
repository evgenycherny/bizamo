package com.e3.bizamo.ioc.inject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.e3.bizamo.commons.validators.Validator;

public class Resolver {
	protected Map<Class<?>, Map<String, IBinding>> map;

		
	Resolver() {
		map = new HashMap<Class<?>, Map<String, IBinding>>();
	}
	public void reset() {
		map = new HashMap<Class<?>, Map<String, IBinding>>();
	}
	
	public void register(Class<?> serviceType, IBinding binding) {
		register(binding.getCanonicalName(), serviceType, binding);
	}
	public void register(Class<?> serviceType, Class<?> implementationType) {
		register(implementationType.getCanonicalName(), serviceType, implementationType);
	}
	public void register(String key, Class<?> serviceType, Class<?> implementationType) {
		this.register(key, serviceType, new ClassBinding(implementationType));
	}
	public <T> void register(Class<T> serviceType, T registredInstance) {
		InstanceBinding binding = new InstanceBinding(registredInstance);
		register(serviceType, binding);
	}
	public <T> void register(String key, Class<T> serviceType, T registredInstance) {
		InstanceBinding binding = new InstanceBinding(registredInstance);
		register(key, serviceType, binding);
	}
	public void register(String key, Class<?> serviceType, IBinding binding) {
		Validator.NotNull("key", key);
		Validator.NotNull("serviceType", serviceType);
		Validator.NotNull("binding", binding);
		verifyImplementationImplementsService(serviceType, binding);

		Map<String, IBinding> keyMap = null;
		if (map.containsKey(serviceType)) {
			keyMap = map.get(serviceType);
		}
		else {
			keyMap = new HashMap<String, IBinding>();
			map.put(serviceType, keyMap);
		}
		
		keyMap.put(key, binding);
	}

	
	public <T> T resolve(Class<T> service) {
		return resolve(service, new DefaultSelector());
	}
	
	public <T> T resolve(Class<T> serviceType, ISelector selector) {
		return resolve(null, serviceType, selector);
	}
	
	public <T> T resolve(String key, Class<T> serviceType) {
		return resolve(key, serviceType, new DefaultSelector());
	}
	
	@SuppressWarnings("unchecked")
	public <T> T resolve(String key, Class<T> serviceType, ISelector selector) {
		Validator.NotNull("serviceType", serviceType);
		Validator.NotNull("selector", selector);
		
		IBinding binding = selectBinding(key, serviceType, selector);
		return (T)binding.instanciate(this);
	}
	public <T> Class<? extends T> getImplementationClass(String key, Class<T> serviceType) {
		return getImplementationClass(key, serviceType, new DefaultSelector());
	}
	public <T> Class<? extends T> getImplementationClass(String key, Class<T> serviceType, ISelector selector) {
		IBinding binding = selectBinding(key, serviceType, selector);
		
		@SuppressWarnings("unchecked")
		Class<? extends T> implementation = (Class<? extends T>) binding.getImplementationClass();
		return implementation;
	}
	private IBinding selectBinding(String key, Class<?> serviceType, ISelector selector) {
		List<IBinding> bindings = new ArrayList<IBinding>();
		if (key==null) bindings.addAll(getBindings(serviceType));
		else bindings.add(getBinding(key, serviceType));
		
		
		IBinding binding = selector.selectSingle(bindings);
		if (binding==null) throw new ResolutionException(
					String.format("No matching service found for [%s]", serviceType.getCanonicalName()));
		
		return binding;
	}
	
	
	public <T> List<T> resolveAll(Class<T> service) {
		return resolveAll(service, new DefaultSelector());
	}
	
	public <T> List<T> resolveAll(Class<T> serviceType, ISelector selector) {
		Validator.NotNull("service", serviceType);
		
		List<IBinding> bindings = getBindings(serviceType);			
		bindings = selector.selectMany(bindings);	
		return instanciateBindings(bindings);
	}

	@SuppressWarnings("unchecked")
	private <T> List<T> instanciateBindings(List<IBinding> bindings) {
		List<T> instances = new ArrayList<T>();
		for(IBinding binding:bindings) {
			instances.add((T) binding.instanciate(this));
		}
		return instances;
	}
	
	private void verifyImplementationImplementsService(Class<?> serviceType, IBinding binding) {
		if (!serviceType.isAssignableFrom(binding.getImplementationClass()))
			throw new RegistrationException(
					String.format("Class [%s] should implement [%s]", 
							binding.getCanonicalName(), 
							serviceType.getCanonicalName()));
	}

	protected IBinding getBinding(String key, Class<?> serviceType) {
		Map<String, IBinding> keyMap = getKeyMapByServiceType(serviceType);
		
		IBinding binding = keyMap.get(key);
		if (binding==null) throw new ResolutionException(
				String.format("Service [%s] is not registred under key [%s]", serviceType.getCanonicalName(), key));
		return binding;
	}


	protected List<IBinding> getBindings(Class<?> serviceType) {
		Map<String, IBinding> keyMap = getKeyMapByServiceType(serviceType);
		
		List<IBinding> bindings = new ArrayList<IBinding>();
		bindings.addAll(keyMap.values());
		return bindings;
	}
	private Map<String, IBinding> getKeyMapByServiceType(Class<?> serviceType) {
		Map<String, IBinding> keyMap = map.get(serviceType);
		if (keyMap==null) return new HashMap<String, IBinding>();
		return keyMap;
	}	
	
}
