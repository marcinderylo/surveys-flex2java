package org.adaptiveplatform.codegenerator;

import java.util.ArrayList;
import java.util.List;

import org.adaptiveplatform.codegenerator.model.RemoteServiceModel;
import org.adaptiveplatform.codegenerator.utils.ArrayUtils;


/**
 * Creates an export request for a class annotated with @RemoteService.
 * 
 * @author Rafal Jamroz
 */
public class RemoteServiceActionScriptExporter implements ArtifactExporter {

	private RemoteServiceModel model;

	public RemoteServiceActionScriptExporter(RemoteServiceModel model) {
		this.model = model;
	}

	@Override
	public List<ExportRequest> export() {
		String directory = ArrayUtils.join(model.getPackage().split("\\."), "/");
		List<ExportRequest> exported = new ArrayList<ExportRequest>();
		exported.add(new ExportRequest(directory, model.getName() + ".as", "remoteService.ftl", model, false));
		exported.add(new ExportRequest(directory, "Remote" + model.getName() + ".as", "remoteServiceImpl.ftl", model,
				false));
		return exported;
	}

	@Override
	public String toString() {
		return "RemoteServiceActionScriptExporter [model=" + model + "]";
	}
}
