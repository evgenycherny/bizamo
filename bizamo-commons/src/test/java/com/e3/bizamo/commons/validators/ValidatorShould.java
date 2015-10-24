package com.e3.bizamo.commons.validators;

import org.junit.Test;

import com.e3.bizamo.commons.exceptions.ApplicationException;

public class ValidatorShould {
	@Test(expected=ApplicationException.class)
	public void throwWhenValueLowerThenRange() {
		Validator.InRange("SomeParam", 1, 2, 10);
	}
	@Test(expected=ApplicationException.class)
	public void throwWhenValueHigherThenRange() {
		Validator.InRange("SomeParam", 11, 2, 10);
	}
	@Test
	public void notThrowWhenValueInRange() {
		Validator.InRange("SomeParam", 2, 1, 10);
	}
	@Test(expected=NullPointerException.class)
	public void throwWhenValueIsNull() {
		Validator.NotNull("SomeParam", null);
	}
	@Test
	public void notThrowWhenValueNotNull() {
		Validator.NotNull("SomeParam", "");
	}
	@Test
	public void constructoreCoverageStub() {
		new Validator();
	}
}
