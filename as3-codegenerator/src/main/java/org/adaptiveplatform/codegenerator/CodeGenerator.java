package org.adaptiveplatform.codegenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.adaptiveplatform.codegenerator.model.ModelFactory;
import org.adaptiveplatform.codegenerator.utils.As3VariableNamesGenerator;
import org.codehaus.plexus.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.flexmojos.generator.api.GenerationException;
import org.sonatype.flexmojos.generator.api.GenerationRequest;
import org.sonatype.flexmojos.generator.api.Generator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Adapter class for adaptive-platform code generator utility.
 * 
 * @author Rafal Jamroz, PL
 */
@Component(role = org.sonatype.flexmojos.generator.api.Generator.class, hint = "adaptiveCodeGenerator")
public class CodeGenerator implements Generator {
	private static final String TRANSLATE_PACKAGES_EXTRA_OPTION = "translatePackages";

	private Logger logger = LoggerFactory.getLogger(CodeGenerator.class);

	private FileExporterFactory exporters;
	private ModelFactory models;
	private ClasspathTranslator translate;
	private As3VariableNamesGenerator nameGenerator;
	private Configuration templatingEngine;

	public CodeGenerator() {
		initializeClasspathNamesTranslator();
		initializeAs3ParametersNameGenerator();

		models = new ModelFactory(translate, nameGenerator);
		exporters = new FileExporterFactory(models);

		templatingEngine = new Configuration();
		templatingEngine.setClassForTemplateLoading(CodeGenerator.class, "/");
	}

	private void initializeClasspathNamesTranslator() {
		translate = new ClasspathTranslator();
		translate.addTranslatedClass(List.class, "mx.collections.ArrayCollection");
		translate.addTranslatedClass(Set.class, "mx.collections.ArrayCollection");
		translate.addTranslatedClass(Date.class, "Date");
		translate.addTranslatedClass(Long.class, "Number");
		translate.addTranslatedClass(Long.TYPE.getClass(), "Number");
		translate.addTranslatedClass(Byte.class, "int");
		translate.addTranslatedClass(Byte.TYPE.getClass(), "int");
		translate.addTranslatedClass(Short.class, "int");
		translate.addTranslatedClass(Short.TYPE.getClass(), "int");
		translate.addTranslatedClass(Integer.class, "int");
		translate.addTranslatedClass(Integer.TYPE.getClass(), "int");
		translate.addTranslatedClass(Float.class, "Number");
		translate.addTranslatedClass(Float.TYPE.getClass(), "Number");
		translate.addTranslatedClass(Double.class, "Number");
		translate.addTranslatedClass(Double.TYPE.getClass(), "Number");
		translate.addTranslatedClass(Boolean.class, "Boolean");
		translate.addTranslatedClass(Boolean.TYPE.getClass(), "Boolean");
	}

	private void initializeAs3ParametersNameGenerator() {
		nameGenerator = new As3VariableNamesGenerator();
		nameGenerator.addRestrictedName("get");
		nameGenerator.addRestrictedName("set");
		nameGenerator.addRestrictedName("int");
	}

	public void generate(GenerationRequest request) throws GenerationException {
		handleTranslatedPackages(request.getExtraOptions().get(TRANSLATE_PACKAGES_EXTRA_OPTION));

		List<ArtifactExporter> exportable = new ArrayList<ArtifactExporter>();
		for (String classString : request.getClasses().keySet()) {
			try {
				Class<?> clas = request.getClassLoader().loadClass(classString);
				if (exporters.exportable(clas)) {
					exportable.add(exporters.create(clas));
				}
			} catch (ClassNotFoundException e) {
				logger.error("couldnt load the class to export (name=" + classString + ")", e);
			}
		}

		for (ArtifactExporter exporter : exportable) {
			List<ExportRequest> exportRequests = exporter.export();

			for (ExportRequest exportRequest : exportRequests) {
				File baseDirectory;
				if (exportRequest.isPersistent()) {
					baseDirectory = request.getPersistentOutputFolder();
				} else {
					baseDirectory = request.getTransientOutputFolder();
				}
				File targetFile = createTargetFile(baseDirectory, exportRequest.getOutputDirectory(), exportRequest
						.getOutputFile());
				if (!exportRequest.isPersistent() || !targetFile.exists()) {
					writeFile(exportRequest, targetFile);
				}
			}
		}
	}

	private void handleTranslatedPackages(String string) throws GenerationException {
		if (string == null || string.length() == 0) {
			return;
		}
		Properties properties = loadProperties(string);
		for (Map.Entry<Object, Object> entry : properties.entrySet()) {
			Object key = entry.getKey();
			Object value = entry.getValue();
			if (key != null && value != null) {
				translate.addTranslatedPackage(key.toString().trim(), value.toString().trim());
			}
		}
	}

	private Properties loadProperties(String string) throws GenerationException {
		Properties properties = new Properties();
		try {
			properties.load(new StringReader(string));
			return properties;
		} catch (IOException cause) {
			throw new GenerationException("cannot parse extra option: " + TRANSLATE_PACKAGES_EXTRA_OPTION, cause);
		}
	}

	private void writeFile(ExportRequest exportRequest, File file) {
		Writer output = null;
		try {
			output = new FileWriter(file);
			Template template = templatingEngine.getTemplate(exportRequest.getTemplateName());
			template.process(exportRequest.getModel(), output);
		} catch (IOException e) {
			logger.error("error while writting generated artifact", e);
		} catch (TemplateException e) {
			logger.error("templating engine error", e);
		} finally {
			try {
				if (output != null) {
					output.close();
				}
			} catch (IOException ignore) {
			}
		}
	}

	private File createTargetFile(File rootDirectory, String relativeDirectory, String outputFile) {
		File directory = new File(rootDirectory, relativeDirectory);
		directory.mkdirs();
		return new File(directory, outputFile);
	}
}
