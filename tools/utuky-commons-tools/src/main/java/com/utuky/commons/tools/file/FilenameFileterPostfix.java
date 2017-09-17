package com.utuky.commons.tools.file;

import java.io.File;
import java.io.FilenameFilter;

public class FilenameFileterPostfix implements FilenameFilter {
	
	String postfix[] = null ;
	
	public FilenameFileterPostfix(String[] postfix) {
		this.postfix = postfix;
	}

	@Override
	public boolean accept(File dir, String name) {
		boolean result = false ;
		if(postfix==null||postfix.length==0) return result ;
		for(String str:postfix) {
			if(name.endsWith(str)) {
				result = true ;
				break;
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		File file = new File("C:\\work\\admin\\radioupdatetool\\data\\qingting");
		String[] names = file.list(new FilenameFileterPostfix(new String[]{"txt"}));
		for(String item : names) {
			System.out.println(item);
		}
	}

}
