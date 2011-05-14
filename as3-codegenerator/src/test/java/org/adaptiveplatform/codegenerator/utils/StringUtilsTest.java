package org.adaptiveplatform.codegenerator.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StringUtilsTest {

	@Test
	public void shouldDecapitalize() throws Exception {
		assertEquals("sample String", StringUtils.decapitalize("Sample String"));
	}

	@Test
	public void shouldDecapitalizeCharacter() throws Exception {
		assertEquals("s", StringUtils.decapitalize("S"));
	}

	@Test
	public void shouldDecapitalizeAllLowerLetters() throws Exception {
		assertEquals("abc", StringUtils.decapitalize("abc"));
	}

	@Test
	public void shouldShouldDecapitalizeEmpty() throws Exception {
		assertEquals("", StringUtils.decapitalize(""));
	}

	@Test
	public void shouldShouldDecapitalizeNull() throws Exception {
		assertEquals(null, StringUtils.decapitalize(null));
	}
}
