package org.adaptiveplatform.codegenerator.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Model representing AS3 DTO class.
 * 
 * @author Rafal Jamroz
 */
public class RemoteObjectModel {

	private TypeModel type;
	private List<PropertyModel> properties;

	public RemoteObjectModel(TypeModel type, List<? extends PropertyModel> properties) {
		this.type = type;
		this.properties = Collections.unmodifiableList(properties);
	}

	public Set<TypeModel> getImports() {
		Set<TypeModel> imports = new HashSet<TypeModel>();
		for (TypeModel type : getRelatedClasses()) {
			if (type.getPackage() != null && !type.getPackage().equals("java.lang")
					&& !type.getPackage().equals(getPackage())) {
				imports.add(type);
			}
		}
		return imports;
	}

	public Set<TypeModel> getRelatedClasses() {
		Set<TypeModel> related = new HashSet<TypeModel>();
		for (PropertyModel property : getProperties()) {
			if (!property.getType().isPrimitive()) {
				related.add(property.getType());
			}
		}
		if (type.isSubclass()) {
			related.add(type.getSuperType());
		}
		return related;
	}

	public String getOriginalPackage() {
		return type.getOriginalPackage();
	}

	public String getOriginalName() {
		return type.getOriginalName();
	}

	public String getPackage() {
		return type.getPackage();
	}

	public String getName() {
		return type.getName();
	}

	public List<PropertyModel> getProperties() {
		return properties;
	}

	public TypeModel getSuperType() {
		return type.getSuperType();
	}

	public boolean isSubclass() {
		return type.isSubclass();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		RemoteObjectModel other = (RemoteObjectModel) obj;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RemoteObjectModel [type=" + type + "]";
	}
}
