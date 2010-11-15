package org.adaptiveplatform.codegenerator.model;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static org.fest.assertions.Assertions.assertThat;

import java.lang.annotation.ElementType;
import java.util.List;
import java.util.Set;

import org.adaptiveplatform.codegenerator.ClasspathTranslator;
import org.adaptiveplatform.codegenerator.sampleclasses.SampleDao;
import org.adaptiveplatform.codegenerator.sampleclasses.SampleDto;
import org.adaptiveplatform.codegenerator.utils.As3VariableNamesGenerator;
import org.junit.Before;
import org.junit.Test;

public class ModelFactoryTest {
	private ModelFactory factory;
	private RemoteObjectModel object;
	private RemoteServiceModel service;

	@Before
	public void beforeMethod() throws Exception {
		factory = new ModelFactory(new ClasspathTranslator(),
				new As3VariableNamesGenerator());
		object = factory.createRemoteObject(SampleDto.class);
		service = factory.createRemoteService(SampleDao.class);
	}

	@Test
	public void shouldGenericTypeEqual() throws Exception {
		assertEquals(type(List.class, String.class), object.getProperties()
				.get(3).getType());
	}

	@Test
	public void shouldGetPackage() throws Exception {
		assertEquals(object.getPackage(), SampleDto.class.getPackage()
				.getName());
	}

	@Test
	public void shouldCreateServiceWithMethods() throws Exception {
		assertEquals(service.getMethods().size(), 3);
	}

	@Test
	public void shouldReturnProperties() throws Exception {
		assertThat(object.getProperties()).containsExactly(
				property("id", Long.class), property("name", String.class),
				property("email", String.class),
				property("roles", List.class, String.class));
	}

	@Test
	public void shouldResolveRelatedClasses() throws Exception {
		assertThat(object.getRelatedClasses()).contains(type(String.class),
				type(Long.class));
	}

	@Test
	public void shouldResolveImports() throws Exception {
		assertThat(object.getImports()).containsOnly(
				type(List.class, String.class));
	}

	@Test
	public void shouldCreateDictionaryModelFromEnum() throws Exception {
		RemoteDictionaryModel model = factory
				.createRemoteDictionary(ElementType.class);
		assertEquals(model.getConstants(), asList("TYPE", "FIELD", "METHOD",
				"PARAMETER", "CONSTRUCTOR", "LOCAL_VARIABLE",
				"ANNOTATION_TYPE", "PACKAGE"));
	}

	@Test
	public void shouldResolveFieldGenericArguments() throws Exception {
		class ClassWithGenericField {
			private List<String> field;
		}
		RemoteObjectModel ro = factory
				.createRemoteObject(ClassWithGenericField.class);
		PropertyModel propertyModel = ro.getProperties().get(0);
		assertEquals("String", propertyModel.getType().getActualGenericArguments().get(
				0).getName());
	}

	interface ClassWithGenericMethodReturnType {
		Set<Short> doIt();
	}

	@Test
	public void shouldResolveMethodGenericReturnType() throws Exception {
		RemoteServiceModel rs = factory
				.createRemoteService(ClassWithGenericMethodReturnType.class);
		assertEquals(type(Set.class, Short.class), rs.getMethods().get(0)
				.getReturnType());
	}

	interface ClassWithGenericMethodParameters {
		void doIt(Class<Long> stringClass);
	}

	@Test
	public void shouldResolveMethodGenericParameters() throws Exception {
		RemoteServiceModel rs = factory
				.createRemoteService(ClassWithGenericMethodParameters.class);
		assertEquals(type(Class.class, Long.class), rs.getMethods().get(0)
				.getParameters().get(0).getType());
	}

	private PropertyModel property(String name, Class<?> claz,
			Class<?>... generics) {
		return new PropertyModel(type(claz, generics), name, true, true);
	}

	private TypeModel type(Class<?> claz, Class<?>... generics) {
		return factory.createType(claz, generics);
	}
}
