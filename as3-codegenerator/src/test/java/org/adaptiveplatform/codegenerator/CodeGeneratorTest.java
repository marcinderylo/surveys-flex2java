package org.adaptiveplatform.codegenerator;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.adaptiveplatform.codegenerator.sampleclasses.SampleDao;
import org.adaptiveplatform.codegenerator.sampleclasses.SampleDto;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.sonatype.flexmojos.generator.api.GenerationRequest;

@Ignore("run manually")
public class CodeGeneratorTest {

	private CodeGenerator generator;

	@Before
	public void beforeMethod() throws Exception {
		generator = new CodeGenerator();
	}

	@Test
	public void shouldGenerateDtos() throws Exception {
		// given
		GenerationRequest request = createGenerationRequest(SampleDto.class);
		// when
		generator.generate(request);
		// then
	}

	@Test
	public void shouldGenerateDao() throws Exception {
		// given
		GenerationRequest request = createGenerationRequest(SampleDao.class);
		// when
		generator.generate(request);
		// then
	}

	private GenerationRequest createGenerationRequest(Class<?>... classes) {
		GenerationRequest request = new GenerationRequest();
		Map<String, File> map = new HashMap<String, File>();
		for (Class<?> clas : classes) {
			map.put(clas.getName(), null);
		}
		request.setClasses(map);
		request.setClassLoader(getClass().getClassLoader());
		request.setPersistentOutputFolder(new File("target"));
		request.setTransientOutputFolder(new File("target"));
		return request;
	}
}
