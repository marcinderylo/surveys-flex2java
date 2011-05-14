package org.adaptiveplatform.codegenerator;

/**
 * Value object containing specifics of an artifact to be exported.
 * 
 * @author Rafal Jamroz
 */
public class ClassGenerationRequest {
	private final String pckage;
	private final String className;

	private final String templateName;
	private final Object model;

	private final boolean persistent;

	public ClassGenerationRequest(String pckage, String className, String templateName, Object model, boolean persistent) {
		this.pckage = pckage;
		this.className = className;
		this.templateName = templateName;
		this.model = model;
		this.persistent = persistent;
	}

	public String getPackage() {
		return pckage;
	}

	public String getClassName() {
		return className;
	}

	public String getTemplateName() {
		return templateName;
	}

	public Object getModel() {
		return model;
	}

	public boolean isPersistent() {
		return persistent;
	}
}
