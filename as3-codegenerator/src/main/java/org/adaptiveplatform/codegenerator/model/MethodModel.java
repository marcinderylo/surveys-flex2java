package org.adaptiveplatform.codegenerator.model;

import java.util.Collections;
import java.util.List;

/**
 * Model representing java method.
 * 
 * @author Rafal Jamroz
 */
public class MethodModel {
	private String name;
	private TypeModel returnType;
	private List<ParameterModel> parameters;
	private boolean hasGenericParameter;

	public MethodModel(String name, TypeModel returnType,
			List<ParameterModel> paramters) {
		this.name = name;
		this.returnType = returnType;
		this.parameters = Collections.unmodifiableList(paramters);
		for (ParameterModel param : paramters) {
			if (param.getType().isGeneric()) {
				hasGenericParameter = true;
				break;
			}
		}
	}

	public String getName() {
		return name;
	}

	public TypeModel getReturnType() {
		return returnType;
	}
	
	public boolean isHasGenericParameter() {
		return hasGenericParameter;
	}

	public List<ParameterModel> getParameters() {
		return parameters;
	}
}
