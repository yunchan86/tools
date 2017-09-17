package com.utuky.commons.tools.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpsUtil {

	/**
	 * 发送消息
	 * 
	 * @param url
	 * @param headers
	 * @param body
	 * @param keyPath
	 * @param keyPwd
	 * @return
	 */
	public static String sendPost(String url, Map<String, String> headers, String body, String keyPath, String keyPwd) {
		String result = "";
		CloseableHttpClient httpClient = null;
		try {
			SSLConnectionSocketFactory sslConnFactory = getSSLConnFactory(keyPath, keyPwd);
			if (sslConnFactory != null) {
				httpClient = HttpClients.custom().setSSLSocketFactory(sslConnFactory).build();
				HttpPost post = new HttpPost(url);
				RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();
				post.setConfig(requestConfig);
				if (headers != null) {
					for (String key : headers.keySet()) {
						post.setHeader(key, headers.get(key));
					}
				}
				post.setEntity(new StringEntity(body, "UTF-8"));
				HttpResponse response = httpClient.execute(post);
				HttpEntity entity = response.getEntity();
				if (null != entity) {
					result = EntityUtils.toString(entity, "UTF-8");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (httpClient != null) {
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/**
	 * 证书处理
	 * 
	 * @param keyPath
	 * @param keyPwd
	 * @return
	 */
	private static SSLConnectionSocketFactory getSSLConnFactory(String keyPath, String keyPwd) {
		SSLContext sslContext = null;
		FileInputStream instream = null;
		KeyStore trustStore = null;
		SSLConnectionSocketFactory sslConnFactory = null;
		try {
			trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			instream = new FileInputStream(new File(keyPath));
			trustStore.load(instream, keyPwd.toCharArray());
			// 相信自己的CA和所有自签名的证书
			sslContext = SSLContexts.custom().loadTrustMaterial(trustStore, new TrustSelfSignedStrategy()).build();
			sslConnFactory = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(instream!=null){
					instream.close();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sslConnFactory;
	}

	public static Map<String, String> sendForForm(String url, Map<String, String> headers, Map<String, Object> formParams, int timeOut) {
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				// 信任所有
				@Override
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
			CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			HttpPost post = new HttpPost(url);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeOut).setConnectTimeout(timeOut).build();
			post.setConfig(requestConfig);
			if (headers != null) {
				for (String key : headers.keySet()) {
					post.setHeader(key, headers.get(key));
				}
			}
			MultipartEntityBuilder entity = MultipartEntityBuilder.create();
			entity = entity.setCharset(Charset.forName("UTF-8"));
			entity = entity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			for (Entry<String, Object> parameter : formParams.entrySet()) {
				if (parameter.getValue() instanceof File) {
					entity = entity.addPart(parameter.getKey(), new FileBody((File) parameter.getValue()));
				} else if (parameter.getValue() != null) {
					entity = entity.addPart(parameter.getKey(),
							new StringBody(parameter.getValue().toString(), ContentType.create("multipart/form-data", Consts.UTF_8)));
				}
			}
			post.setEntity(entity.build());
			HttpResponse response = httpClient.execute(post);
			resultMap.put("status", String.valueOf(response.getStatusLine().getStatusCode()));
			HttpEntity returnEntity = response.getEntity();
			if (null != returnEntity) {
				resultMap.put("body", EntityUtils.toString(returnEntity, "UTF-8"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	public static Map<String, String> sendPost(String url, Map<String, String> headers, String body, int timeOut) {
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				// 信任所有
				@Override
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
			CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			HttpPost post = new HttpPost(url);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeOut).setConnectTimeout(timeOut).build();
			post.setConfig(requestConfig);
			if (headers != null) {
				for (String key : headers.keySet()) {
					post.setHeader(key, headers.get(key));
				}
			}
			post.setEntity(new StringEntity(body, "UTF-8"));
			HttpResponse response = httpClient.execute(post);
			HttpEntity entity = response.getEntity();
			resultMap.put("status", String.valueOf(response.getStatusLine().getStatusCode()));
			if (null != entity) {
				resultMap.put("body", EntityUtils.toString(entity, "UTF-8"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	public static Map<String, String> sendGet(String url, Map<String, String> headers, int timeOut) {
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				// 信任所有
				@Override
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
			CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			HttpGet httpGet = new HttpGet(url);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeOut).setConnectTimeout(timeOut).build();
			httpGet.setConfig(requestConfig);
			if (headers != null) {
				for (String key : headers.keySet()) {
					httpGet.setHeader(key, headers.get(key));
				}
			}
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			resultMap.put("status", String.valueOf(response.getStatusLine().getStatusCode()));
			if (null != entity) {
				resultMap.put("body", EntityUtils.toString(entity, "UTF-8"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	public static Map<String, String> sendPut(String url, Map<String, String> headers, String body, int timeOut) {
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				// 信任所有
				@Override
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
			CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			HttpPut httpPut = new HttpPut(url);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeOut).setConnectTimeout(timeOut).build();
			httpPut.setConfig(requestConfig);
			if (headers != null) {
				for (String key : headers.keySet()) {
					httpPut.setHeader(key, headers.get(key));
				}
			}
			httpPut.setEntity(new StringEntity(body, "UTF-8"));
			HttpResponse response = httpClient.execute(httpPut);
			HttpEntity entity = response.getEntity();
			resultMap.put("status", String.valueOf(response.getStatusLine().getStatusCode()));
			if (null != entity) {
				resultMap.put("body", EntityUtils.toString(entity, "UTF-8"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

	public static Map<String, String> sendDelete(String url, Map<String, String> headers, int timeOut) {
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				// 信任所有
				@Override
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
			CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			HttpDelete httpDelet = new HttpDelete(url);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeOut).setConnectTimeout(timeOut).build();
			httpDelet.setConfig(requestConfig);
			if (headers != null) {
				for (String key : headers.keySet()) {
					httpDelet.setHeader(key, headers.get(key));
				}
			}
			HttpResponse response = httpClient.execute(httpDelet);
			HttpEntity entity = response.getEntity();
			resultMap.put("status", String.valueOf(response.getStatusLine().getStatusCode()));
			if (null != entity) {
				resultMap.put("body", EntityUtils.toString(entity, "UTF-8"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}
	
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
		String result = "";
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				// 信任所有
				@Override
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
			CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			HttpPost post = new HttpPost(url);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();
			post.setConfig(requestConfig);
			if (headers != null) {
				for (String key : headers.keySet()) {
					post.setHeader(key, headers.get(key));
				}
			}
			if (params != null) {
				// 创建参数队列
				List<NameValuePair> formparams = new ArrayList<NameValuePair>();
				for (String key : params.keySet()) {
					formparams.add(new BasicNameValuePair(key, params.get(key)));
				}
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");

				post.setEntity(entity);
			}
			HttpResponse response = httpClient.execute(post);
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				result = EntityUtils.toString(entity, "UTF-8");
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
