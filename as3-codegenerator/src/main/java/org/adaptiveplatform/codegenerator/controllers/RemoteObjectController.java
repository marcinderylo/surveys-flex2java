package org.adaptiveplatform.codegenerator.controllers;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.adaptiveplatform.codegenerator.ClassGenerationRequest;
import org.adaptiveplatform.codegenerator.api.RemoteExclude;
import org.adaptiveplatform.codegenerator.model.AnnotationModel;
import org.adaptiveplatform.codegenerator.model.AnnotationModel.AnnotationParameterModel;
import org.adaptiveplatform.codegenerator.model.AnnotationModelFactory;
import org.adaptiveplatform.codegenerator.model.PropertyModel;
import org.adaptiveplatform.codegenerator.model.RemoteObjectModel;
import org.adaptiveplatform.codegenerator.model.TypeModel;
import org.adaptiveplatform.codegenerator.model.TypeModelFactory;
import org.apache.commons.beanutils.PropertyUtils;

import com.google.common.collect.Lists;

public class RemoteObjectController implements CodeGenerationController {

	private final TypeModelFactory types;
	private final AnnotationModelFactory exportableAnnotationPolicy;

	public RemoteObjectController(TypeModelFactory types, AnnotationModelFactory exportableAnnotationPolicy) {
		this.types = types;
		this.exportableAnnotationPolicy = exportableAnnotationPolicy;
	}

	@Override
	public Collection<ClassGenerationRequest> generate(Class<?> classToGenerate) {
		RemoteObjectModel model = createRemoteObject(classToGenerate);
		ClassGenerationRequest object = new ClassGenerationRequest(model.getPackage(), model.getName(), "remoteObject",
				model, true);
		ClassGenerationRequest objectBase = new ClassGenerationRequest(model.getPackage(), model.getName() + "Base",
				"remoteObjectBase", model, false);
		return Arrays.asList(object, objectBase);
	}

	private RemoteObjectModel createRemoteObject(Class<?> clas) {
		if (clas.isPrimitive()) {
			throw new RuntimeException("cannot create RemoteObjectModel from primitive type " + clas.getName());
		}
		TypeModel typeModel = types.createType(clas);
		List<PropertyModel> properties = getPropertiesOfClass(clas);
		return new RemoteObjectModel(typeModel, properties);
	}

	private List<PropertyModel> getPropertiesOfClass(Class<?> clas) {
		List<PropertyModel> properties = new ArrayList<PropertyModel>();
		for (Field field : clas.getDeclaredFields()) {
			if (!Modifier.isStatic(field.getModifiers())
					&& !containsAnnotation(field.getAnnotations(), RemoteExclude.class)) {
				PropertyDescriptor propertyDescriptor = getPropertyDescriptor(clas, field.getName());
				PropertyModel propertyModel = createProperty(field, propertyDescriptor);
				if (propertyModel != null) {
					properties.add(propertyModel);
				}
			}
		}
		return properties;
	}

	private PropertyDescriptor getPropertyDescriptor(Class<?> clas, String name) {
		for (PropertyDescriptor prop : PropertyUtils.getPropertyDescriptors(clas)) {
			if (name.equals(prop.getName())) {
				return prop;
			}
		}
		return null;
	}

	private PropertyModel createProperty(Field field, PropertyDescriptor property) {
		List<Annotation> annotations = Lists.newArrayList();
		annotations.addAll(Arrays.asList(field.getAnnotations()));
		if (property != null) {
			annotations.addAll(annotationsFromMethod(property.getReadMethod()));
			annotations.addAll(annotationsFromMethod(property.getWriteMethod()));
		}

		TypeModel propertyType = types.createType(field.getGenericType());
		return new PropertyModel(propertyType, field.getName(), true, true, exportableAnnotations(annotations));
	}

	private boolean containsAnnotation(Annotation[] annotations, Class<?> annotationType) {
		for (Annotation annotation : annotations) {
			if (annotation.annotationType().equals(annotationType)) {
				return true;
			}
		}
		return false;
	}

	private Collection<? extends Annotation> annotationsFromMethod(Method method) {
		if (method != null) {
			return Arrays.asList(method.getAnnotations());
		}
		return Collections.emptySet();
	}

	private List<AnnotationModel> exportableAnnotations(List<Annotation> propertyAnnotations) {
		List<AnnotationModel> annotations = new ArrayList<AnnotationModel>();
		for (Annotation annotation : propertyAnnotations) {
			if (exportableAnnotationPolicy.isExportable(annotation)) {
				annotations.add(exportableAnnotationPolicy.createAnnotationModel(annotation));
			}
		}
		return annotations;
	}
}
