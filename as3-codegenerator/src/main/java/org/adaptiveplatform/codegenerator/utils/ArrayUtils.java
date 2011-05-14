package org.adaptiveplatform.codegenerator.utils;

public final class ArrayUtils {

	private ArrayUtils() {
	}

	public static String join(String[] elements, String glue) {
		StringBuilder string = new StringBuilder();
		boolean first = true;
		for (String element : elements) {
			if (!first) {
				string.append(glue);
			}
			first = false;
			string.append(element);
		}
		return string.toString();
	}
}
