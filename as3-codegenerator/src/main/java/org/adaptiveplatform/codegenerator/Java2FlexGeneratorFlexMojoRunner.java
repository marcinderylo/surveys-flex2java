package org.adaptiveplatform.codegenerator;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.codehaus.plexus.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.flexmojos.generator.api.GenerationException;
import org.sonatype.flexmojos.generator.api.GenerationRequest;
import org.sonatype.flexmojos.generator.api.Generator;

@Component(role = org.sonatype.flexmojos.generator.api.Generator.class, hint = "adaptiveCodeGenerator")
public class Java2FlexGeneratorFlexMojoRunner implements Generator {
	private final Logger logger = LoggerFactory.getLogger(Java2FlexGeneratorFlexMojoRunner.class);

	private static final String TRANSLATE_PACKAGES_EXTRA_OPTION = "translatePackages";

	public void generate(GenerationRequest request) throws GenerationException {
		Java2FlexCodeGeneratorConfiguration configuration = new Java2FlexCodeGeneratorConfiguration();
		configuration.translatePackages(loadPropertiesMap(request.getExtraOptions()
				.get(TRANSLATE_PACKAGES_EXTRA_OPTION)));
		configuration.addConstrainAnnotationsPackage("org.adaptiveplatform");
		configuration.configureFreemarkerTemplatingEngine("/", ".ftl");
		configuration.configureFileExporter(request.getPersistentOutputFolder(), request.getTransientOutputFolder());
		Java2FlexCodeGenerator generator = configuration.build();
		generator.generate(loadClasses(request.getClasses().keySet(), request.getClassLoader()));
	}

	private Set<Class<?>> loadClasses(Set<String> classesStrings, ClassLoader classLoader) {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		for (String classString : classesStrings) {
			try {
				classes.add(classLoader.loadClass(classString));
			} catch (ClassNotFoundException e) {
				logger.warn("Class not found: " + classString);
			}
		}
		return classes;
	}

	private Map<String, String> loadPropertiesMap(String string) throws GenerationException {
		Properties properties = new Properties();
		try {
			properties.load(new StringReader(string));
			Map<String, String> map = new HashMap<String, String>();
			for (Map.Entry<Object, Object> entry : properties.entrySet()) {
				map.put(toTrimmedString(entry.getKey()), toTrimmedString(entry.getValue()));
			}
			return map;
		} catch (IOException cause) {
			throw new GenerationException("cannot parse extra option: " + TRANSLATE_PACKAGES_EXTRA_OPTION, cause);
		}
	}

	private String toTrimmedString(Object value) {
		if (value == null) {
			return null;
		}
		return value.toString().trim();
	}
}
