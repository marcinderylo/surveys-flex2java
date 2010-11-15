package org.adaptiveplatform.codegenerator;

import java.util.List;

/**
 * Creates a list of artifacts to export.
 * 
 * @author Rafal Jamroz
 */
public interface ArtifactExporter {
	List<ExportRequest> export();
}
