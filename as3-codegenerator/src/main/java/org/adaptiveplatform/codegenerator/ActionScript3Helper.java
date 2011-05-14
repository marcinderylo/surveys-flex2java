package org.adaptiveplatform.codegenerator;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ActionScript3Helper {

	public static Map<Class<?>, String> javaToAS3TypesTranslations() {
		Map<Class<?>, String> t = new HashMap<Class<?>, String>();
		t.put(List.class, "mx.collections.ArrayCollection");
		t.put(Set.class, "mx.collections.ArrayCollection");
		t.put(Date.class, "Date");
		t.put(Long.class, "Number");
		t.put(Long.TYPE.getClass(), "Number");
		t.put(Byte.class, "int");
		t.put(Byte.TYPE.getClass(), "int");
		t.put(Short.class, "int");
		t.put(Short.TYPE.getClass(), "int");
		t.put(Integer.class, "int");
		t.put(Integer.TYPE.getClass(), "int");
		t.put(Float.class, "Number");
		t.put(Float.TYPE.getClass(), "Number");
		t.put(Double.class, "Number");
		t.put(Double.TYPE.getClass(), "Number");
		t.put(Boolean.class, "Boolean");
		t.put(Boolean.TYPE.getClass(), "Boolean");
		return t;
	}

	public static Collection<String> as3Keywords() {
		return Arrays.asList("get", "set", "int");
	}
}
