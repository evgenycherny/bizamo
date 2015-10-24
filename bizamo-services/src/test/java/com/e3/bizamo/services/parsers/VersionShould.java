package com.e3.bizamo.services.parsers;

import junit.framework.TestCase;

import org.junit.Test;
import org.mockito.Mockito;

import com.e3.bizamo.services.parsers.Version;
import com.e3.bizamo.services.parsers.VersionFormatException;

public class VersionShould extends Mockito {
	@Test
	public void constructoreCoverageStub() {
		new Version();
	}

	@Test
	public void convertToString() {
		Version version = new Version(1,2,3);
		TestCase.assertEquals("1.2.3", version.toString());
	}
	@Test
	public void compare() {
		TestCase.assertTrue(new Version(1,2,3).compareTo(new Version(1,2,4))==-1);
		TestCase.assertTrue(new Version(1,2,3).compareTo(new Version(1,3,3))==-1);
		TestCase.assertTrue(new Version(1,2,3).compareTo(new Version(2,2,3))==-1);
		TestCase.assertTrue(new Version(1,2,3).compareTo(new Version(1,2,3))==0);
		TestCase.assertTrue(new Version(1,2,3).equals(new Version(1,2,3)));
		TestCase.assertTrue(!new Version(1,2,3).equals(new Version(1,2,4)));
	}
	@Test
	public void parse() {
		TestCase.assertEquals(new Version(1,0,0),Version.parse("1"));
		TestCase.assertEquals(new Version(1,2,0),Version.parse("1.2"));
		TestCase.assertEquals(new Version(1,2,3),Version.parse("1.2.3"));
	}
	@Test(expected=VersionFormatException.class)
	public void throwOnBadParserInput() {
		Version.parse("fake");
	}


}
