package com.jtexplorer.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Data
public class ParamStaticConfig {
    /**
     * 配置值部分
     */
    @Value("${myConfig.redis.timeout}")
    private Long redisTimeout;

    @Value("${myConfig.sms.signName}")
    private String smsSignName;
    @Value("${myConfig.sms.accessKeyId}")
    private String smsAccessKeyId;
    @Value("${myConfig.sms.accessKeySecret}")
    private String smsAccessKeySecret;
    @Value("${myConfig.sms.templateCode}")
    private String smsTemplateCode;

    @Value("${myConfig.email.host}")
    private String emailHost;
    @Value("${myConfig.email.sender}")
    private String emailSender;
    @Value("${myConfig.email.senderPassword}")
    private String emailSenderPassword;
    @Value("${myConfig.email.title}")
    private String emailTitle;

    @Value("${myConfig.file.uploadUrl}")
    private String uploadUrl;

    public enum ConfigParamKeyEnum {

        redisTimeout("redisTimeout"),

        smsSignName("smsSignName"),
        smsAccessKeyId("smsAccessKeyId"),
        smsAccessKeySecret("smsAccessKeySecret"),
        smsTemplateCode("smsTemplateCode"),
        uploadUrl("uploadUrl"),

        emailHost("emailHost"),
        emailSender("emailSender"),
        emailSenderPassword("emailSenderPassword"),
        emailTitle("emailTitle"),

        nullParam("nullParam"),
        ;


        ConfigParamKeyEnum(String paramName){
            this.paramName = paramName;
        }
        private String paramName;
        public String getParamName(){
            return paramName;
        }
        public static ConfigParamKeyEnum findByParamName(String paramName){
            for(ConfigParamKeyEnum v:ConfigParamKeyEnum.values()){
                if(v.getParamName().equals(paramName)){
                    return v;
                }
            }
            return nullParam;
        }
    }

    /**
     * 静态配置部分
     */
    public static Map<ConfigParamKeyEnum,Object> staticConfigParam = new HashMap<>();
    public static Object getWebappPathStatic(ConfigParamKeyEnum key){
        return ParamStaticConfig.staticConfigParam.get(key);
    }
    @Bean
    public int initStatic(){
        Field[] fields = ParamStaticConfig.class.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Value.class)) {
                Object fieldValue = getFieldValue(this, field);
                ParamStaticConfig.staticConfigParam.put(ConfigParamKeyEnum.findByParamName(field.getName()),fieldValue);
            }
        }
        return 0;
    }
    private static Object getFieldValue(Object o, Field f) {
        try {
            return o.getClass().getMethod("get" + upperFirstLatter(f.getName())).invoke(o);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String upperFirstLatter(String letter) {
        return letter.substring(0, 1).toUpperCase() + letter.substring(1);
    }
}
