package org.adaptiveplatform.codegenerator.model;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

import java.util.HashSet;
import java.util.Set;

import org.adaptiveplatform.codegenerator.As3VariableNamesProvider;
import org.junit.Before;
import org.junit.Test;

public class As3VariableNamesGeneratorTest {
	private As3VariableNamesProvider generator;

	@Before
	public void beforeMethod() throws Exception {
		generator = new As3VariableNamesProvider();
	}

	@Test
	public void shouldGenerateOneNameForType() throws Exception {
		// when
		String string = generator.nameForParameter("String");
		// then
		assertEquals(string, "string");
	}

	@Test
	public void shouldGenerateTwoNamesForTheSameType() throws Exception {
		// when
		String string = generator.nameForParameter("String");
		String string2 = generator.nameForParameter("String");
		// then
		assertFalse(string.equals(string2));
	}

	@Test
	public void shouldGenerateNameAvoidingRestrictions() throws Exception {
		// given
		generator.addRestrictedName("string");
		// when
		String string2 = generator.nameForParameter("String");
		// then
		assertFalse("string".equals(string2));
	}

	@Test
	public void shouldGenerateSameNameAfterReseting() throws Exception {
		// when
		String string = generator.nameForParameter("String");
		generator.reset();
		String string2 = generator.nameForParameter("String");
		// then
		assertEquals(string, string2);
	}

	@Test
	public void shouldGenerateDifferentNameManyTimes() throws Exception {
		// given
		Set<String> generated = new HashSet<String>();
		// when
		for (int i = 0; i < 1000; i++) {
			String string = generator.nameForParameter("String");
			generated.add(string);
		}
		// then
		assertEquals(generated.size(), 1000);
	}
}
