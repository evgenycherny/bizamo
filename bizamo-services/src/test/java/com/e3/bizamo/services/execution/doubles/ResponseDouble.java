package com.e3.bizamo.services.execution.doubles;

public class ResponseDouble {
	private String s1;
	private int i1;
	public String getS1() {
		return s1;
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
	@Override
	public boolean equals(Object other) {
		ResponseDouble otherResponse = (ResponseDouble)other;
		return getI1()==otherResponse.getI1() && this.getS1().equals(otherResponse.getS1());
	}
}
