package com.utuky.commons.tools.utils;

import java.util.Collection;

/**
 * 
 * @author Frain
 * 集合的自定义处理工具类
 */
public class CollectionUtils {

	public static boolean isBlank (Collection<?> collection) {
		if(collection==null || collection.isEmpty()) return true ;
		return false ;
	}
}
