package com.utuky.commons.tools.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

	public static Workbook build(InputStream is){
		Workbook workbook = null ;
		if(!is.markSupported()) {
			is = new PushbackInputStream(is,8);
		}
		try {
			if(POIFSFileSystem.hasPOIFSHeader(is)) {  
				workbook = new HSSFWorkbook(is);  
			}
			if(POIXMLDocument.hasOOXMLHeader(is)) {  
				workbook = new XSSFWorkbook(OPCPackage.open(is));  
			}
		}
		catch (InvalidFormatException | IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		} 
		return workbook;
	}
}
