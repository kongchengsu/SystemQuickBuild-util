package com.jtexplorer.util;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

/**
 *
 * @author 苏友朋
 * @date 2016/7/10
 */
public class StringUtil {
    /**
     * curno表示需要被填充的字符
     * length表示要填充的长度
     * fillStr表示需要填充的字符
     * 根据要求来填充字符串
     */
    public static String getCurNo(int curno, int length, String fillStr) {
        int temp = curno;
        StringBuilder sb = new StringBuilder(length);
        int count = 0;
        while (curno / 10 != 0) {
            curno = curno / 10;
            count++;
        }
        int size = length - count - 1;
        for (int i = 0; i < size; i++) {
            sb.append(fillStr);
        }
        sb.append(temp);
        return sb.toString();
    }

    /**
     * curno表示需要被填充的字符
     * length表示要填充的长度
     * fillStr表示需要填充的字符
     * 根据要求来填充字符串
     */
    public static String getFillNo(String type, String curno, int length, String fillStr) {
        StringBuilder sb = new StringBuilder(length);
        if ("aft".equals(type)) {
            sb.append(fillStr);
        }
        for (int i = 0; i < length; i++) {
            sb.append(curno);
        }
        if ("bef".equals(type)) {
            sb.append(fillStr);
        }
        return sb.toString();
    }

    /**
     * 将字符串填充到规定的长度
     * <p>
     * curno表示需要被填充的字符
     * length表示要填充的长度
     * fillStr表示需要填充的字符
     * 根据要求来填充字符串
     */
    public static String getFillNoToL(String type, String curno, int length, String fillStr) {
        length = length - fillStr.length();
        if (length > 0) {
            return getFillNo(type, curno, length, fillStr);
        } else {
            return fillStr;
        }
    }

    /**
     * 判断字符串是否是null或空字符串：是，返回true
     *
     * @param str 要判断的字符串
     * @return boolean  是，返回true    否，返回false
     */
    public static boolean isEmpty(String str) {
        return (str == null) || (str.trim().length() == 0);
    }

    /**
     * 判断字符串是否是null或空字符串：否，返回true
     *
     * @param str 要判断的字符串
     * @return boolean  否，返回true    是，返回false
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 判断字符串是否是null或空字符串：否，返回true
     *
     * @param str 要判断的字符串
     * @return boolean  否，返回true    是，返回false
     */
    public static boolean isNotEmpty(Object str) {
        if(str == null){
            return false;
        }else {
            return !isEmpty(str.toString());
        }
    }

    /**
     * 判断字符串是否是null或空字符串：否，返回false
     *
     * @param str 要判断的字符串
     * @return boolean  否，返回true    是，返回false
     */
    public static boolean isEmpty(Object str) {
        if(str == null){
            return true;
        }else {
            return isEmpty(str.toString());
        }
    }

