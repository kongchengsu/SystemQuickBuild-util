package com.jtexplorer.util;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.PI;

/**
 * Created by wang on 2017/6/7.
 */
@Slf4j
public class MapDistanceUtil {


    //  地球赤道半径，单位米
    private static double EARTH_RADIUS = 6378.137;

    private static double rad(double d) {
        return d * PI / 180.0;
    }

    /**
     * 根据两个位置的经纬度，来计算两地的距离（单位为KM）
     * 参数为String类型
     * @param lat1Str 用户1经度
     * @param lng1Str 用户1纬度
     * @param lat2Str 用户2经度
     * @param lng2Str 用户2纬度
     * @return
     */
    public static Double getDistance(String lat1Str, String lng1Str, String lat2Str, String lng2Str) {
        Double lat1 = Double.parseDouble(lat1Str);
        Double lng1 = Double.parseDouble(lng1Str);
        Double lat2 = Double.parseDouble(lat2Str);
        Double lng2 = Double.parseDouble(lng2Str);

        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double difference = radLat1 - radLat2;
        double mdifference = rad(lng1) - rad(lng2);
        double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(difference / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(mdifference / 2), 2)));
        distance = distance * EARTH_RADIUS;
        distance = Math.round(distance * 10000.0) / 10000.0;
        return distance;
    }


    /**
     * 获取当前用户一定距离以内的经纬度值
     * 单位米 return minLat
     * 最小经度 minLng
     * 最小纬度 maxLat
     * 最大经度 maxLng
     * 最大纬度 minLat
     */
    public static Map getAround(String latStr, String lngStr, String raidus) {
        Map map = new HashMap(16);

        Double latitude = Double.parseDouble(latStr);
        Double longitude = Double.parseDouble(lngStr);

        Double degree = (24901 * 1609) / 360.0;
        double raidusMile = Double.parseDouble(raidus);

        Double dpmLng = 1 / degree;
        Double radiusLng = dpmLng * raidusMile;
        Double minLng = latitude - radiusLng;
        Double maxLng = latitude + radiusLng;

        Double mpdLat = degree * Math.cos(latitude * (PI / 180));
        Double dpmLat = 1 / mpdLat;
        Double radiusLat = dpmLat * raidusMile;
        Double maxLat = longitude - radiusLat;
        Double minLat = longitude + radiusLat;

        map.put("maxLng", minLat+"");
        map.put("minLng", maxLat+"");
        map.put("minLat", minLng+"");
        map.put("maxLat", maxLng+"");

        return map;
    }

    public static Map<String,Object> getLngAndLat(String address,String city){
        Map<String,Object> map=new HashMap<String, Object>();
        String url = "http://restapi.amap.com/v3/geocode/geo";

        Map<String,String> param = new HashMap();
        param.put("output","JSON");
        param.put("key","db80893c24823e76b1a07fde739cbdb6");
        param.put("address",address);
        param.put("city",city);

        Map<String,String> header = new HashMap<String, String>();
        header.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        header.put("Accept-Encoding","gzip, deflate");
        header.put("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        header.put("Connection","keep-alive");
        header.put("Host","restapi.amap.com");
        header.put("Upgrade-Insecure-Requests","1");

        String json = null;
        try {
            json = HttpClientUtil.doBasisGet(url,param,header,"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
            log.info(e.getMessage());
        }
        if(json == null || "".equals(json))
        {
            map.put("lng", 0.0);
            map.put("lat", 0.0);
            map.put("city",null);
            map.put("province",null);
        }
        try {
            JSONObject obj = JSONObject.fromObject(json);
            if(obj.get("status").toString().equals("1")){

                JSONArray geocodes = obj.getJSONArray("geocodes");

                if(geocodes.size()>0) {

                    JSONObject trueAddress = geocodes.getJSONObject(0);
                    String location = trueAddress.getString("location");
                    String lngX = location.split(",")[0];
                    String latY = location.split(",")[1];
                    String nowCity = trueAddress.getString("city");
                    String nowProvince = trueAddress.getString("province");
                    map.put("lng", Double.valueOf(lngX));
                    map.put("lat", Double.valueOf(latY));
                    map.put("city",nowCity);
                    map.put("province",nowProvince);
                }else
                {
                    map.put("lng", 0.0);
                    map.put("lat", 0.0);
                    map.put("city",null);
                    map.put("province",null);
                }
            }else{
                map.put("lng", 0.0);
                map.put("lat", 0.0);
                map.put("city",null);
                map.put("province",null);
            }
        }catch (Exception e)
        {
            log.info(address);
            log.info(e.getMessage());
            map.put("lng", 0.0);
            map.put("lat", 0.0);
            map.put("city",null);
            map.put("province",null);
        }
        return map;
    }

    public static String loadJSON (String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL oracle = new URL(url);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    yc.getInputStream()));
            String inputLine = null;
            while ( (inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        }
        return json.toString();
    }

//    public static Map<String, BigDecimal> getLatAndLngByAddress(String addr){
//        String address = "";
//        String lat = "";
//        String lng = "";
//        try {
//            address = java.net.URLEncoder.encode(addr,"UTF-8");
//        } catch (UnsupportedEncodingException e1) {
//            e1.printStackTrace();
//        }
//        String url = String.format("http://api.map.baidu.com/geocoder/v2/?"
//                +"ak=4rcKAZKG9OIl0wDkICSLx8BA&output=json&address=%s",address);
//        URL myURL = null;
//        URLConnection httpsConn = null;
//        //进行转码
//        try {
//            myURL = new URL(url);
//        } catch (MalformedURLException e) {
//
//        }
//        try {
//            httpsConn = (URLConnection) myURL.openConnection();
//            if (httpsConn != null) {
//                InputStreamReader insr = new InputStreamReader(
//                        httpsConn.getInputStream(), "UTF-8");
//                BufferedReader br = new BufferedReader(insr);
//                String data = null;
//                if ((data = br.readLine()) != null) {
//                    lat = data.substring(data.indexOf("\"lat\":")
//                            + ("\"lat\":").length(), data.indexOf("},\"precise\""));
//                    lng = data.substring(data.indexOf("\"lng\":")
//                            + ("\"lng\":").length(), data.indexOf(",\"lat\""));
//                }
//                insr.close();
//            }
//        } catch (IOException e) {
//
//        }
//        Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
//        map.put("lat", new BigDecimal(lat));
//        map.put("lng", new BigDecimal(lng));
//        return map;
//    }

}
