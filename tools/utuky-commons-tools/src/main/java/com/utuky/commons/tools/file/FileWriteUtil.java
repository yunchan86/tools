package com.utuky.commons.tools.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.List;

public class FileWriteUtil {
	/**
	 * 通过FileWriter写文件
	 * 
	 * @param filePath
	 * @param line
	 * @param isAppend
	 */
	public static void writeByWriter(String filePath, String data, boolean isAppend) {
		try {
			FileWriter fw = new FileWriter(filePath, isAppend);
			fw.write(data + "\r\n");
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 通过FileWriter批量写入数据
	 * 
	 * @param filePath
	 * @param datas
	 * @param isAppend
	 */
	public static void writeByWriter(String filePath, List<String> datas, boolean isAppend) {
		try {
			FileWriter fw = new FileWriter(filePath, isAppend);
			for (String data : datas) {
				fw.write(data + "\r\n");
			}
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 通过输出流来写文件
	 * 
	 * @param filePath
	 * @param datas
	 */
	public void writeByOutStream(String filePath, List<String> datas) {
		try {
			FileOutputStream out = new FileOutputStream(new File(filePath));
			for (String data : datas) {
				out.write((data + "\r\n").getBytes());
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
