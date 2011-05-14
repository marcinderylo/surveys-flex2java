package org.adaptiveplatform.codegenerator.model;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.Validate;

/**
 * ActionScript3 meta-data model.
 * 
 * @author Rafał Jamróz
 */
public class AnnotationModel {
	private final String name;
	private final List<AnnotationParameterModel> parameters;

	public AnnotationModel(String name, List<AnnotationParameterModel> parameters) {
		this.name = name;
		this.parameters = Collections.unmodifiableList(parameters);
	}

	public String getName() {
		return name;
	}

	public List<AnnotationParameterModel> getParameters() {
		return parameters;
	}

	public boolean hasParameters() {
		return !parameters.isEmpty();
	}

	public static class AnnotationParameterModel {
		private final String name;
		private final Object value;

		public AnnotationParameterModel(String name, Object value) {
			Validate.notNull(value);
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public String getValueAsString() {
			if (value.getClass().equals(String.class)) {
				return "\"" + value + "\"";
			} else {
				return value.toString();
			}
		}
	}
}
