package com.utuky.commons.tools.file;

import java.io.File;

public class FileOperUtil {

	/**
	 * 删除文件夹
	 * 
	 * @param dirPath
	 */
	public static void delDir(String dirPath) {
		try {
			File file = new File(dirPath);
			if (file.isDirectory()) {
				File[] subFiles = file.listFiles();
				if (subFiles != null && subFiles.length > 0) {
					for (File subFile : subFiles) {
						subFile.delete();
					}
				}

				file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除指定文件
	 * 
	 * @param filePath
	 */
	public static void delFile(String filePath) {
		try {
			File file = new File(filePath);
			if (file.exists()) {
				file.delete();
				System.out.println("文件：" + filePath + "删除完成");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
