package org.adaptiveplatform.codegenerator;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.adaptiveplatform.codegenerator.api.RemoteDictionary;
import org.adaptiveplatform.codegenerator.api.RemoteObject;
import org.adaptiveplatform.codegenerator.api.RemoteService;
import org.adaptiveplatform.codegenerator.controllers.CodeGenerationController;
import org.adaptiveplatform.codegenerator.controllers.RemoteDictionaryController;
import org.adaptiveplatform.codegenerator.controllers.RemoteObjectController;
import org.adaptiveplatform.codegenerator.controllers.RemoteServiceController;
import org.adaptiveplatform.codegenerator.export.Exporter;
import org.adaptiveplatform.codegenerator.export.FileExporter;
import org.adaptiveplatform.codegenerator.export.FreemarkerTemplatingEngine;
import org.adaptiveplatform.codegenerator.model.AnnotationModelFactory;
import org.adaptiveplatform.codegenerator.model.TypeModelFactory;

import com.google.common.collect.Sets;

public class Java2FlexCodeGeneratorConfiguration {

	private Map<Class<? extends Annotation>, CodeGenerationController> controllerMappings = new HashMap<Class<? extends Annotation>, CodeGenerationController>();
	private Map<String, String> translatedPackages = new HashMap<String, String>();
	private Set<String> constraintAnnotationsPackages;
	private FreemarkerTemplatingEngine templatingEngine;
	private Exporter exporter;

	public Java2FlexCodeGeneratorConfiguration() {
		constraintAnnotationsPackages = Sets.newHashSet("javax.validation.constraints");
	}

	public Java2FlexCodeGenerator build() {
		TypeModelFactory types = createTypeModelFactory();

		addCustomController(RemoteObject.class, createRemoteObjectController(types));
		addCustomController(RemoteService.class, createRemoteServiceController(types));
		addCustomController(RemoteDictionary.class, new RemoteDictionaryController(types));

		return new Java2FlexCodeGenerator(controllerMappings, templatingEngine, exporter);
	}

	private TypeModelFactory createTypeModelFactory() {
		ClasspathTranslator translate = new ClasspathTranslator();
		translate.addTranslatedClasses(ActionScript3Helper.javaToAS3TypesTranslations());
		translate.addTranslatedPackages(translatedPackages);
		TypeModelFactory types = new TypeModelFactory(translate);
		return types;
	}

	private RemoteObjectController createRemoteObjectController(TypeModelFactory types) {
		AnnotationModelFactory annotations = new AnnotationModelFactory(constraintAnnotationsPackages);
		return new RemoteObjectController(types, annotations);
	}

	private RemoteServiceController createRemoteServiceController(TypeModelFactory types) {
		As3VariableNamesProvider variableNamesProvider = new As3VariableNamesProvider();
		variableNamesProvider.addRestrictedNames(ActionScript3Helper.as3Keywords());
		return new RemoteServiceController(types, variableNamesProvider);
	}

	public void translatePackages(Map<String, String> translatedPackages) {
		this.translatedPackages.putAll(translatedPackages);
	}

	public void addConstrainAnnotationsPackage(String pckage) {
		constraintAnnotationsPackages.add(pckage);
	}

	public void configureFreemarkerTemplatingEngine(String templatesClasspathPrefix, String templatesClasspathPostfix) {
		templatingEngine = new FreemarkerTemplatingEngine(Java2FlexCodeGenerator.class, templatesClasspathPrefix,
				templatesClasspathPostfix);
	}

	public void configureFileExporter(File persistentOutputFolder, File transientOutputFolder) {
		exporter = new FileExporter(persistentOutputFolder, transientOutputFolder);
	}

	public void setExporter(Exporter exporter) {
		this.exporter = exporter;
	}

	public void addCustomController(Class<? extends Annotation> annotation, CodeGenerationController controller) {
		controllerMappings.put(annotation, controller);
	}
}
