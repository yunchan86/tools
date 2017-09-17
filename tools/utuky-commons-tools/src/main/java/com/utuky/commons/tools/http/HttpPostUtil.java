package com.utuky.commons.tools.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.lang3.StringUtils;

public class HttpPostUtil {

	/**
	 * 发送表单请求
	 * 
	 * @param url
	 * @param headers
	 * @param params
	 * @param timeOut
	 * @return
	 * @throws Exception
	 */
	public static String sendForm(String url, Map<String, String> headers, Map<String, String> params, int timeOut) {
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
			if (partList != null && partList.size() > 0) {
				Part[] parts = new Part[partList.size()];
				partList.toArray(parts);
				MultipartRequestEntity entity = new MultipartRequestEntity(parts, postMethod.getParams());
				postMethod.setRequestEntity(entity);
			}
			HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
			client.getHttpConnectionManager().getParams().setConnectionTimeout(timeOut);
			client.executeMethod(postMethod);
			// // 打印服务器返回的状态
			// System.out.println(postMethod.getStatusLine());
			// // 打印返回的信息
			// System.out.println(postMethod.getResponseBodyAsString());
			return new String(postMethod.getResponseBody(), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			postMethod.abort();
			postMethod.releaseConnection();
		}
		return null;
	}

	/**
	 * 发送请求
	 * 
	 * @param url
	 * @param headers
	 * @param body
	 * @param timeOut
	 * @return
	 * @throws Exception
	 */
	public static String send(String url, Map<String, String> headers, String body, int timeOut) {
		PostMethod postMethod = new PostMethod(url);
		try {
			// 设置HTTP头信息
			postMethod.setRequestHeader("Connection", "close");
			if (headers != null && headers.size() > 0) {
				for (String key : headers.keySet()) {
					postMethod.setRequestHeader(key, headers.get(key));
				}
			}
			StringRequestEntity entity = new StringRequestEntity(body, "text/json", "UTF-8");
			postMethod.setRequestEntity(entity);
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

	/**
	 * 发送请求
	 * 
	 * @param url
	 * @param headers
	 * @param body
	 * @param contentType
	 * @param timeOut
	 * @return
	 */
	public static String send(String url, Map<String, String> headers, String body, String contentType, int timeOut) {
		PostMethod postMethod = new PostMethod(url);
		try {
			// 设置HTTP头信息
			postMethod.setRequestHeader("Connection", "close");
			if (headers != null && headers.size() > 0) {
				for (String key : headers.keySet()) {
					postMethod.setRequestHeader(key, headers.get(key));
				}
			}
			StringRequestEntity entity = new StringRequestEntity(body, contentType, "UTF-8");
			postMethod.setRequestEntity(entity);
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

	public static Map<String, String> sendByteForMap(String url, Map<String, String> headers, byte[] body, int timeOut) {
		return sendByteForMap(url, headers, body, null, timeOut);
	}

	public static Map<String, String> sendByteForMap(String url, Map<String, String> headers, byte[] body, String contentType, int timeOut) {
		PostMethod postMethod = new PostMethod(url);
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			postMethod.setRequestHeader("Connection", "close");
			if ((headers != null) && (headers.size() > 0)) {
				for (String key : headers.keySet()) {
					postMethod.setRequestHeader(key, headers.get(key));
				}
			}
			if (StringUtils.isBlank(contentType)) {
				contentType = "text/json";
			}
			ByteArrayRequestEntity entity = new ByteArrayRequestEntity(body, contentType);
			postMethod.setRequestEntity(entity);
			HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
			client.getHttpConnectionManager().getParams().setConnectionTimeout(timeOut);
			client.executeMethod(postMethod);
			resultMap.put("status", String.valueOf(postMethod.getStatusCode()));
			resultMap.put("body", new String(postMethod.getResponseBody(), "UTF-8"));

			return resultMap;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
		}
		return resultMap;
	}

	public static Map<String, String> sendForMap(String url, Map<String, String> headers, String body, int timeOut) {
		PostMethod postMethod = new PostMethod(url);
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			postMethod.setRequestHeader("Connection", "close");
			if ((headers != null) && (headers.size() > 0)) {
				for (String key : headers.keySet()) {
					postMethod.setRequestHeader(key, headers.get(key));
				}
			}
			StringRequestEntity entity = new StringRequestEntity(body, "text/json", "UTF-8");
			postMethod.setRequestEntity(entity);
			HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
			client.getHttpConnectionManager().getParams().setConnectionTimeout(timeOut);
			client.executeMethod(postMethod);
			resultMap.put("status", String.valueOf(postMethod.getStatusCode()));
			resultMap.put("body", new String(postMethod.getResponseBody(), "UTF-8"));

			return resultMap;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			postMethod.releaseConnection();
		}
		return resultMap;
	}

	public static Map<String, String> sendForMap(String url, Map<String, String> headers, String body, String contentType, int timeOut) throws Exception {
		PostMethod postMethod = new PostMethod(url);
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			postMethod.setRequestHeader("Connection", "close");
			if ((headers != null) && (headers.size() > 0)) {
				for (String key : headers.keySet()) {
					postMethod.setRequestHeader(key, headers.get(key));
				}
			}
			if (StringUtils.isBlank(contentType)) {
				contentType = "text/json";
			}
			StringRequestEntity entity = new StringRequestEntity(body, contentType, "UTF-8");
			postMethod.setRequestEntity(entity);
			HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
			client.getHttpConnectionManager().getParams().setConnectionTimeout(timeOut);
			client.executeMethod(postMethod);
			resultMap.put("status", String.valueOf(postMethod.getStatusCode()));
			resultMap.put("body", new String(postMethod.getResponseBody(), "UTF-8"));

			return resultMap;
		} finally {
			postMethod.releaseConnection();
		}
	}

	public static Map<String, String> sendForMap(String url, Map<String, String> headers, String body, int timeOut, int times, Set<String> statusSet) {
		Map<String, String> resultMap = sendForMap(url, headers, body, timeOut);
		while (resultMap != null && !resultMap.isEmpty()) {
			if (times-- > 0
					&& (statusSet == null && !"200".equals(resultMap.get("status")) || statusSet != null && statusSet.contains(resultMap.get("status")))) {
				resultMap = sendForMap(url, headers, body, timeOut);
			} else {
				break;
			}
		}
		return resultMap;
	}

	public static void main(String[] args) {
		// "http://153.36.230.193/ll-service-vboxres-0.0.1-SNAPSHOT/bus/refresh"
		// "http://153.36.230.193:8001/bus/refresh"
		System.out.println(HttpPostUtil.send("http://153.36.230.193:80/songres2/bus/refresh", null, "", 1000));
	}
}
