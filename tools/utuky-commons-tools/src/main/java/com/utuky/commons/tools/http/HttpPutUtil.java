package com.utuky.commons.tools.http;

import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpClientParams;

public class HttpPutUtil {

	public static String send(String url, Map<String, String> headers, String body, int timeOut) {
		PutMethod putMethod = new PutMethod(url);
		try {
			// 设置HTTP头信息
			putMethod.setRequestHeader("Connection", "close");
			if (headers != null && headers.size() > 0) {
				for (String key : headers.keySet()) {
					putMethod.setRequestHeader(key, headers.get(key));
				}
			}
			// 设置包体
			StringRequestEntity entity = new StringRequestEntity(body, "text/json", "UTF-8");
			putMethod.setRequestEntity(entity);
			HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
			client.getHttpConnectionManager().getParams().setConnectionTimeout(timeOut);
			client.executeMethod(putMethod);
			String result = putMethod.getResponseBodyAsString();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			putMethod.releaseConnection();
		}
		return "";
	}

}
