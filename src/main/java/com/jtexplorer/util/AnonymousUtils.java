package com.jtexplorer.util;

import java.util.Date;

/**
 * @author xuwang
 */
public class AnonymousUtils {

    /**
     * 匿名化
     * @return 使用salt进行md5并加上时间戳
     */
    public static String anonymousString(String value,String salt){
        String a = "";
        if(StringUtil.isEmpty(value)){
            value = StringUtil.getRandNumStr(4);
        }
        if(StringUtil.isEmpty(salt)){
            salt = StringUtil.getRandNumStr(4);
            a = salt;
        }
        // md5
        value = StringUtil.getSaltMd5(value,salt);
        return value+new Date().getTime()+a;
    }

}
