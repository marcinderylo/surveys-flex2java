package org.adaptiveplatform.codegenerator.model;

import java.util.List;

public class RemoteDictionaryModel {
	private TypeModel type;
	private List<String> constants;

	public RemoteDictionaryModel(TypeModel type, List<String> constants) {
		this.type = type;
		this.constants = constants;
	}

	public String getOriginalPackage() {
		return type.getOriginalPackage();
	}

	public String getOriginalName() {
		return type.getOriginalName();
	}

	public String getPackage() {
		return type.getPackage();
	}

	public String getName() {
		return type.getName();
	}

	public List<String> getConstants() {
		return constants;
	}
}
