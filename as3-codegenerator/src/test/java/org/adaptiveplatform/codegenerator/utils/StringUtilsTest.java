package org.adaptiveplatform.codegenerator.utils;

import static org.junit.Assert.assertEquals;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

public class StringUtilsTest {

	@Test
	public void shouldDecapitalize() throws Exception {
		assertEquals("sample String", StringUtils.uncapitalize("Sample String"));
	}

	@Test
	public void shouldDecapitalizeCharacter() throws Exception {
		assertEquals("s", StringUtils.uncapitalize("S"));
	}

	@Test
	public void shouldDecapitalizeAllLowerLetters() throws Exception {
		assertEquals("abc", StringUtils.uncapitalize("abc"));
	}

	@Test
	public void shouldShouldDecapitalizeEmpty() throws Exception {
		assertEquals("", StringUtils.uncapitalize(""));
	}

	@Test
	public void shouldShouldDecapitalizeNull() throws Exception {
		assertEquals(null, StringUtils.uncapitalize(null));
	}
}
