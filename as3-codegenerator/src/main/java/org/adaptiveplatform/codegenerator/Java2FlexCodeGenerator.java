package org.adaptiveplatform.codegenerator;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Map;

import org.adaptiveplatform.codegenerator.controllers.CodeGenerationController;
import org.adaptiveplatform.codegenerator.export.Exporter;
import org.adaptiveplatform.codegenerator.export.TemplatingEngine;
import org.adaptiveplatform.codegenerator.utils.StringUtils;
import org.adaptiveplatform.codegenerator.utils.Validate;

import com.google.common.collect.Sets;

/**
 * Adapter class for adaptive-platform code generator utility.
 * 
 * @author Rafal Jamroz, PL
 */
public class Java2FlexCodeGenerator {

	private Map<Class<? extends Annotation>, CodeGenerationController> annotationsMappings;
	private TemplatingEngine templatingEngine;
	private Exporter exporter;

	public Java2FlexCodeGenerator(Map<Class<? extends Annotation>, CodeGenerationController> annotationsMappings,
			TemplatingEngine templatingEngine, Exporter exporter) {
		Validate.notNull(annotationsMappings, templatingEngine, exporter);
		this.annotationsMappings = annotationsMappings;
		this.templatingEngine = templatingEngine;
		this.exporter = exporter;
	}

	public void generate(Collection<Class<?>> classesToGenerate) {
		Collection<ClassGenerationRequest> generationRequests = generateClasses(classesToGenerate);
		Collection<OutputArtifact> outputArtifacts = generateOutputArtifacts(generationRequests);
		exportArtifacts(outputArtifacts);
	}

	private Collection<ClassGenerationRequest> generateClasses(Collection<Class<?>> classesToGenerate) {
		Collection<ClassGenerationRequest> generationRequests = Sets.newHashSet();
		for (Class<?> classToGenerate : classesToGenerate) {
			CodeGenerationController controller = selectController(classToGenerate);
			if (controller != null) {
				generationRequests.addAll(controller.generate(classToGenerate));
			}
		}
		return generationRequests;
	}

	private CodeGenerationController selectController(Class<?> classToGenerate) {
		for (Annotation annotation : classToGenerate.getAnnotations()) {
			CodeGenerationController controller = annotationsMappings.get(annotation.annotationType());
			if (controller != null) {
				return controller;
			}
		}
		return null;
	}

	private Collection<OutputArtifact> generateOutputArtifacts(Collection<ClassGenerationRequest> generationRequests) {
		Collection<OutputArtifact> outputArtifacts = Sets.newHashSet();
		for (ClassGenerationRequest generationRequest : generationRequests) {
			outputArtifacts.add(createOutputArtifact(generationRequest));
		}
		return outputArtifacts;
	}

	private OutputArtifact createOutputArtifact(ClassGenerationRequest generationRequest) {
		OutputArtifact outputArtifact = new OutputArtifact();
		String fileContent = templatingEngine
				.execute(generationRequest.getTemplateName(), generationRequest.getModel());
		outputArtifact.setContent(fileContent);
		outputArtifact.setDirectory(StringUtils.packageToPath(generationRequest.getPackage()));
		outputArtifact.setFileName(generationRequest.getClassName() + ".as");
		outputArtifact.setPersistent(generationRequest.isPersistent());
		return outputArtifact;
	}

	private void exportArtifacts(Collection<OutputArtifact> outputArtifacts) {
		for (OutputArtifact outputArtifact : outputArtifacts) {
			exporter.export(outputArtifact);
		}
	}
}
