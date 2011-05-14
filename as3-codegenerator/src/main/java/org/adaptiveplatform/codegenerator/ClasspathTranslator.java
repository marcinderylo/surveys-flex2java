package org.adaptiveplatform.codegenerator;

import java.util.HashMap;
import java.util.Map;

/**
 * Translates the names or packages of classes while converting from Java to
 * AS3.
 * 
 * @author Rafal Jamroz
 */
public class ClasspathTranslator {

	Map<String, String> translatedClasses = new HashMap<String, String>();
	Map<String, String> translatedPackages = new HashMap<String, String>();

	public String getTranslatedPackage(Class<?> type) {
		String translatedClass = translatedClasses.get(type.getName());
		if (translatedClass != null) {
			return getPackage(translatedClass);
		}
		if (type.getPackage() != null) {
			String translatedPackage = translatedPackages.get(type.getPackage().getName());
			if (translatedPackage != null) {
				return translatedPackage;
			}
		}
		return getPackage(type.getName());
	}

	private String getPackage(String fullClassName) {
		int lastDot = fullClassName.lastIndexOf(".");
		if (lastDot >= 0) {
			return fullClassName.substring(0, lastDot);
		}
		return null; // primitive type or defalut package
	}

	public String getTranslatedName(Class<?> type) {
		String translatedClass = translatedClasses.get(type.getName());
		if (translatedClass != null) {
			return getClassName(translatedClass);
		}
		return getClassName(type.getName());
	}

	private String getClassName(String fullClassName) {
		int lastDot = fullClassName.lastIndexOf(".");
		if (lastDot >= 0) {
			return fullClassName.substring(lastDot + 1);
		}
		return fullClassName;// primitive type or defalut package
	}

	public void addTranslatedClasses(Map<Class<?>, String> javaToAS3TypesTranslations) {
		for (Map.Entry<Class<?>, String> translation : javaToAS3TypesTranslations.entrySet()) {
			addTranslatedClass(translation.getKey(), translation.getValue());
		}
	}

	public void addTranslatedClass(Class<?> clas, String newName) {
		addTranslatedClass(clas.getName(), newName);
	}

	public void addTranslatedClass(String className, String newName) {
		translatedClasses.put(className, newName);
	}

	public void addTranslatedPackages(Map<String, String> translatedPackages) {
		for (Map.Entry<String, String> translation : translatedPackages.entrySet()) {
			addTranslatedPackage(translation.getKey(), translation.getValue());
		}
	}

	public void addTranslatedPackage(String pkg, String newPkg) {
		translatedPackages.put(pkg, newPkg);
	}
}
