package org.adaptiveplatform.codegenerator.api;

public @interface Param {
	String value();
	
	Class<?> collectionType() default Object.class;
}
