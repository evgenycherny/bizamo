package com.e3.bizamo.services.module;

import org.junit.Test;
import org.mockito.Mockito;

import com.e3.bizamo.services.module.Initializer;

public class InitalizerShould extends Mockito {
	@Test
	public void constructorCoverageStub() {
		new Initializer();
	}
	@Test
	public void executionCoverageStub() {
		new Initializer().initModule();
	}


}
