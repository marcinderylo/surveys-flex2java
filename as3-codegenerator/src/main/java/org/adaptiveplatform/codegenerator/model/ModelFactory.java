package org.adaptiveplatform.codegenerator.model;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adaptiveplatform.codegenerator.ClasspathTranslator;
import org.adaptiveplatform.codegenerator.api.RemoteExclude;
import org.adaptiveplatform.codegenerator.utils.As3VariableNamesGenerator;

public class ModelFactory {
	private Map<Class<?>, TypeModel> typeCache = new HashMap<Class<?>, TypeModel>();
	private final ClasspathTranslator classpathTranslator;
	private final As3VariableNamesGenerator generator;

	public ModelFactory(ClasspathTranslator classpathTranslator,
			As3VariableNamesGenerator generator) {
		this.classpathTranslator = classpathTranslator;
		this.generator = generator;
	}

	private PropertyModel createProperty(Field field) {
		return new PropertyModel(createType(field.getGenericType()), field
				.getName(), true, true);
	}

	private MethodModel createMethod(Method method) {
		List<ParameterModel> paramters = new ArrayList<ParameterModel>();
		generator.reset();
		for (Type paramType : method.getGenericParameterTypes()) {
			TypeModel type = createType(paramType);
			String name = generator.nameForParameter(type.getName());
			paramters.add(createParameter(paramType, name));
		}
		return new MethodModel(method.getName(), createType(method
				.getGenericReturnType()), paramters);
	}

	TypeModel createType(Type type) {
		TypeModel model = typeCache.get(type);
		if (model != null) {
			return model;
		}
		Class<?> rawType;
		Type[] actualGenericTypes;
		if (type instanceof ParameterizedType) {
			ParameterizedType paramType = (ParameterizedType) type;
			rawType = (Class<?>) paramType.getRawType();
			actualGenericTypes = paramType.getActualTypeArguments();
		} else if (type instanceof Class<?>) {
			rawType = (Class<?>) type;
			actualGenericTypes = new Type[0];
		} else {
			throw new RuntimeException("We don't serve your kind here, "
					+ type.getClass()); // TODO handle arrays etc.
		}
		return createType(rawType, actualGenericTypes);
	}

	TypeModel createType(Class<?> claz, Type... generics) {
		List<TypeModel> actualGenericTypes = new ArrayList<TypeModel>();
		for (Type genericType : generics) {
			actualGenericTypes.add(createType(genericType));
		}

		TypeModel superType = null;
		if (claz.getSuperclass() != null
				&& !Object.class.equals(claz.getSuperclass())) {
			superType = createType(claz.getSuperclass());
		}
		String originalPkg = claz.isPrimitive() ? null : claz.getPackage()
				.getName();
		String newPackage = classpathTranslator.getTranslatedPackage(claz);
		String newName = classpathTranslator.getTranslatedName(claz);
		return new TypeModel(originalPkg, claz.getSimpleName(), newPackage,
				newName, claz.isPrimitive(), superType, actualGenericTypes);
	}

	public RemoteObjectModel createRemoteObject(Class<?> clas) {
		if (clas.isPrimitive()) {
			throw new RuntimeException(
					"cannot create RemoteObjectModel from primitive type "
							+ clas.getName());
		}
		TypeModel typeModel = createType(clas);
		List<PropertyModel> properties = new ArrayList<PropertyModel>();
		for (Field field : clas.getDeclaredFields()) {
			if (!field.getDeclaringClass().equals(Object.class)
					&& !Modifier.isStatic(field.getModifiers()) && //
					field.getAnnotation(RemoteExclude.class) == null) {
				properties.add(createProperty(field));
			}
		}
		return new RemoteObjectModel(typeModel, properties);
	}

	public RemoteServiceModel createRemoteService(Class<?> clas) {
		if (!clas.isInterface()) {
			throw new RuntimeException(
					"RemoteServiceModel can only be created from an interface");
		}
		TypeModel typeModel = createType(clas);
		List<MethodModel> methods = new ArrayList<MethodModel>();
		for (Method method : clas.getMethods()) {
			if (!method.getDeclaringClass().equals(Object.class)
					&& method.getAnnotation(RemoteExclude.class) == null) {
				methods.add(createMethod(method));
			}
		}
		return new RemoteServiceModel(typeModel, methods);
	}

	public RemoteDictionaryModel createRemoteDictionary(Class<?> clas) {
		if (!Enum.class.isAssignableFrom(clas)) {
			throw new RuntimeException(
					"RemoteDictionaryModel can only be created from an enum");
		}
		TypeModel type = createType(clas);
		List<String> constants = new ArrayList<String>();
		for (Field field : clas.getDeclaredFields()) {
			if (field.isEnumConstant()) {
				constants.add(field.getName());
			}
		}
		return new RemoteDictionaryModel(type, constants);
	}

	private ParameterModel createParameter(Type type, String name) {
		return new ParameterModel(name, createType(type));
	}

}
