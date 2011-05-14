package org.adaptiveplatform.codegenerator;

import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.adaptiveplatform.codegenerator.export.Exporter;

public class ExporterMock implements Exporter {

	private Map<String, String> exportedArtifacts = new HashMap<String, String>();

	@Override
	public void export(OutputArtifact outputArtifact) {
		exportedArtifacts.put(outputArtifact.getFileName(), outputArtifact.getContent());
	}

	public String getArtifact(String artifactName) {
		String artifact = exportedArtifacts.get(artifactName);
		if (artifactName == null) {
			fail("Artifact has not been exporter: " + artifact);
		}
		return artifact;
	}
}
