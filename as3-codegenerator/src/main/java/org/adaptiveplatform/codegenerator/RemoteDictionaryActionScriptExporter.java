package org.adaptiveplatform.codegenerator;

import java.util.ArrayList;
import java.util.List;

import org.adaptiveplatform.codegenerator.model.RemoteDictionaryModel;
import org.adaptiveplatform.codegenerator.utils.ArrayUtils;


/**
 * Creates an export request for a class annotated with @RemoteDictionary.
 * 
 * @author Rafal Jamroz
 */
public class RemoteDictionaryActionScriptExporter implements ArtifactExporter {

	private RemoteDictionaryModel model;

	public RemoteDictionaryActionScriptExporter(RemoteDictionaryModel model) {
		this.model = model;
	}

	@Override
	public List<ExportRequest> export() {
		String directory = ArrayUtils.join(model.getPackage().split("\\."), "/");
		List<ExportRequest> exported = new ArrayList<ExportRequest>();
		exported.add(new ExportRequest(directory, model.getName() + ".as", "remoteDictionary.ftl", model, false));
		return exported;
	}

	@Override
	public String toString() {
		return "RemoteDictionaryActionScriptExporter [model=" + model + "]";
	}

}