    /**
     * 获取随机n位数
     *
     * @param n 字数
     * @return String
     */
    public static String getRandNumStr(int n) {
        Random random = new Random();
        StringBuilder sRand = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sRand.append(String.valueOf(random.nextInt(10)));
        }
        return sRand.toString();
    }

    /**
     * 加密密码
     *
     * @param flag     plaintext:明文密码   encryption：加密密码   同时运行成功之后会变成salt
     * @param password 明文密码
     * @return 加密之后的密码
     */
    public static String encryptionPassword(String password, String flag, String salt) {
        //盐 为4位字符串
        if ("encryption".equals(flag)) {
            //加密
            //设置密码为md5格式
            password = getSaltMd5(password, salt);
        }
        return password;
    }

    /**
     * 按照给定的随机标识符加密密码
     *
     * @param salt     随机标识符
     * @param password 明文密码
     * @return 加密之后的密码
     */
    public static String encryptionPasswordBySalt(String password, String flag, String salt) {
        if ("encryption".equals(flag)) {
            //加密
            //设置密码为md5格式
            password = getSaltMd5(password, salt);
        }
        return password;
    }

    /**
     * 获取随机字符串
     */
    public static String getRandomString(int strLength) {
        StringBuilder buffer = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < strLength; i++) {
            if (random.nextBoolean()) {
                int charInt = 48 + random.nextInt(10);
                char c = (char) charInt;
                buffer.append(c);
            } else {
                int charInt;
                if (random.nextBoolean()) {
                    charInt = 65 + random.nextInt(26);
                } else {
                    charInt = 97 + random.nextInt(26);
                }
                if (charInt == 79) {
                    charInt = 111;
                }
                char c = (char) charInt;
                buffer.append(c);
            }
        }

        return buffer.toString();
    }

    /**
     * 根据盐和md5算法进行加密
     *
     * @param oriStr 原始字符串
     * @param salt   盐
     * @return 加密后的字符串
     */
    public static String getSaltMd5(String oriStr, String salt) {
        return md5(md5(oriStr) + salt);
    }

    /**
     * 获取字符串的md5值
     *
     * @param str 原始字符串
     * @return md5值
     */
    public static String md5(String str) {
        if (str == null) {
            return null;
        }
        byte[] newByte1 = null;
        try {
            //解决junit 可正常执行，tomcat下执行出错的问题
            newByte1 = str.getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.out.println("不支持的编码");
        }
        try {
            MessageDigest e = MessageDigest.getInstance("MD5");
            byte[] newByte2 = e.digest(newByte1);
            StringBuilder cryptograph = new StringBuilder();

            for (byte aNewByte2 : newByte2) {
                String temp = Integer.toHexString(aNewByte2 & 0xFF);
                if (temp.length() < 2) {
                    temp = "0" + temp;
                }

                cryptograph.append(temp);
            }

            return cryptograph.toString();
        } catch (Exception var7) {
            var7.printStackTrace();
        }
        return null;
    }

    /**
     * 微信用签名生成方法（第一步，设所有发送或者接收到的数据为集合M，将集合M内非空参数值的参数按照参数名ASCII码从小到大排序（字典序），使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串stringA。
     * <p>
     * 特别注意以下重要规则：
     * <p>
     * ◆ 参数名ASCII码从小到大排序（字典序）；
     * ◆ 如果参数的值为空不参与签名；
     * ◆ 参数名区分大小写；
     * ◆ 验证调用返回或微信主动通知签名时，传送的sign参数不参与签名，将生成的签名与该sign值作校验。
     * ◆ 微信接口可能增加字段，验证签名时必须支持增加的扩展字段
     * 第二步，在stringA最后拼接上key得到stringSignTemp字符串，并对stringSignTemp进行MD5运算，再将得到的字符串所有字符转换为大写，得到sign值signValue。）
     *
     * @param map       参数
     * @param secretKey 密钥
     * @return md5值
     */
    public static String getWechatSign(Map<String, String> map, String secretKey) {
        if (map == null || isEmpty(secretKey)) {
            return null;
        }
        List<Map.Entry<String, String>> infoIds = new ArrayList<>(map.entrySet());
        infoIds.sort(Comparator.comparing(o -> (o.getKey())));
        // 构造签名键值对的格式
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> item : infoIds) {
            if (StringUtil.isNotEmpty(item.getKey()) && StringUtil.isNotEmpty(item.getValue())) {
                String key = item.getKey();
                String val = item.getValue();
                sb.append(key).append("=").append(val).append("&");
            }
        }
        sb.append("key=").append(secretKey);
        String sign = sb.toString();
        // MD5加密
        return md5(sign).toUpperCase();
    }


    /**
     * 文件名切割
     *
     * @param fileName 文件名 如  abc.png
     * @return 切割后的数组如["abc","png"]
     */
    public static String[] splitFileName(String fileName) {
        return fileName.split("\\.");
    }

    public static String getStrEncode(String str) {
        String encode = "GB2312";
        try {
            //判断是不是GB2312
            if (str.equals(new String(str.getBytes(encode), encode))) {
                //是的话，返回“GB2312“，以下代码同理
                return encode;
            }
            encode = "ISO-8859-1";
            //判断是不是ISO-8859-1
            if (str.equals(new String(str.getBytes(encode), encode))) {
                return encode;
            }
            encode = "UTF-8";
            //判断是不是UTF-8
            if (str.equals(new String(str.getBytes(encode), encode))) {
                return encode;
            }
            encode = "GBK";
            //判断是不是GBK
            if (str.equals(new String(str.getBytes(encode), encode))) {
                return encode;
            }
        } catch (Exception e) {
            return "";
        }
        return "";
    }

}
