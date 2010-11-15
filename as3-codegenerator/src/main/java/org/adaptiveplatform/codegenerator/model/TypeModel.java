package org.adaptiveplatform.codegenerator.model;

import java.util.List;

/**
 * Model representing java type (class, primitive, array, interface,
 * annotation).
 * 
 * @author Rafal Jamroz
 */
public class TypeModel {

	private final String originalPackage;
	private final String originalName;
	private final String pkg;
	private final String name;
	private final Boolean isPrimitive;
	private final TypeModel superType;
	private final List<TypeModel> actualGenericArguments;

	public TypeModel(String originalPackage, String originalName, String pkg,
			String name, Boolean isPrimitive, TypeModel superType,
			List<TypeModel> genericParameters) {
		this.originalPackage = originalPackage;
		this.originalName = originalName;
		this.pkg = pkg;
		this.name = name;
		this.isPrimitive = isPrimitive;
		this.superType = superType;
		this.actualGenericArguments = genericParameters;
	}

	public boolean isGeneric() {
		return actualGenericArguments != null
				&& !actualGenericArguments.isEmpty();
	}

	public boolean isSubclass() {
		return superType != null;
	}

	public String getOriginalPackage() {
		return originalPackage;
	}

	public String getOriginalName() {
		return originalName;
	}

	public String getPackage() {
		return pkg;
	}

	public String getName() {
		return name;
	}

	public boolean isPrimitive() {
		return isPrimitive;
	}

	public TypeModel getSuperType() {
		return superType;
	}

	public List<TypeModel> getActualGenericArguments() {
		return actualGenericArguments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((actualGenericArguments == null) ? 0
						: actualGenericArguments.hashCode());
		result = prime * result
				+ ((originalName == null) ? 0 : originalName.hashCode());
		result = prime * result
				+ ((originalPackage == null) ? 0 : originalPackage.hashCode());
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
		TypeModel other = (TypeModel) obj;
		if (actualGenericArguments == null) {
			if (other.actualGenericArguments != null)
				return false;
		} else if (!actualGenericArguments.equals(other.actualGenericArguments))
			return false;
		if (originalName == null) {
			if (other.originalName != null)
				return false;
		} else if (!originalName.equals(other.originalName))
			return false;
		if (originalPackage == null) {
			if (other.originalPackage != null)
				return false;
		} else if (!originalPackage.equals(other.originalPackage))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TypeModel [pkg=" + pkg + ", name=" + name
				+ ", originalPackage=" + originalPackage + ", originalName="
				+ originalName + ", isPrimitive=" + isPrimitive + "]";
	}
}
