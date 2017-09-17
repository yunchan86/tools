package com.utuky.commons.tools.utils;

public class ObjectTypeUtil {

	public static String getBaseType(Object obj) {
		String result = null ;
		if(obj instanceof String) {
			result = "String";
		}else if(obj instanceof Integer) {
			result = "Integer" ;
		}
		return result ;
	}
}
