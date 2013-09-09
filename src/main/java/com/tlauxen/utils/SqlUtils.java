package com.tlauxen.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.tlauxen.model.Entity;

public class SqlUtils {

	public static String getSqlFindById(Class<?> clazz) {
		
		String tableName = clazz.getSimpleName();

		Map<String,List<String>> projectionFieldsTables = getProjectionFieldsTables(tableName, clazz);
		
		List<String> projection = projectionFieldsTables.get("projection");
		List<String> joinTables = projectionFieldsTables.get("joinTables");
		
		StringBuilder from = new StringBuilder(tableName);
		String join = " left join %s on %s.id = %s.id%s";
		for (String joinTable: joinTables) {
			from.append(String.format(join, joinTable, joinTable, tableName, joinTable));
		}
		
		String sqlPattern = "select %s from %s where %s";
		
		return String.format(sqlPattern, StringUtils.join(projection.toArray(new String[]{}), ","), from.toString(), tableName+".id = ?");
	}
	
	public static String getSqlInsert(Entity entity) {

		String tableName = entity.getClass().getSimpleName();

		Map<String, Object> props = ReflectionUtils.getFields(entity);
		
		String sqlPattern = "insert into %s (%s) values (%s)";
		
		String fields = StringUtils.join(props.keySet(),",");
		String values = ":"+fields.replace(",", ",:");
		
		return String.format(sqlPattern, tableName, fields, values);
		
	}
	
	public static String getSqlUpdate(Entity entity) {

		String tableName = entity.getClass().getSimpleName();

		Map<String, Object> props = ReflectionUtils.getFields(entity);
		
		String sqlPattern = "update %s set %s where %s";
		
		StringBuilder fields = new StringBuilder();
		String where = "id = :id";
		
		for (String field: props.keySet()) {
			
			if (!"id".equals(field)) {

				if (fields.length() > 0) {
					fields.append(",");
				}
				fields.append(field).append("=:").append(field);
				
			}
			
		}

		return String.format(sqlPattern, tableName, fields.toString(), where);
		
	}
	
	public static String getSqlRemove(Entity entity) {

		String tableName = entity.getClass().getSimpleName();

		String sqlPattern = "delete from %s where %s";
		
		String where = "id = :id";
		
		return String.format(sqlPattern, tableName, where);
		
	}
	
	private static Map<String,List<String>> getProjectionFieldsTables(String tableName, Class<?> clazz) {
		Map<String, Class<?>> fields = ReflectionUtils.getFields(clazz);
		
		List<String> projection = new ArrayList<String>();
		List<String> joinTables = new ArrayList<String>();
		
		for (String key: fields.keySet()) {
			Class<?> attrClazz = fields.get(key);
			
			if (Entity.class.isAssignableFrom(attrClazz)) {
				
				joinTables.add(attrClazz.getSimpleName());
				
				Map<String, Class<?>> fieldFields = ReflectionUtils.getFields(attrClazz);
				
				for (String fieldKey: fieldFields.keySet()) {
					if (! (Entity.class.isAssignableFrom(fieldFields.get(fieldKey)))) {
						
						String joinTableName = attrClazz.getSimpleName();
						
						projection.add(joinTableName+"."+fieldKey+" as " + joinTableName+"_"+fieldKey);
						
					}
				}
				
			} else {
				projection.add(tableName+"."+key+" as " + key);
			}
		}
		
		Map<String,List<String>> toReturn = new HashMap<String, List<String>>();
		
		toReturn.put("projection", projection);
		toReturn.put("joinTables", joinTables);
		
		return toReturn;
	}

}
