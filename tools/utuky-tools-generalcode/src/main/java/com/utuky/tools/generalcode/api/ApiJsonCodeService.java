package com.utuky.tools.generalcode.api;

import java.util.Map;

import com.utuky.commons.tools.json.JSONUtil;
import com.utuky.commons.tools.utils.ObjectTypeUtil;
import com.utuky.tools.generalcode.model.RespCodeEnum;
import com.utuky.tools.generalcode.model.RespData;

public class ApiJsonCodeService {

	public RespData generalCode(String json,Map<String,Object> config) {
		RespData result = new RespData(RespCodeEnum.DEFAULT_ERROR.getCode(),RespCodeEnum.DEFAULT_ERROR.getMsg());
		try {
			Map<String,Object> mapdata = JSONUtil.json2Obj(json, Map.class, null) ;
			if(mapdata==null) {
				result.setRespData(RespCodeEnum.DATA_NO_EXISTS.getCode(), RespCodeEnum.DATA_NO_EXISTS.getMsg());
				return result ;
			}
			for(String key:mapdata.keySet()) {
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setRespData(99, e.getMessage());
		}
		return result;
	}
	
	protected String makeClassCode(Map<String,Object> classAttributeMap,Map<String,String> config) {
		StringBuffer sb = new StringBuffer();
		
		return sb.toString();
	}
	
	protected String getObjectType(Object obj) {
		return ObjectTypeUtil.getBaseType(obj);
	}
}
