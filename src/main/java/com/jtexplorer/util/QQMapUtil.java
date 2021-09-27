package com.jtexplorer.util;

import com.alibaba.fastjson.JSONObject;
import com.jtexplorer.entity.AddressComponents;
import com.jtexplorer.entity.JsonRootBean;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QQMapUtil {

    private static String key = "TKTBZ-DDTKO-ABKWU-SVBDP-K4FXZ-SHBK3";

    /**
     * 腾讯 地址转坐标
     * @param address
     * @param city
     * @return
     */
    public static JsonRootBean getLngAndLat(String address, String city){
        JsonRootBean jsonRootBean = new JsonRootBean();

        String url = "https://apis.map.qq.com/ws/geocoder/v1/?key="+key+"&address="+address;

        String json = null;
        json = HttpClientUtil.doGet(url);
        jsonRootBean = JSONObject.parseObject(json,JsonRootBean.class);
        return jsonRootBean;
    }
 /**
     * 腾讯 地址转坐标
     * @param longitude 经度
     * @param latitude 维度
     * @return
     */
    public static JsonRootBean getAddressByLngLat(String longitude, String latitude){
        JsonRootBean jsonRootBean = new JsonRootBean();

        String url = "https://apis.map.qq.com/ws/geocoder/v1/?location="+latitude+","+longitude+"&key="+key;
        String json = null;
        json = HttpClientUtil.doGet(url);
        JSONObject jsonObjectAddress = JSONObject.parseObject(json).getJSONObject("result").getJSONObject("address_component");
        jsonRootBean = JSONObject.parseObject(json,JsonRootBean.class);
        jsonRootBean.getResult().setAddress_components(JSONObject.parseObject(jsonObjectAddress.toJSONString(), AddressComponents.class));
        return jsonRootBean;
    }

    public static  void main(String[] args){
//        System.out.println(getLngAndLat("山东省临沂市兰山区应用科学城",""));
        System.out.println(getAddressByLngLat("39.984154","116.307490"));
    }

}
