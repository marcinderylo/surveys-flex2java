package org.adaptiveplatform.codegenerator.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adaptiveplatform.codegenerator.utils.StringUtils;

/**
 * Model representing AS3 remote interface class.
 * 
 * @author Rafal Jamroz
 */
public class RemoteServiceModel {
	private TypeModel type;
	private List<MethodModel> methods;

	public RemoteServiceModel(TypeModel type, List<? extends MethodModel> methods) {
		this.type = type;
		this.methods = Collections.unmodifiableList(methods);
	}

	public Set<TypeModel> getImports() {
		Set<TypeModel> imports = new HashSet<TypeModel>();
		for (TypeModel claz : getRelatedClasses()) {
			if (claz.getPackage() != null && !claz.getPackage().equals("java.lang")
					&& !claz.getPackage().equals(getPackage())) {
				imports.add(claz);
			}
		}
		return imports;
	}

	public Set<TypeModel> getRelatedClasses() {
		Set<TypeModel> related = new HashSet<TypeModel>();
		for (MethodModel method : getMethods()) {
			if (!method.getReturnType().isPrimitive()) {
				related.add(method.getReturnType());
			}
			for (ParameterModel model : method.getParameters()) {
				if (!model.getType().isPrimitive()) {
					related.add(model.getType());
				}
			}
		}
		return related;
	}

	public String getPackage() {
		return type.getPackage();
	}

	public String getName() {
		return type.getName();
	}

	public String getBeanName() {
		return StringUtils.decapitalize(getName());
	}

	public List<MethodModel> getMethods() {
		return methods;
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
		RemoteServiceModel other = (RemoteServiceModel) obj;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RemoteServiceModel [type=" + type + "]";
	}
}
