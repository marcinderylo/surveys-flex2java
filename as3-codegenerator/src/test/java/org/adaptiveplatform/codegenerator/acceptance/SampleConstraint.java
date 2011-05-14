package org.adaptiveplatform.codegenerator.acceptance;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface SampleConstraint {
	public int min() default 0;

	public int max() default Integer.MAX_VALUE;
}