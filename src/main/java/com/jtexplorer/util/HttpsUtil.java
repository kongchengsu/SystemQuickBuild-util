package com.jtexplorer.util;

import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * HttpsUtil class
 *
 * @author 蒋鹏程
 * @author 苏友朋
 * @date 2018/4/11 9:23
 * @date 2019/03/09 10:34
 */
public class HttpsUtil {
    /**
     * 根据 参数 通过get获取网页源码
     *
     * @param url      网页url
     * @param params   参数
     * @param headers  请求头
     * @param encoding 编码  默认UTF-8
     * @return 网页源码
     * @throws IOException 异常
     */
    public static Map doBasisGetObject(String url, Map<String, String> params,
                                       Map<String, String> headers, String encoding) throws IOException {
        Map<String, Object> map = new HashMap<>(16);
        JSONObject jsonObject;
        CloseableHttpClient httpClient = null;
        String context;
        // 设置默认编码
        if (StringUtil.isEmpty(encoding)) {
            encoding = HttpClientUtil.DEFAULT_CHARSET;
        }
        try {
            httpClient = new SSLClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String urlAndParams = HttpClientUtil.getUrlAndParams(url, params);
        //构建httpGet
        HttpGet httpGet = new HttpGet(urlAndParams);
        //设置请求头
        if (!ObjectUtil.isNullOrEmpty(headers)) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                //设置请求头
                httpGet.addHeader(entry.getKey(), entry.getValue());
            }
        }
        //执行get方法
        assert httpClient != null;
        CloseableHttpResponse response1 = httpClient.execute(httpGet);
        //获取返回结果
        HttpEntity entity1 = response1.getEntity();
        int code = response1.getStatusLine().getStatusCode();
        context = HttpClientUtil.getContextFromHttpEntity(entity1, encoding);
        //销毁
        EntityUtils.consume(entity1);
        jsonObject = JSONObject.fromObject(context);
        map.put("code", code);
        map.put("context", jsonObject);
        return map;
    }


    /**
     * 根据 参数 通过get获取网页源码,
     * 上面的方法使用到haspmap，因为hashmap非线程安全，所以直接返回json
     *
     * @param url      网页url
     * @param params   参数
     * @param headers  请求头
     * @param encoding 编码  默认UTF-8
     * @return 网页源码
     * @throws IOException 异常
     */
    public static JSONObject doBasisGetObjectWithJson(String url, Map<String, String> params,
                                                      Map<String, String> headers, String encoding) throws IOException {
        JSONObject jsonObject;
        CloseableHttpClient httpClient = null;
        String context;
        //设置默认编码
        if (StringUtil.isEmpty(encoding)) {
            encoding = HttpClientUtil.DEFAULT_CHARSET;
        }
        try {
            httpClient = new SSLClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String urlAndParams = HttpClientUtil.getUrlAndParams(url, params);
        //构建httpGet
        HttpGet httpGet = new HttpGet(urlAndParams);
        //设置请求头
        if (!ObjectUtil.isNullOrEmpty(headers)) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                //设置请求头
                httpGet.addHeader(entry.getKey(), entry.getValue());
            }
        }
        //执行get方法
        assert httpClient != null;
        CloseableHttpResponse response1 = httpClient.execute(httpGet);
        //获取返回结果
        HttpEntity entity1 = response1.getEntity();
        response1.getStatusLine().getStatusCode();
        context = HttpClientUtil.getContextFromHttpEntity(entity1, encoding);
        //销毁
        EntityUtils.consume(entity1);
        jsonObject = JSONObject.fromObject(context);
        return jsonObject;
    }


}

