package org.adaptiveplatform.codegenerator.acceptance;

import java.io.Serializable;

import org.adaptiveplatform.codegenerator.api.RemoteObject;

@RemoteObject
class SampleCommand implements Serializable {
	private String name;
	private String description;

	@SampleConstraint(min = 5)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}