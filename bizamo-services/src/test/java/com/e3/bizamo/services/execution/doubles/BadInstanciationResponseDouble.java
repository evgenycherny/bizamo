package com.e3.bizamo.services.execution.doubles;

public class BadInstanciationResponseDouble {
	@SuppressWarnings("unused")
	private String s1;
	private int i1;
	@SuppressWarnings("unused")
	private static boolean stub = Throw();
	
	public BadInstanciationResponseDouble() {
	}
	private static boolean Throw() {
		throw new RuntimeException();
	}
	public String getS1() {
		throw new RuntimeException();
	}
	public void setS1(String s1) {
		this.s1 = s1;
	}
	public int getI1() {
		return i1;
	}
	public void setI1(int i1) {
		this.i1 = i1;
	}
}
