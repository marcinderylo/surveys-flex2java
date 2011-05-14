package org.adaptiveplatform.codegenerator.controllers;

import java.util.Collection;

import org.adaptiveplatform.codegenerator.ClassGenerationRequest;

public interface CodeGenerationController {

	Collection<ClassGenerationRequest> generate(Class<?> classToGenerate);
}
