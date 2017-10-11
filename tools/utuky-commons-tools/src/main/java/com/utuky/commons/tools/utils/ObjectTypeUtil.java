package com.utuky.commons.tools.utils;

import java.util.Date;

public class ObjectTypeUtil {

	public static String getBaseType(Object obj) {
		String result = null ;
		if(obj instanceof String) {
			result = "String";
		}else if(obj instanceof Integer) {
			result = "Integer" ;
		}else if(obj instanceof Long) {
			result = "Long" ;
		}else if(obj instanceof Float) {
			result = "Float" ;
		}else if(obj instanceof Double) {
			result = "Double" ;
		}else if(obj instanceof Character) {
			result = "Character" ;
		}else if(obj instanceof Boolean) {
			result = "Boolean" ;
		}else if(obj instanceof Date){
			result = "Date";
		}else {
			Class<? extends Object> type = obj.getClass();
			if(type == int.class) {
				result = "int" ;
			}else if(type == float.class) {
				result = "float";
			}else if(type == double.class) {
				result = "double";
			}else if(type == long.class) {
				result = "long";
			}else if(type == char.class) {
				result = "char";
			}else if(type == byte.class) {
				result = "byte";
			}else if(type == boolean.class) {
				result = "boolean";
			}
		}
		return result ;
	}
	
	public static String getCollectionType(Object obj) {
		String result = null ;
		
		return result ;
	}
}
