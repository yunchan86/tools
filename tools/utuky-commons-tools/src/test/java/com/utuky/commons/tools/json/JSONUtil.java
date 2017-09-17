package org.utuky.commons.base.json;

import java.lang.reflect.Type;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;

/**
 * @since 2017-09-17 
 * @author Frain
 *
 */
public class JSONUtil {

	public String obj2Json(Object obj) {
		return JSON.toJSONString(obj);
	}
	
	public static <T> T json2Obj(String json,Class<T> clazz,Feature... features) {
		try {
			T value = null;
			if(features!=null) {
				value = JSON.parseObject(json, clazz,features);
			}else {
				value = JSON.parseObject(json, clazz);
			}
			return value;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static <T> T json2Obj(String json, Type type,Feature... features) {
		try {
			T value = null;
			if(features!=null) {
				value = JSON.parseObject(json, type,features);
			}else {
				value = JSON.parseObject(json, type);
			}
			return value;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
