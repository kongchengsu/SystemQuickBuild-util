package com.jtexplorer.util;

import com.jtexplorer.entity.JsonRootBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressUtil {
    /**
     * 根据地址信息获取经纬度
     *
     * @param address 详细地址
     * @param city    市名
     * @return Map<String, Double> 经纬度：key：lng和lat
     */
    public static Map<String, Double> getLngAndLat(String address, String city) throws Exception {
        JsonRootBean jsonRootBean = QQMapUtil.getLngAndLat(address, city);
        Map<String, Double> result = new HashMap<>();
        result.put("lng", jsonRootBean.getResult().getLocation().getLng());
        result.put("lat", jsonRootBean.getResult().getLocation().getLat());
        return result;
    }

    /**
     * 根据经纬度获取GeoHash字符串
     *
     * @param lat 纬度
     * @param lng 经度
     * @return GeoHash字符串
     */
    public static String getGeoHashByLngAndLat(double lat, double lng) {
        return GeoHash.encode(lat, lng);
    }

    /**
     * 根据地址信息获取GeoHash字符串
     *
     * @param address 详细地址
     * @param city    市名
     * @return GeoHash字符串
     */
    public static String getGeoHashByAddress(String address, String city) {
        try {
            Map<String, Double> result = getLngAndLat(address, city);
            return getGeoHashByLngAndLat(result.get("lat"), result.get("lng"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 根据经纬度获取GeoHash字符串及周边GeoHash字符串
     *
     * @param lat 纬度
     * @param lng 经度
     * @return GeoHash字符串列表
     */
    public static List<String> getGeoHashListByLngAndLat(double lat, double lng) {
        return GeoHash.getArroundGeoHash(lat, lng);
    }

    /**
     * 根据地址信息获取GeoHash字符串及周边GeoHash字符串
     *
     * @param address 详细地址
     * @param city    市名
     * @return GeoHash字符串列表
     */
    public static List<String> getGeoHashListByAddress(String address, String city) {
        try {
            Map<String, Double> result = getLngAndLat(address, city);
            return getGeoHashListByLngAndLat(result.get("lat"), result.get("lng"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 根据经纬度获取距离信息
     *
     * @param lat1Str 第一个地址的纬度
     * @param lng1Str 第一个地址的经度
     * @param lat2Str 第二个地址的纬度
     * @param lng2Str 第二个地址的经度
     * @return 距离
     */
    public static Double getDistanceByLngAndLat(String lat1Str, String lng1Str, String lat2Str, String lng2Str) {
        return MapDistanceUtil.getDistance(lat1Str, lng1Str, lat2Str, lng2Str);
    }

    /**
     * 根据地址获取距离信息
     *
     * @param addressOne 第一个地址的详细地址
     * @param cityOne    第一个地址的市名
     * @param addressTwo 第二个地址的详细地址
     * @param cityTwo    第二个地址的市名
     * @return 距离
     */
    public static Double getDistanceByAddress(String addressOne, String cityOne, String addressTwo, String cityTwo) {
        try {
            Map<String, Double> one = getLngAndLat(addressOne, cityOne);
            Map<String, Double> two = getLngAndLat(addressTwo, cityTwo);
            return getDistanceByLngAndLat(one.get("lat").toString(), one.get("lng").toString(),
                    two.get("lat").toString(), two.get("lng").toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据经纬度获取距离最短的地址
     *
     * @param sign   标点的经纬度信息：key：key：lng和lat
     * @param target 目标地址的经纬度信息：key：各地点的key，value：各地点的经纬度信息的map：key：lng和lat
     * @return String 最短地址的标识key
     */
    public static String getDistanceMinByLngAndLat(Map<String, Double> sign, Map<String, Map<String, Double>> target) {
        Map<String, String> result = new HashMap<>();
        result.put("key", "");
        result.put("distance", "");
        target.forEach((k, v) -> {
            Double distance = getDistanceByLngAndLat(sign.get("lat").toString(), sign.get("lng").toString(),
                    v.get("lat").toString(), v.get("lng").toString());
            v.put("distance", distance);
            if (StringUtil.isEmpty(result.get("distance"))) {
                result.put("key", k);
                result.put("distance", distance.toString());
            } else {
                Double distanceOld = Double.parseDouble(result.get("distance"));
                if (distanceOld > distance) {
                    result.put("key", k);
                    result.put("distance", distance.toString());
                }
            }
        });
        return result.get("key");
    }

    /**
     * 根据地址信息获取距离最短的地址
     *
     * @param signA   标点的地址信息：key：key：address和city
     * @param targetA 目标地址的地址信息：key：各地点的key，value：各地点的地址信息的map：key：address和city
     * @return String 最短地址的标识key
     */
    public static String getDistanceMinByAddress(Map<String, String> signA, Map<String, Map<String, String>> targetA) {
        try {
            Map<String, String> result = new HashMap<>();
            // 首先将
            Map<String, Double> sign = getLngAndLat(signA.get("address"), signA.get("city"));
            Map<String, Map<String, Double>> target = new HashMap<>();
            targetA.forEach((k, v) -> {
                Map<String, Double> targetItem = null;
                try {
                    targetItem = getLngAndLat(v.get("address"), v.get("city"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                target.put(k, targetItem);
            });
            return getDistanceMinByLngAndLat(sign, target);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        try {
            Map<String, Double> a = getLngAndLat("应用科学城2号公寓楼", "临沂");
            System.out.println("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
