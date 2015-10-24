package com.e3.bizamo.services.tomcat;

import java.util.Enumeration;

import javax.naming.CompositeName;

public class NameDouble extends CompositeName {
	private static final long serialVersionUID = 1L;
	public NameDouble(Enumeration<String> comps) {
		super(comps);
	}
}
