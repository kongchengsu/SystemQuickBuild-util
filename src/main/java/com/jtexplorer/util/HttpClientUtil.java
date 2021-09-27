package com.jtexplorer.util;

import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HttpClient封装 class
 *
 * @author 明明如月
 * @author 苏友朋
 * @date 2018/4/11 9:23
 * @date 2019/03/09 10:34
 */
public class HttpClientUtil {

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final String DEFAULT_CHARSET = StandardCharsets.UTF_8.toString();

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            //设置通用的请求属性
            conn.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:21.0) Gecko/20100101 Firefox/21.0)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            OutputStreamWriter outWriter = new OutputStreamWriter(conn.getOutputStream(), StandardCharsets.UTF_8);
            out = new PrintWriter(outWriter);
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = in.readLine()) != null) {
                in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                stringBuilder.append(line);
            }
            result = stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * get请求
     * @return
     */
    public static String doGet(String url) {
        try {
            HttpClient client = new DefaultHttpClient();
            //发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);

            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                String strResult = EntityUtils.toString(response.getEntity());

                return strResult;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 根据 参数 通过get获取网页源码
     *
     * @param url      网页url
     * @param params   参数
     * @param headers  请求头
     * @param encoding 编码  默认UTF-8
     * @return 网页源码
     * @throws IOException ClientProtocolException
     */
    public static String doBasisGet(String url, Map<String, String> params,
                                    Map<String, String> headers, String encoding) throws IOException {
        String context;
        // 设置默认编码
        if (StringUtil.isEmpty(encoding)) {
            encoding = DEFAULT_CHARSET;
        }
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String urlAndParams = getUrlAndParams(url, params);
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
        CloseableHttpResponse response1 = httpclient.execute(httpGet);
        //获取返回结果
        HttpEntity entity1 = response1.getEntity();
        context = getContextFromHttpEntity(entity1, encoding);
        //销毁
        EntityUtils.consume(entity1);
        return context;
    }

    /**
     * 模拟post请求
     *
     * @param url      请求ur
     * @param params   参数
     * @param encoding 编码
     * @return String json等内容
     * @throws IOException 异常
     */
    public static String doBasisPost(String url, Map<String, String> params,
                                     String encoding) throws IOException {
        String context;
        //设置默认编码
        if (StringUtil.isEmpty(encoding)) {
            encoding = DEFAULT_CHARSET;
        }
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        //调用封装方法
        List<NameValuePair> nvps = getNameValuePair(params);
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));
        CloseableHttpResponse response2 = httpClient.execute(httpPost);
        HttpEntity entity2 = response2.getEntity();
        context = getContextFromHttpEntity(entity2, encoding);
        EntityUtils.consume(entity2);
        return context;
    }


    /**
     * 根据响应内容和编码获取返回的内容
     *
     * @param entity   返回entity
     * @param encoding 编码
     * @return 返回的内容
     * @throws IOException UnsupportedOperationException
     */
    static String getContextFromHttpEntity(HttpEntity entity, String encoding) throws UnsupportedOperationException, IOException {
        if (entity.getContentLength() > 0)
        {
            //获取获取的内容不为空
            //读取页面源码
            BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), encoding));
            StringBuilder sb = new StringBuilder(100);
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append(LINE_SEPARATOR);
            }
            //别忘了关流
            br.close();
            return sb.toString();
        }
        return "";
    }

    /**
     * 根据请求参数封装成 Post 参数对
     *
     * @param params 参数map
     * @return List<NameValuePair>
     */
    private static List<NameValuePair> getNameValuePair(Map<String, String> params) {
        List<NameValuePair> nvps = new ArrayList<>();
        //设置请求头
        if (!ObjectUtil.isNullOrEmpty(params)) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        return nvps;
    }

    /**
     * 根据 拼接请求url
     *
     * @param url    原始
     * @param params 请求参数map
     * @return String 拼接后的url
     */
    static String getUrlAndParams(String url, Map<String, String> params) {
        //拼接url
        StringBuilder sb = new StringBuilder(50);
        //网址加?
        sb.append(url);
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


    /**
     * 高德地图接口使用，直接拷贝原项目，需优化
     * @param url
     * @param params
     * @param encoding
     * @return
     * @throws IOException
     */
    public static Map doBasisGetObject(String url, Map<String, String> params,
                                       Map<String, String> headers, String encoding) throws IOException {
        Map map = new HashMap();
        JSONObject jsonObject = new JSONObject();
        String context;
        if (StringUtil.isEmpty(encoding))//设置默认编码
        {
            encoding = DEFAULT_CHARSET;
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
        System.out.println(context);
        EntityUtils.consume(entity1);//销毁
        jsonObject = JSONObject.fromObject(context);
        map.put("code", code);
        map.put("context", jsonObject);
        return map;
    }


}
