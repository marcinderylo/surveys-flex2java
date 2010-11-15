package org.adaptiveplatform.codegenerator.model;

/**
 * Model representing a java method parameter.
 * 
 * @author Rafal Jamroz
 */
public class ParameterModel {
	private String name;
	private TypeModel type;

	public ParameterModel(String name, TypeModel type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public TypeModel getType() {
		return type;
	}
}
