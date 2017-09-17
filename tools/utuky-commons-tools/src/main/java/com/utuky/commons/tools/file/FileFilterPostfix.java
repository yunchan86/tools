package com.utuky.commons.tools.file;

import java.io.File;
import java.io.FileFilter;

public class FileFilterPostfix implements FileFilter {
	
	private String postfix[];
	
	public FileFilterPostfix(String postfix[]){
		this.postfix = postfix;
	}

	@Override
	public boolean accept(File file) {
		boolean result = false ;
		if(postfix==null||postfix.length==0) return result ;
		if(file.isDirectory()) return result ;
		for(String str:postfix) {
			if(file.getName().endsWith(str)) {
				result = true ;
				break;
			}
		}
		return result;
	}

	public static void main(String[] args) {
		File file = new File("C:\\work\\admin\\radioupdatetool\\data\\qingting");
		File filelist[] = file.listFiles(new FileFilterPostfix(new String[]{"txt"}));
		for(File item : filelist) {
			System.out.println(item.getAbsolutePath());
		}
	}
}
