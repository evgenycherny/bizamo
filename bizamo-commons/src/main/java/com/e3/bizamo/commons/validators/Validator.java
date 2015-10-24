package com.e3.bizamo.commons.validators;

import com.e3.bizamo.commons.exceptions.ApplicationException;

public class Validator {
	public static void NotNull(String param, Object value) {
		if (value==null) 
			throw new NullPointerException(String.format("Parameter [%s] of method can't be null", param));
	}
	public static void InRange(String param, int value, int min, int max) {
		if (value<min || value>max)
			throw new ApplicationException(String.format("Value [%d] of parameter [%s] should be in range [%d,%d]", value, param, min, max));
	}
}
