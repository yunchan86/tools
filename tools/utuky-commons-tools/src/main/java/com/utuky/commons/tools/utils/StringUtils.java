package com.utuky.commons.tools.utils;

public class StringUtils {

	public static boolean isBlank(String str) {
		boolean b = false ;
		if(str==null || "".equals(str.trim())) b = true ;
		return b ;
	}
	
	public static boolean isNotBlank(String str) {
		boolean b = true ;
		if(isBlank(str)) b = false ;
		return b ;
	}
}
