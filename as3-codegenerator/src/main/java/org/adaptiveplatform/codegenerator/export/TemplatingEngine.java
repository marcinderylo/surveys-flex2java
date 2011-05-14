package org.adaptiveplatform.codegenerator.export;

public interface TemplatingEngine {

	String execute(String templateName, Object model);

}
