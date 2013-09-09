package com.tlauxen.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.tlauxen.model.Entity;

public class ReflectionUtils {

	public static Map<String, Class<?>> getFields(Class<?> clazz) {
		Map<String, Class<?>> toReturn = new HashMap<String, Class<?>>();
		Field[] declaredFields = clazz.getDeclaredFields();
		for (Field field: declaredFields) {
			if (!Modifier.isStatic(field.getModifiers())) {
				toReturn.put(field.getName(), field.getType());
			}
		}
		return toReturn;
	}
	
	public static Map<String, Object> getFields(Entity entity) {
		Map<String, Object> toReturn = new HashMap<String, Object>();
		
		Map<String, Class<?>> fields = getFields(entity.getClass());
		for (String field: fields.keySet()) {
			Class<?> attrClazz = fields.get(field);
			
			if (Entity.class.isAssignableFrom(attrClazz)) {
				
				toReturn.put("id"+attrClazz.getSimpleName(), ((Entity)getFieldValue(entity, field)).getId());
				
			} else {
				
				toReturn.put(field, getFieldValue(entity, field));
				
			}

		}

		return toReturn;
	}
	
	public static Object getFieldValue(Object entity, String fieldName) {
		Class<? extends Object> clazz = entity.getClass();
		try {
			Field field = clazz.getDeclaredField(fieldName);
			String sufix = "get";
			if (field.getType().isPrimitive() && Boolean.class.isAssignableFrom(field.getType())) {
				sufix = "is";
			}
			String methodName = sufix + StringUtils.capitalize(fieldName);
			Method method = clazz.getDeclaredMethod(methodName, new Class[]{});
			return method.invoke(entity, new Object[]{});
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Object setFieldValue(Object entity, String fieldName, Object value) {
		Class<? extends Object> clazz = entity.getClass();
		try {
			Field field = clazz.getDeclaredField(fieldName);
			String sufix = "set";
			String methodName = sufix + StringUtils.capitalize(fieldName);
			Method method = clazz.getDeclaredMethod(methodName, new Class[]{field.getType()});
			return method.invoke(entity, new Object[]{value});
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static <T> T populate(T o, Map<String,Object> values) {
		for (String key: values.keySet()) {
			
			//Se o atributo for uama entity
			if (key.contains("_")) {
				String[] s = key.split("_");
				String fieldName = s[0];
				String subFieldName = s[1];
				
				
				try {
				
					Field field = o.getClass().getDeclaredField(fieldName);

					Class<?> fieldClazz = field.getType();
					
					Field subField = fieldClazz.getDeclaredField(subFieldName);
					
					Class<?> subFieldClazz = subField.getType();
					
					//Ignora se o atributo do atributo for um objeto do tipo Entity
					if (! Entity.class.isAssignableFrom(subFieldClazz)) {
						
						Object fieldValue = getFieldValue(o, fieldName);
						
						if (fieldValue == null) {
							setFieldValue(o, fieldName, fieldClazz.newInstance());
							fieldValue = getFieldValue(o, fieldName);
						}
						
						setFieldValue(fieldValue, subFieldName, values.get(key));
						
					}
				
				} catch (NoSuchFieldException e) {
					throw new RuntimeException(e);
				} catch (InstantiationException e) {
					throw new RuntimeException(e);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
				
			//Se o atributo é um campo comum
			} else {
				setFieldValue(o, key, values.get(key));
			}
		}
		
		return o;
	}
		
}
