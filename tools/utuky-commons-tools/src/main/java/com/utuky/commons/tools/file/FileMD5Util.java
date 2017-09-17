package com.utuky.commons.tools.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

public class FileMD5Util {

	private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
			'e', 'f' };

	/**
	 * MD5校验
	 * 
	 * @param filename
	 *            文件名
	 * @return
	 */
	public static String md5sum(String filename) {
		InputStream fis = null;
		byte[] buffer = new byte[1024];
		int numRead = 0;
		MessageDigest md5;
		try {
			fis = new FileInputStream(filename);
			md5 = MessageDigest.getInstance("MD5");
			while ((numRead = fis.read(buffer)) > 0) {
				md5.update(buffer, 0, numRead);
			}
			return toHexString(md5.digest());
		} catch (Exception e) {
			return null;
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				fis = null;
			}
		}
	}

	public static String md5InputStream(InputStream fis) {
		if (fis != null) {
			byte[] buffer = new byte[1024];
			int numRead = 0;
			MessageDigest md5;
			try {
				md5 = MessageDigest.getInstance("MD5");
				while ((numRead = fis.read(buffer)) > 0) {
					md5.update(buffer, 0, numRead);
				}
				return toHexString(md5.digest());
			} catch (Exception e) {
				return null;
			} finally {
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					fis = null;
				}
			}

		}
		return null;
	}

	public static String md5File(String path) {
		return md5File(new File(path));
	}

	public static String md5File(File file) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			return md5InputStream(fis);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
				}
			}
		}
	}

	public static String toHexString(byte[] b) {
		StringBuilder sb = new StringBuilder(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);
			sb.append(HEX_DIGITS[b[i] & 0x0f]);
		}
		return sb.toString();
	}
}
