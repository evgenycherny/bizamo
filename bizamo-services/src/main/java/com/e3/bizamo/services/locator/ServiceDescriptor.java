package com.e3.bizamo.services.locator;

import com.e3.bizamo.services.parsers.Version;

public class ServiceDescriptor {
	private String name;
	private String servicePackage = "";
	private Version version;
	private String method = "POST";
	
	public ServiceDescriptor() {
		
	}
	public ServiceDescriptor(String name, Version version) {
		this("", name, version);
	}
	public ServiceDescriptor(String servicePackage, String name, Version version) {
		this.setName(name);
		this.setServicePackage(servicePackage);
		this.version = version;
	}
	public String getName() {
		return name;
	}
	public ServiceDescriptor setName(String name) {
		this.name = name.toLowerCase();
		return this;
	}
	public Version getVersion() {
		return version;
	}
	public ServiceDescriptor setVersion(Version version) {
		this.version = version;
		return this;
	}
	public String getServicePackage() {
		return servicePackage;
	}
	public ServiceDescriptor setServicePackage(String servicePackage) {
		this.servicePackage = servicePackage.toLowerCase();
		return this;
	}
	
	@Override 
	public String toString() {
		return String.format("[package:%s, service=%s, version=%s, method=%s]", servicePackage, name, version, method);
	}
	public String getMethod() {
		return method;
	}
	public ServiceDescriptor setMethod(String method) {
		this.method = method.toLowerCase();
		return this;
	}
	public String getRegistrationKey() {
		String key = "service:" + this.getServicePackage() + ":" +  this.getName() + ":" + this.getVersion() + ":" + this.getMethod();
		return key.toLowerCase();
	}
}
