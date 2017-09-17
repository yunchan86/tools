package com.utuky.commons.tools.http;

import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.params.HttpClientParams;

public class HttpDeleteUtil {

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
	public static String send(String url, Map<String, String> headers, String body, int timeOut) throws Exception {
		DeleteMethod deleteMethod = new DeleteMethod(url);
		try {
			// 设置HTTP头信息
			deleteMethod.setRequestHeader("Connection", "close");
			if (headers != null && headers.size() > 0) {
				for (String key : headers.keySet()) {
					deleteMethod.setRequestHeader(key, headers.get(key));
				}
			}
			// 设置包体
			HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
			client.getHttpConnectionManager().getParams().setConnectionTimeout(timeOut);
			int status = client.executeMethod(deleteMethod);
			if (status == 200) {
				String result = deleteMethod.getResponseBodyAsString();
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			deleteMethod.releaseConnection();
		}
		return "";
	}

}
