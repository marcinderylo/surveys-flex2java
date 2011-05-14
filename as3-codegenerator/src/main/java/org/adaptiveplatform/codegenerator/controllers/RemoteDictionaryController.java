package org.adaptiveplatform.codegenerator.controllers;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.adaptiveplatform.codegenerator.ClassGenerationRequest;
import org.adaptiveplatform.codegenerator.model.RemoteDictionaryModel;
import org.adaptiveplatform.codegenerator.model.TypeModel;
import org.adaptiveplatform.codegenerator.model.TypeModelFactory;

public class RemoteDictionaryController implements CodeGenerationController {

	private final TypeModelFactory types;

	public RemoteDictionaryController(TypeModelFactory types) {
		this.types = types;
	}

	@Override
	public Collection<ClassGenerationRequest> generate(Class<?> classToGenerate) {
		RemoteDictionaryModel model = createRemoteDictionary(classToGenerate);
		ClassGenerationRequest dictionary = new ClassGenerationRequest(model.getPackage(), model.getName(),
				"remoteDictionary", model, false);
		return Collections.singleton(dictionary);
	}

	private RemoteDictionaryModel createRemoteDictionary(Class<?> clas) {
		if (!isEnum(clas)) {
			throw new RuntimeException("RemoteDictionaryModel can only be created from an enum");
		}
		TypeModel type = types.createType(clas);
		List<String> constants = new ArrayList<String>();
		for (Field field : clas.getDeclaredFields()) {
			if (field.isEnumConstant()) {
				constants.add(field.getName());
			}
		}
		return new RemoteDictionaryModel(type, constants);
	}

	private boolean isEnum(Class<?> clas) {
		return Enum.class.isAssignableFrom(clas);
	}
}
