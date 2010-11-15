package org.adaptiveplatform.codegenerator.utils;

import java.util.HashSet;
import java.util.Set;

public class As3VariableNamesGenerator {

	private Set<String> restricted = new HashSet<String>();
	private Set<String> usedNames = new HashSet<String>();

	public String nameForParameter(String keyword) {
		String candidate = StringUtils.decapitalize(keyword);
		while (restricted.contains(candidate) || usedNames.contains(candidate)) {
			candidate = increaseTrailingNumber(candidate);
		}
		usedNames.add(candidate);
		return candidate;
	}

	private String increaseTrailingNumber(String string) {
		int i = string.length() - 1;
		for (; i >= 0; i--) {
			if (!isDigit(string.charAt(i))) {
				break;
			}
		}
		String numberString = string.substring(i + 1);
		int number = 2;
		if (!numberString.isEmpty()) {
			number = Integer.parseInt(numberString) + 1;
		}
		return string.substring(0, i + 1) + number;
	}

	private boolean isDigit(char character) {
		return character >= '0' && character <= '9';
	}

	public void reset() {
		usedNames.clear();
	}

	public void addRestrictedName(String string) {
		restricted.add(string);
	}
}
