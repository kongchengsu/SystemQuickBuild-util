package com.jtexplorer.util;



import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 蒋鹏程 on 2016/9/1.
 */
public class TransportUtil {
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final String DAFAULT_CHARSET = HTTP.UTF_8;
    public static final String WXPAYSDK_VERSION = "WXPaySDK/3.0.9";
    public static final String USER_AGENT = WXPAYSDK_VERSION +
            " (" + System.getProperty("os.arch") + " " + System.getProperty("os.name") + " " + System.getProperty("os.version") +
            ") Java/" + System.getProperty("java.version") + " HttpClient/" + HttpClient.class.getPackage().getImplementationVersion();

    /**
     * 根据 参数 通过get获取网页源码
     *
     * @param url      网页url
     * @param params   参数
     * @param headers  请求头
     * @param encoding 编码  默认UTF-8
     * @return 网页源码
     * @throws IOException
     * @throws ClientProtocolException
     */
    public static Map doBasisGetObject(String url, Map<String, String> params,
                                       Map<String, String> headers, String encoding) throws IOException {
        Map map = new HashMap();
        JSONObject jsonObject = new JSONObject();
        String context;
        if (StringUtil.isEmpty(encoding))//设置默认编码
        {
            encoding = DAFAULT_CHARSET;
        }
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String urlAndParams = getUrlAndParams(url, params);
        HttpGet httpGet = new HttpGet(urlAndParams);//构建httpGet
        if (!ObjectUtil.isNullOrEmpty(headers))//设置请求头
        {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpGet.addHeader(entry.getKey(), entry.getValue());//设置请求头
            }
        }
        //执行get方法
        CloseableHttpResponse response1 = httpclient.execute(httpGet);
        HttpEntity entity1 = response1.getEntity();//获取返回结果
        int code = response1.getStatusLine().getStatusCode();
        context = getContextFromHttpEntity(entity1, encoding);
//        System.out.println(context);
        EntityUtils.consume(entity1);//销毁

        jsonObject = JSONObject.fromObject(context);
        map.put("code", code);
        map.put("context", jsonObject);
//        System.out.println(map);
        return map;
    }


    public static JSONObject doHttpGet(String url, Map<String, String> params,
                                       Map<String, String> headers, String encoding) throws IOException {
        JSONObject jsonObject = new JSONObject();
        String context;
        if (StringUtil.isEmpty(encoding))//设置默认编码
        {
            encoding = DAFAULT_CHARSET;
        }
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String urlAndParams = getUrlAndParams(url, params);
        HttpGet httpGet = new HttpGet(urlAndParams);//构建httpGet
        if (!ObjectUtil.isNullOrEmpty(headers))//设置请求头
        {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpGet.addHeader(entry.getKey(), entry.getValue());//设置请求头
            }
        }
        //执行get方法
        CloseableHttpResponse response1 = httpclient.execute(httpGet);
        HttpEntity entity1 = response1.getEntity();//获取返回结果
        int code = response1.getStatusLine().getStatusCode();
        context = getContextFromHttpEntity(entity1, encoding);
        EntityUtils.consume(entity1);//销毁

        jsonObject = JSONObject.fromObject(context);
        return jsonObject;
    }

    public static Map doBasisPostJson(String url, JSONObject json, String encoding) throws IOException {
        Map map = new HashMap();
        JSONObject jsonObject = new JSONObject();
        String context;
        if (StringUtil.isEmpty(encoding))//设置默认编码
        {
            encoding = DAFAULT_CHARSET;
        }
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        //调用封装方法
        StringEntity stringEntity = new StringEntity(json.toString(), encoding);
        stringEntity.setContentEncoding(encoding);
        stringEntity.setContentType("application/json;charset=utf-8");
        httpPost.setEntity(stringEntity);
        String str = getContextFromHttpEntity(httpPost.getEntity(), encoding);
//        System.out.print("json:" + str);
        CloseableHttpResponse response2 = httpclient.execute(httpPost);
        int code = response2.getStatusLine().getStatusCode();
        HttpEntity entity2 = response2.getEntity();
        context = getContextFromHttpEntity(entity2, encoding);
        EntityUtils.consume(entity2);
//        System.out.println(context);
        if (code == 200) {
            if (context == null || "".equals(context)) {
                map.put("code", code);
                map.put("context", "成功");
            } else {
                jsonObject = JSONObject.fromObject(context);
                map.put("code", code);
                map.put("context", jsonObject);
            }

        } else {
            jsonObject = JSONObject.fromObject(context);
            map.put("code", code);
            map.put("context", jsonObject);
        }
        return map;
    }

    /**
     * xml格式参数的接口请求方法
     *
     * @param url 接口地址
     * @param data xml格式的字符串形式的参数
     * @param mchId 可能用于证书验证
     * @return String
     */
    public static  String doBasisPostJsonWithXmlParam(String url, String data,String mchId){
        BasicHttpClientConnectionManager connManager;
        connManager = new BasicHttpClientConnectionManager(
                RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", PlainConnectionSocketFactory.getSocketFactory())
                        .register("https", SSLConnectionSocketFactory.getSocketFactory())
                        .build(),
                null,
                null,
                null
        );
        HttpClient httpClient = HttpClientBuilder.create()
                .setConnectionManager(connManager)
                .build();
        HttpPost httpPost = new HttpPost(url);
        //调用封装方法
        StringEntity postEntity = new StringEntity(data, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.addHeader("User-Agent", USER_AGENT + " " + mchId);
        httpPost.setEntity(postEntity);
        HttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            return EntityUtils.toString(httpEntity, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据响应内容和编码获取返回的内容
     *
     * @param entity   返回entity
     * @param encoding 编码
     * @return 返回的内容
     * @throws IOException
     * @throws UnsupportedOperationException
     * @throws UnsupportedEncodingException
     */
    public static String getContextFromHttpEntity(HttpEntity entity, String encoding) throws UnsupportedEncodingException, UnsupportedOperationException, IOException {

        if (entity.getContentLength() > 0)//获取获取的内容不为空
        {
            //读取页面源码
            BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), encoding));
            StringBuilder sb = new StringBuilder(100);
            String line;
            while ((line = br.readLine()) != null) {

                sb.append(line).append(LINE_SEPARATOR);
            }
            br.close();//别忘了关流
            return sb.toString();
        }
        return "";
    }

    public static String getUrlAndParams(String url, Map<String, String> params) {
        StringBuilder sb = new StringBuilder(50);//拼接url
        sb.append(url);//网址加?
        if (params != null && !params.isEmpty()) {
            sb.append("?");

            for (Map.Entry<String, String> entry : params.entrySet()) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
            String urlGet = sb.toString();
            return urlGet.substring(0, urlGet.length() - 1);
        }
        return url;
    }


    private static final String APPLICATION_JSON = "application/json";

    private static final String CONTENT_TYPE_TEXT_JSON = "text/json";

    public static void httpPostWithJSON(String url, String json) throws Exception {
        // 将JSON进行UTF-8编码,以便传输中文
        String encoderJson = URLEncoder.encode(json, HTTP.UTF_8);

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);

        StringEntity se = new StringEntity(encoderJson);
        se.setContentType(CONTENT_TYPE_TEXT_JSON);
        se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
        httpPost.setEntity(se);
        httpClient.execute(httpPost);


    }



}
