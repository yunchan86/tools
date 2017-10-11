package com.utuky.tools.generalcode.api;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.utuky.commons.tools.json.JSONUtil;
import com.utuky.commons.tools.utils.ObjectTypeUtil;
import com.utuky.commons.tools.utils.StringUtils;
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
	
	protected Map<String,String> makeClassCode(Map<String,Object> classAttributeMap,Map<String,String> config) {
		Map<String,String> result = new HashMap<String,String>();
		StringBuffer sb = new StringBuffer();
		Map<String,String> attributeMap = new HashMap<String,String>();
		for(String key:classAttributeMap.keySet()) {
			String type = this.getObjectType(classAttributeMap.get(key));
			if(StringUtils.isBlank(type)) {//非基本数据类型
				//do nothing
			}else {
				if(config==null || config.get(key)==null) type = StringUtils.firstUpperCase(type);
				type = StringUtils.firstUpperCase(config.get(key)) ;
			}
			attributeMap.put(key, type);
		}
		String classname = config.get("classname");
		sb.append(getClassCode(classname,attributeMap)) ;
		result.put(classname, sb.toString()) ;
		return result ;
	}
	
	protected String getPackageCode(String packagename) {
		StringBuffer result = new StringBuffer();
		if(StringUtils.isNotBlank(packagename)) result.append("package "+packagename + ";\n" ) ;
		return result.toString();
	}
	
	protected String getImportClassname(String packagename,String classname) {
		StringBuffer result = new StringBuffer();
		if(StringUtils.isBlank(classname)) result.append("\n");
		else if(StringUtils.isNotBlank(packagename)) result.append("import "+packagename+"."+classname+";\n");
		else result.append("\n");
		return result.toString() ;
	}
	
	protected String getClassCode(String classname,Map<String,String> classAttributeConfig) {
		StringBuffer result = new StringBuffer();
		String tabs = "";
		result.append(tabs+"public class "+classname+" {\n") ;
		if(classAttributeConfig!=null) {
			for(String attribute:classAttributeConfig.keySet()) {
				String attributeType = classAttributeConfig.get(attribute);
				result.append(tabs+"\t"+"private "+attributeType+" "+attribute+";\n") ;
			}
			for(String attribute:classAttributeConfig.keySet()) {
				String attributeType = classAttributeConfig.get(attribute);
				String getMethod = getGetMethodCode(attribute,attributeType);
				String setMethod = getSetMethodCode(attribute,attributeType);
				result.append(getMethod);
				result.append(setMethod);
			}
		}
		result.append("}\n");
		return result.toString();
	}
	
	private String getGetMethodCode(String attribute,String attributeType) {
		StringBuffer result = new StringBuffer() ;
		String tabs = "";
		String newAttribute = StringUtils.firstUpperCase(attribute);
		result.append(tabs+"public "+attributeType+" get"+newAttribute+"() {"+"\n") ;
		result.append(tabs+"\t"+"return"+attribute+";\n") ;
		result.append(tabs+"}\n") ;
		return result.toString() ;
	}
	private String getSetMethodCode(String attribute,String attributeType) {
		StringBuffer result = new StringBuffer() ;
		String tabs = "";
		String newAttribute = StringUtils.firstUpperCase(attribute);
		result.append(tabs+"public void set"+newAttribute+"("+attributeType+" "+attribute+") {"+"\n") ;
		result.append(tabs+"\t"+"this."+attribute+"="+attribute+";\n") ;
		result.append(tabs+"}\n") ;
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
