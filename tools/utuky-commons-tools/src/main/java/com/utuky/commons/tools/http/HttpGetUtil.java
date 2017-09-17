package com.utuky.commons.tools.http;

import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;

public class HttpGetUtil {

	public static String send(String url, Map<String, String> headers, String body, int timeOut) {
		GetMethod getMethod = new GetMethod(url);
		try {
			// 设置HTTP头信息
			getMethod.setRequestHeader("Connection", "close");
			if (headers != null && headers.size() > 0) {
				for (String key : headers.keySet()) {
					getMethod.setRequestHeader(key, headers.get(key));
				}
			}
			// 设置包体
			HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
			client.getHttpConnectionManager().getParams().setConnectionTimeout(timeOut);
			client.getHttpConnectionManager().getParams().setSoTimeout(timeOut);
			client.executeMethod(getMethod);
			String result = new String(getMethod.getResponseBody(), "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(System.currentTimeMillis());
		} finally {
			getMethod.releaseConnection();
		}
		return "";
	}

	public static String send(String url, Map<String, String> headers, String body, String encode, int timeOut) {
		GetMethod getMethod = new GetMethod(url);
		try {
			// 设置HTTP头信息
			getMethod.setRequestHeader("Connection", "close");
			if (headers != null && headers.size() > 0) {
				for (String key : headers.keySet()) {
					getMethod.setRequestHeader(key, headers.get(key));
				}
			}
			// 设置包体
			HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
			client.getHttpConnectionManager().getParams().setConnectionTimeout(timeOut);
			client.getHttpConnectionManager().getParams().setSoTimeout(timeOut);
			client.executeMethod(getMethod);
			String result = new String(getMethod.getResponseBody(), encode);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			getMethod.releaseConnection();
		}
		return "";
	}

	public static String sendMp3(String url, Map<String, String> headers, String body, int timeOut) {
		GetMethod getMethod = new GetMethod(url);
		try {
			// 设置HTTP头信息
			getMethod.setRequestHeader("Connection", "close");
			if (headers != null && headers.size() > 0) {
				for (String key : headers.keySet()) {
					getMethod.setRequestHeader(key, headers.get(key));
				}
			}
			// 设置包体
			HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
			if (timeOut != 0) {
				client.getHttpConnectionManager().getParams().setConnectionTimeout(timeOut);
				client.getHttpConnectionManager().getParams().setSoTimeout(timeOut);
			}
			client.executeMethod(getMethod);
			int code = getMethod.getStatusCode();
			if (code != 200) {
				return "5";
			}
			long size = getMethod.getResponseContentLength();
			if (size < 100) {
				return "6";
			}
			return "0";
		} catch (Exception e) {
			e.printStackTrace();
			return "8";
		} finally {
			getMethod.releaseConnection();
		}
	}

}
