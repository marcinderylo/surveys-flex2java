package org.adaptiveplatform.codegenerator.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Class will be generated as DTO class in AS3.
 * 
 * @author Rafal Jamroz
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
public @interface RemoteObject {

}
