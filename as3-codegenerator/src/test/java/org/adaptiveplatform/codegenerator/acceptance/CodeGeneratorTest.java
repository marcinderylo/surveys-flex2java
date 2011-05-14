package org.adaptiveplatform.codegenerator.acceptance;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.adaptiveplatform.codegenerator.ExporterMock;
import org.adaptiveplatform.codegenerator.Java2FlexCodeGenerator;
import org.adaptiveplatform.codegenerator.Java2FlexCodeGeneratorConfiguration;
import org.apache.log4j.lf5.util.StreamUtils;
import org.junit.Before;
import org.junit.Test;

public class CodeGeneratorTest {

	private Java2FlexCodeGenerator generator;
	private ExporterMock exporterMock;

	@Before
	public void beforeMethod() throws Exception {
		Java2FlexCodeGeneratorConfiguration config = new Java2FlexCodeGeneratorConfiguration();
		config.configureFreemarkerTemplatingEngine("/", ".ftl");
		config.addConstrainAnnotationsPackage("org.adaptiveplatform.codegenerator");
		exporterMock = new ExporterMock();
		config.setExporter(exporterMock);
		generator = config.build();
	}

	@Test
	public void shouldGenerateDtos() throws Exception {
		// when
		generate(SampleDto.class);
		// then
		assertArtifactsMatchExpectedOutput("SampleDto.as", "SampleDtoBase.as");
	}

	@Test
	public void shouldGenerateDao() throws Exception {
		// when
		generate(SampleDao.class);
		// then
		assertArtifactsMatchExpectedOutput("SampleDao.as", "RemoteSampleDao.as");
	}

	@Test
	public void shouldGenerateCommandWithValidationMetadata() throws Exception {
		// when
		generate(SampleCommand.class);
		// then
		assertArtifactsMatchExpectedOutput("SampleCommand.as", "SampleCommandBase.as");
	}

	private void generate(Class<?>... classes) {
		generator.generate(Arrays.asList(classes));
	}

	private void assertArtifactsMatchExpectedOutput(String... artifactNames) throws IOException {
		for (String artifactName : artifactNames) {
			String expectedContent = loadExpectedArtifactContent(artifactName);
			assertEquals(expectedContent, exporterMock.getArtifact(artifactName));
		}
	}

	private String loadExpectedArtifactContent(String artifactName) throws IOException {
		InputStream resource = getClass().getResourceAsStream(artifactName);
		if (resource == null)
			throw new RuntimeException("expected artifact content not found on classpath: " + artifactName);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		StreamUtils.copyThenClose(resource, outputStream);
		return outputStream.toString();
	}
}
