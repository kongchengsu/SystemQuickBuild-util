package com.jtexplorer.util;


import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 蒋鹏程 on 2017/2/18.
 */
@Slf4j
public class MapUtil {

    private static double EARTH_RADIUS = 6378.137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 计算两个经纬度之间的距离，
     * 纬度范围-90~90，经度范围-180~180
     * @param lat1 维度
     * @param lng1 经度
     * @param lat2
     * @param lng2
     * @return
     */
    public static double getDistance(double lat1, double lng1, double lat2,
                                     double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000d) / 10000d;
        s = s * 1000;
        return s;
    }

    /**
     * 根据地址获取经纬度
     * @param address  详细地址，例如：山东省临沂市兰山区临沂大学
     * @param city 选填，城市：例如：上海市
     * @return
     */
    public static Map<String,Object> getLngAndLat(String address,String city){

        if(StringUtil.isEmpty(address)){
            return null;
        }

        Map<String,Object> map=new HashMap<String, Object>();
        String url = "http://restapi.amap.com/v3/geocode/geo";

        Map<String,String> param = new HashMap();
        param.put("output","JSON");
        param.put("key","db80893c24823e76b1a07fde739cbdb6");
        try {
            if(StringUtil.isNotEmpty(address)) {
                param.put("address", URLEncoder.encode(address, "utf-8"));
            }
            if(StringUtil.isNotEmpty(city)) {
                param.put("city", URLEncoder.encode(city, "utf-8"));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }


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


}
