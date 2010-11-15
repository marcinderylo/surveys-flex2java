package org.adaptiveplatform.codegenerator;

import org.adaptiveplatform.codegenerator.api.RemoteDictionary;
import org.adaptiveplatform.codegenerator.api.RemoteObject;
import org.adaptiveplatform.codegenerator.api.RemoteService;
import org.adaptiveplatform.codegenerator.model.ModelFactory;


/**
 * TODO class info
 * 
 * @author Rafal Jamroz
 */
public class FileExporterFactory {

	private final ModelFactory factory;

	public FileExporterFactory(ModelFactory factory) {
		this.factory = factory;
	}

	public ArtifactExporter create(Class<?> clas) {
		if (isRemoteObject(clas)) {
			return new RemoteObjectActionScriptExporter(factory.createRemoteObject(clas));
		} else if (isRemoteService(clas)) {
			return new RemoteServiceActionScriptExporter(factory.createRemoteService(clas));
		} else if (isRemoteDictionary(clas)) {
			return new RemoteDictionaryActionScriptExporter(factory.createRemoteDictionary(clas));
		}
		return null;
	}

	public boolean exportable(Class<?> clas) {
		return isRemoteObject(clas) || isRemoteService(clas) || isRemoteDictionary(clas);
	}

	private boolean isRemoteObject(Class<?> clas) {
		return clas.getAnnotation(RemoteObject.class) != null;
	}

	private boolean isRemoteService(Class<?> clas) {
		return clas.getAnnotation(RemoteService.class) != null;
	}

	private boolean isRemoteDictionary(Class<?> clas) {
		return clas.getAnnotation(RemoteDictionary.class) != null;
	}
}
