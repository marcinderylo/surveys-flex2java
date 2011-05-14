package org.adaptiveplatform.codegenerator.model;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adaptiveplatform.codegenerator.ClasspathTranslator;

public class TypeModelFactory {
	private Map<Class<?>, TypeModel> typeCache = new HashMap<Class<?>, TypeModel>();
	private final ClasspathTranslator classpathTranslator;

	public TypeModelFactory(ClasspathTranslator classpathTranslator) {
		this.classpathTranslator = classpathTranslator;
	}

	public TypeModel createType(Type type) {
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
			throw new RuntimeException("We don't serve your kind here, " + type.getClass());
		}
		return createType(rawType, actualGenericTypes);
	}

	public TypeModel createType(Class<?> claz, Type... generics) {
		List<TypeModel> actualGenericTypes = new ArrayList<TypeModel>();
		for (Type genericType : generics) {
			actualGenericTypes.add(createType(genericType));
		}

		TypeModel superType = null;
		if (claz.getSuperclass() != null && !Object.class.equals(claz.getSuperclass())) {
			superType = createType(claz.getSuperclass());
		}
		String originalPkg = claz.isPrimitive() ? null : claz.getPackage().getName();
		String newPackage = classpathTranslator.getTranslatedPackage(claz);
		String newName = classpathTranslator.getTranslatedName(claz);
		// TODO handle arrays etc.
		return new TypeModel(originalPkg, claz.getSimpleName(), newPackage, newName, claz.isPrimitive(), superType,
				actualGenericTypes);
	}
}
