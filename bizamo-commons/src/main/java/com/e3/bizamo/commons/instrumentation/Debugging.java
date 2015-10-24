package com.e3.bizamo.commons.instrumentation;

import java.lang.management.ManagementFactory;

public class Debugging {
	Debugging() {}
	public static boolean isDebugMode() {
		return ManagementFactory.getRuntimeMXBean().getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;
	}
}
