package com.utuky.commons.tools.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileReadUtil {

	/**
	 * 通过输入流来读取文件，比较适合读取较大的文件
	 * 
	 * @param filePath
	 * @return
	 */
	public static List<String> readByInStream(String filePath) {
		List<String> lines = new ArrayList<String>();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), "UTF-8"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lines;
	}

	/**
	 * 通过FileReader读取文件
	 * 
	 * @param filePath
	 * @return
	 */
	public static List<String> readByReader(String filePath) {
		try {
			File file = new File(filePath);
			BufferedReader br = new BufferedReader(new FileReader(file));
			List<String> infos = new ArrayList<String>();
			String line = br.readLine();
			while (line != null) {
				infos.add(line);
				line = br.readLine();
			}
			br.close();
			return infos;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
