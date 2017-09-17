package com.utuky.commons.tools.address;

import java.net.URLEncoder;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.ll.commons.commonlib.http.HttpGetUtil;
import com.ll.commons.commonlib.json.JsonUtil;

/**
 * @since 2017-09-17
 * @author Frain
 *
 */
public class BaiduAddressUtil {

	private static String SUGGESTION_URL="http://api.map.baidu.com/place/v2/suggestion?query=%s&region=%s&output=json&ak=6858012dc1ce9bbf845b3ab3e82b8ba3";
	private static String DETAIL_URL="http://api.map.baidu.com/place/v2/detail?uid=%s&output=json&scope=1&ak=6858012dc1ce9bbf845b3ab3e82b8ba3";
	
	/**
	 * 获取百度的地址数据列表
	 * @param region
	 * @param address
	 * @return
	 */
	public static RespBaiduAddress<List<BaiduAddress>> getAddressList(String region,String address){
		String url = null;
		try {
			url = String.format(SUGGESTION_URL, URLEncoder.encode(address,"utf8"),URLEncoder.encode(region,"utf8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		String respdata = HttpGetUtil.send(url, null, "", 20000);
		System.out.println(respdata);
		RespBaiduAddress<List<BaiduAddress>> result = JsonUtil.json2Obj(respdata, new TypeToken<RespBaiduAddress<List<BaiduAddress>>>() {
		}.getType());
		return result;
	}
	/**
	 * 获取百度的详细地址
	 * @param uid
	 * @return
	 */
	public static RespBaiduAddress<BaiduAddress> getAddress(String uid) {
		String url = String.format(DETAIL_URL,uid);
		String respdata = HttpGetUtil.send(url, null, "", 20000);
		System.out.println(respdata);
		RespBaiduAddress<BaiduAddress> result = JsonUtil.json2Obj(respdata, new TypeToken<RespBaiduAddress<BaiduAddress>>() {
		}.getType());
		return result;
	}
	// http://api.map.baidu.com/place/v2/suggestion?query=%E5%94%90%E5%B1%B1%E5%A4%A7%E5%8E%A6&region=%E5%8C%97%E4%BA%AC&output=json&ak=6858012dc1ce9bbf845b3ab3e82b8ba3
	public static void main(String[] args) {
		RespBaiduAddress<List<BaiduAddress>> result = BaiduAddressUtil.getAddressList("北京", "唐山大厦");
		System.out.println(result);
		RespBaiduAddress<BaiduAddress> detail = BaiduAddressUtil.getAddress(result.getResult().get(0).getUid());
		System.out.println(detail);
	}
}
