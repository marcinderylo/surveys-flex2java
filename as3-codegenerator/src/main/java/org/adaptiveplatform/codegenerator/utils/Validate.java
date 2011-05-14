package org.adaptiveplatform.codegenerator.utils;

public class Validate {

	public static void notNull(Object... objects) {
		for (Object object : objects) {
			org.apache.commons.lang.Validate.notNull(object);
		}
	}
}
