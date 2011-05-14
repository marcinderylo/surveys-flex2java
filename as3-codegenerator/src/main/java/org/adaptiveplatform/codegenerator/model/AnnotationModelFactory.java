package org.adaptiveplatform.codegenerator.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.adaptiveplatform.codegenerator.model.AnnotationModel.AnnotationParameterModel;

import com.google.common.collect.Sets;

public class AnnotationModelFactory {

	private final Set<String> allowedPackages;

	private final Set<String> disallowedParameters = Sets.newHashSet("payload", "groups", "message");

	public AnnotationModelFactory(Set<String> exportablePackages) {
		allowedPackages = Collections.unmodifiableSet(exportablePackages);
	}

	public boolean isExportable(Annotation annotation) {
		Package annotationPackage = annotation.annotationType().getPackage();
		if (annotationPackage != null) {
			for (String allowedPackage : allowedPackages) {
				if (annotationPackage.getName().startsWith(allowedPackage)) {
					return true;
				}
			}
		}
		return false;
	}

	public AnnotationModel createAnnotationModel(Annotation annotation) {
		List<AnnotationParameterModel> params = new ArrayList<AnnotationParameterModel>();
		for (Method method : annotation.annotationType().getDeclaredMethods()) {
			String name = method.getName();
			if (!disallowedParameters.contains(name)) {
				try {
					Object value = method.invoke(annotation);
					params.add(new AnnotationParameterModel(name, value));
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
		return new AnnotationModel(annotation.annotationType().getSimpleName(), params);
	}
}
