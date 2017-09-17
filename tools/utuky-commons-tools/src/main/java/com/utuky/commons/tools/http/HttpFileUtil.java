package com.utuky.commons.tools.http;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpClientParams;

public class HttpFileUtil {

	/**
	 * POST发送文件
	 * 
	 * @param url
	 * @param headers
	 * @param params
	 * @param filePaths
	 * @param timeOut
	 * @return
	 * @throws Exception
	 */
	public static String send(String url, Map<String, String> headers, Map<String, String> params, List<String> filePaths, int timeOut) {
		PostMethod postMethod = new PostMethod(url);
		try {
			// 设置HTTP头信息
			postMethod.setRequestHeader("Connection", "close");
			if (headers != null && headers.size() > 0) {
				for (String key : headers.keySet()) {
					postMethod.setRequestHeader(key, headers.get(key));
				}
			}
			List<Part> partList = new ArrayList<Part>();
			// 设置普通参数
			if (params != null && params.size() > 0) {
				for (String key : params.keySet()) {
					Part part = new StringPart(key, params.get(key));
					partList.add(part);
				}
			}
			// 设置文件参数
			if (filePaths != null) {
				for (String filePath : filePaths) {
					File file = new File(filePath);
					Part part = new FilePart(file.getName(), file);
					partList.add(part);
				}
			}
			if (partList != null && partList.size() > 0) {
				Part[] parts = new Part[partList.size()];
				partList.toArray(parts);
				MultipartRequestEntity entity = new MultipartRequestEntity(parts, postMethod.getParams());
				postMethod.setRequestEntity(entity);
			}
			HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
			client.getHttpConnectionManager().getParams().setConnectionTimeout(timeOut);
			client.executeMethod(postMethod);
			return new String(postMethod.getResponseBody(), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
		}
		return "";
	}

	public static boolean downloadNet(String urlStr, String filePath) {
		// 下载网络文件
		int byteread = 0;

		try {
			URL url = new URL(urlStr);
			URLConnection conn = url.openConnection();

			InputStream inStream = conn.getInputStream();
			FileOutputStream fs = new FileOutputStream(filePath);

			byte[] buffer = new byte[1204];
			while ((byteread = inStream.read(buffer)) != -1) {
				fs.write(buffer, 0, byteread);
			}

			fs.flush();
			fs.close();
		} catch (FileNotFoundException e) {
			System.out.println("未找到待下载的文件");
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
