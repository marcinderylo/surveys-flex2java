package org.adaptiveplatform.codegenerator.model;

import java.util.List;

/**
 * Model representing AS3 type (class, primitive, array, interface).
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

	public TypeModel(String originalPackage, String originalName, String pkg, String name, Boolean isPrimitive,
			TypeModel superType, List<TypeModel> genericParameters) {
		this.originalPackage = originalPackage;
		this.originalName = originalName;
		this.pkg = pkg;
		this.name = name;
		this.isPrimitive = isPrimitive;
		this.superType = superType;
		this.actualGenericArguments = genericParameters;
	}

	public boolean isGeneric() {
		return actualGenericArguments != null && !actualGenericArguments.isEmpty();
	}

	public boolean isSubclass() {
		return superType != null;
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
	public String toString() {
		return "TypeModel [pkg=" + pkg + ", name=" + name + "]";
	}

	public String getOriginalPackage() {
		return originalPackage;
	}

	public String getOriginalName() {
		return originalName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actualGenericArguments == null) ? 0 : actualGenericArguments.hashCode());
		result = prime * result + ((isPrimitive == null) ? 0 : isPrimitive.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((originalName == null) ? 0 : originalName.hashCode());
		result = prime * result + ((originalPackage == null) ? 0 : originalPackage.hashCode());
		result = prime * result + ((pkg == null) ? 0 : pkg.hashCode());
		result = prime * result + ((superType == null) ? 0 : superType.hashCode());
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
		if (isPrimitive == null) {
			if (other.isPrimitive != null)
				return false;
		} else if (!isPrimitive.equals(other.isPrimitive))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
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
		if (pkg == null) {
			if (other.pkg != null)
				return false;
		} else if (!pkg.equals(other.pkg))
			return false;
		if (superType == null) {
			if (other.superType != null)
				return false;
		} else if (!superType.equals(other.superType))
			return false;
		return true;
	}
}
