package org.adaptiveplatform.codegenerator.model;

import java.util.Collections;
import java.util.List;

/**
 * Model representing AS3 property.
 * 
 * @author Rafal Jamroz
 */
public class PropertyModel {
	private String name;
	private TypeModel type;
	private boolean readable;
	private boolean writable;
	private List<AnnotationModel> annotations;

	public PropertyModel(TypeModel type, String name, boolean readable, boolean writable,
			List<AnnotationModel> annotations) {
		this.name = name;
		this.type = type;
		this.readable = readable;
		this.writable = writable;
		this.annotations = annotations;
		if (this.annotations == null) {
			this.annotations = Collections.emptyList();
		}
	}

	public String getName() {
		return name;
	}

	public TypeModel getType() {
		return type;
	}

	public boolean isReadable() {
		return readable;
	}

	public boolean isWritable() {
		return writable;
	}

	public List<AnnotationModel> getAnnotations() {
		return annotations;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PropertyModel other = (PropertyModel) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PropertyModel [type=" + type + ", name=" + name + "]";
	}
}
