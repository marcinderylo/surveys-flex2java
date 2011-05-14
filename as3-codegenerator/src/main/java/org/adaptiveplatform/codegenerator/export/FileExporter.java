package org.adaptiveplatform.codegenerator.export;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.adaptiveplatform.codegenerator.OutputArtifact;

public class FileExporter implements Exporter {
	private final File persistentOutputFolder;
	private final File transientOutputFolder;

	public FileExporter(File persistentOutputFolder, File transientOutputFolder) {
		this.persistentOutputFolder = persistentOutputFolder;
		this.transientOutputFolder = transientOutputFolder;
	}

	@Override
	public void export(OutputArtifact outputArtifact) {
		if (shouldWrite(outputArtifact)) {
			File baseDirectory = outputArtifact.isPersistent() ? persistentOutputFolder : transientOutputFolder;
			File targetFile = createTargetFile(baseDirectory, outputArtifact.getDirectory(),
					outputArtifact.getFileName());
			write(targetFile, outputArtifact.getContent());
		}
	}

	private boolean shouldWrite(OutputArtifact outputArtifact) {
		if (!outputArtifact.isPersistent()) {
			return true;
		}
		File targetFile = file(persistentOutputFolder, outputArtifact.getDirectory(), outputArtifact.getFileName());
		return !targetFile.exists();
	}

	private void write(File targetFile, String content) {
		try {
			FileWriter writer = new FileWriter(targetFile);
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private File createTargetFile(File rootDirectory, String relativeDirectory, String outputFile) {
		File file = file(rootDirectory, relativeDirectory, outputFile);
		file.getParentFile().mkdirs();
		return file;
	}

	private File file(File directory, String... pathElements) {
		for (String pathElement : pathElements) {
			directory = new File(directory, pathElement);
		}
		return directory;
	}

}
