package com.utuky.tools.generalcode.api;

import java.io.File;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

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
		String privateStr = "private";
		String symbol = ";";
		String split = " ";
		String br = "\n";
		for(String key:classAttributeMap.keySet()) {
			String type = this.getObjectType(classAttributeMap.get(key));
			if(StringUtils.isBlank(type)) {
				
			}
			sb.append(privateStr+split+type+split+key+split+symbol+br) ;
		}
		return sb.toString();
	}
	
	protected String getPackageCode(String packagename) {
		return "package "+packagename + " ;" ;
	}
	
	protected String getImportClassname(String packagename,String classname) {
		String result = "";
		if(StringUtils.isNotBlank(packagename)) result = packagename+"."+classname;
		else result = classname ;
		result = "import "+result ;
		return result ;
	}
	
	protected String getClassCode() {
		StringBuffer result = new StringBuffer();
		
		return result.toString();
	}
	
	private String getGetMethodCode() {
		StringBuffer result = new StringBuffer() ;
		
		return result.toString() ;
	}
	
	protected boolean createCodeFile(String code,String rootpath,String packagename,String classname) {
		boolean b = false ;
		File path = new File(rootpath);
		//TODO
		
		return b ;
	}
	
	protected String getObjectType(Object obj) {
		return ObjectTypeUtil.getBaseType(obj);
	}
}
