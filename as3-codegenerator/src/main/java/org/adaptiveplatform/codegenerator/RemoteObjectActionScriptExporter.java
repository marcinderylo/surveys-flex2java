package org.adaptiveplatform.codegenerator;

import java.util.ArrayList;
import java.util.List;

import org.adaptiveplatform.codegenerator.model.RemoteObjectModel;
import org.adaptiveplatform.codegenerator.utils.ArrayUtils;


/**
 * Creates an export request for a class annotated with @RemoteObject.
 * 
 * @author Rafal Jamroz
 */
public class RemoteObjectActionScriptExporter implements ArtifactExporter {

	private RemoteObjectModel model;

	public RemoteObjectActionScriptExporter(RemoteObjectModel model) {
		this.model = model;
	}

	@Override
	public List<ExportRequest> export() {
		String directory = ArrayUtils.join(model.getPackage().split("\\."), "/");
		List<ExportRequest> exported = new ArrayList<ExportRequest>();
		exported.add(new ExportRequest(directory, model.getName() + ".as", "remoteObject.ftl", model, true));
		exported.add(new ExportRequest(directory, model.getName() + "Base.as", "remoteObjectBase.ftl", model, false));
		return exported;
	}

	@Override
	public String toString() {
		return "RemoteObjectActionScriptExporter [model=" + model + "]";
	}
}
