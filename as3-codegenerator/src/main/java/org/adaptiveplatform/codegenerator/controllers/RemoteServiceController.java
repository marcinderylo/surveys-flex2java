package org.adaptiveplatform.codegenerator.controllers;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.adaptiveplatform.codegenerator.As3VariableNamesProvider;
import org.adaptiveplatform.codegenerator.ClassGenerationRequest;
import org.adaptiveplatform.codegenerator.api.RemoteExclude;
import org.adaptiveplatform.codegenerator.model.MethodModel;
import org.adaptiveplatform.codegenerator.model.ParameterModel;
import org.adaptiveplatform.codegenerator.model.RemoteServiceModel;
import org.adaptiveplatform.codegenerator.model.TypeModel;
import org.adaptiveplatform.codegenerator.model.TypeModelFactory;

public class RemoteServiceController implements CodeGenerationController {
	private final TypeModelFactory types;
	private final As3VariableNamesProvider names;

	public RemoteServiceController(TypeModelFactory types, As3VariableNamesProvider names) {
		this.types = types;
		this.names = names;
	}

	@Override
	public Collection<ClassGenerationRequest> generate(Class<?> classToGenerate) {
		RemoteServiceModel model = createRemoteService(classToGenerate);
		ClassGenerationRequest service = new ClassGenerationRequest(model.getPackage(), model.getName(),
				"remoteService", model, false);
		ClassGenerationRequest remoteService = new ClassGenerationRequest(model.getPackage(), "Remote"
				+ model.getName(), "remoteServiceImpl", model, false);
		return Arrays.asList(service, remoteService);
	}

	private RemoteServiceModel createRemoteService(Class<?> clas) {
		if (!clas.isInterface()) {
			throw new RuntimeException("RemoteServiceModel can only be created from an interface");
		}
		TypeModel typeModel = types.createType(clas);
		List<MethodModel> methods = new ArrayList<MethodModel>();
		for (Method method : clas.getDeclaredMethods()) {
			if (method.getAnnotation(RemoteExclude.class) == null) {
				methods.add(createMethod(method));
			}
		}
		return new RemoteServiceModel(typeModel, methods);
	}

	private MethodModel createMethod(Method method) {
		List<ParameterModel> paramters = new ArrayList<ParameterModel>();
		names.reset();
		for (Type paramType : method.getGenericParameterTypes()) {
			TypeModel type = types.createType(paramType);
			String name = names.nameForParameter(type.getName());
			paramters.add(createParameter(paramType, name));
		}
		return new MethodModel(method.getName(), types.createType(method.getGenericReturnType()), paramters);
	}

	private ParameterModel createParameter(Type type, String name) {
		return new ParameterModel(name, types.createType(type));
	}

}
