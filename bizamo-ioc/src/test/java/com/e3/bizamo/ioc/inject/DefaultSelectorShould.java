package com.e3.bizamo.ioc.inject;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.e3.bizamo.ioc.inject.doubles.BindingDouble;

public class DefaultSelectorShould {
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void returnAllBindingsForSelectMany() {
		DefaultSelector selector = new DefaultSelector();
		List<IBinding> bindingList = new ArrayList<IBinding>();
		bindingList.add(new BindingDouble());
		bindingList.add(new BindingDouble());
		bindingList.add(new BindingDouble());
		List<IBinding> selection = selector.selectMany(bindingList);
		TestCase.assertEquals(3,  selection.size());
	}
	
	@Test
	public void returnNullForSelectSingleOnEmptyList() {
		DefaultSelector selector = new DefaultSelector();
		List<IBinding> bindingList = new ArrayList<IBinding>();
		TestCase.assertNull(selector.selectSingle(bindingList));
	}
	@Test
	public void returnNullForSelectSingleWithMultipleBindings() {
		DefaultSelector selector = new DefaultSelector();
		List<IBinding> bindingList = new ArrayList<IBinding>();
		bindingList.add(new BindingDouble("1"));
		bindingList.add(new BindingDouble("2"));
		bindingList.add(new BindingDouble("3"));
		TestCase.assertNull(selector.selectSingle(bindingList));
	}
	
	@Test
	public void returnBindingForSelectSingleWithSingleBindings() {
		DefaultSelector selector = new DefaultSelector();
		List<IBinding> bindingList = new ArrayList<IBinding>();
		bindingList.add(new BindingDouble("1"));
		TestCase.assertEquals("1",selector.selectSingle(bindingList).getRegistrationKey());
	}
}
