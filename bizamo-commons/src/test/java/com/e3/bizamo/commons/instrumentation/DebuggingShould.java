package com.e3.bizamo.commons.instrumentation;

import org.junit.Test;

public class DebuggingShould {
	@Test
	public void justRun() {
		Debugging.isDebugMode();
	}
	@Test
	public void constructoreCoverageStub() {
		new Debugging();
	}
}
