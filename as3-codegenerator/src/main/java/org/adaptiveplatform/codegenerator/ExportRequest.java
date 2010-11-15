package org.adaptiveplatform.codegenerator;

/**
 * Value object containing specifics of an artifact to be exported.
 * 
 * @author Rafal Jamroz
 */
public class ExportRequest {
	/**
	 * Relative directory where the file should be written.
	 */
	private final String outputDirectory;
	/**
	 * File name.
	 */
	private final String outputFile;
	/**
	 * Template used for exporting.
	 */
	private final String templateName;
	/**
	 * Data model used in the template.
	 */
	private final Object model;
	/**
	 * Persistent artifact will only be generated if it doesn't already exist.
	 */
	private final boolean persistent;

	public ExportRequest(String outputDirectory, String outputFile, String templateName, Object model,
			boolean persistent) {
		this.outputDirectory = outputDirectory;
		this.outputFile = outputFile;
		this.templateName = templateName;
		this.model = model;
		this.persistent = persistent;
	}

	public String getOutputDirectory() {
		return outputDirectory;
	}

	public String getOutputFile() {
		return outputFile;
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
