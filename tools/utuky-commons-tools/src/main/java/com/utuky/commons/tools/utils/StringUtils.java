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
	
	public static String firstUpperCase(String str) {
		String result = str ;
		if(str==""||"".equals(str.trim())) return null ;
		String b = str.substring(0, 1) ;
		str.replaceFirst(b,b.toUpperCase() ) ;
		return result ;
	}
	
	public static String firstLowerCase(String str) {
		String result = str ;
		if(str==""||"".equals(str.trim())) return null ;
		String b = str.substring(0, 1) ;
		str.replaceFirst(b,b.toLowerCase()) ;
		return result ;
	}
}
