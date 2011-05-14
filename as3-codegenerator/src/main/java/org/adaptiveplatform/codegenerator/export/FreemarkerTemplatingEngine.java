package org.adaptiveplatform.codegenerator.export;

import java.io.StringWriter;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreemarkerTemplatingEngine implements TemplatingEngine {

	private Configuration freemarkerConfiguration;

	private String pathPostfix;

	public FreemarkerTemplatingEngine(Class<?> classForTemplateLoading, String pathPrefix, String pathPostfix) {
		freemarkerConfiguration = new Configuration();
		freemarkerConfiguration.setClassForTemplateLoading(classForTemplateLoading, pathPrefix);
		this.pathPostfix = pathPostfix;
	}

	@Override
	public String execute(String templateName, Object model) {
		StringWriter writer = new StringWriter();
		try {
			Template template = freemarkerConfiguration.getTemplate(templateName + pathPostfix);
			template.process(model, writer);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return writer.toString();
	}
}
