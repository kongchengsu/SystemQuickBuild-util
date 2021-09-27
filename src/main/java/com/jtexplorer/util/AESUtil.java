package com.jtexplorer.util;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;


public class AESUtil {

    /**
     * 密钥算法
     */
    private static final String ALGORITHM = "AES";
    /**
     * 加解密算法/工作模式/填充方式
     */
    private static final String ALGORITHM_MODE_PADDING = "AES/ECB/PKCS7Padding";

    static {
        //Security.addProvider(new BouncyCastleProvider());
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * AES加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String encryptData(String data,String key) throws Exception {
        // 创建密码器
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING, "BC");
        SecretKeySpec secretKeySpec = new SecretKeySpec(MD532(key).getBytes(), ALGORITHM);
        // 初始化
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        return new String(encodeBase64(cipher.doFinal(data.getBytes())));
    }

    /**
     * AES解密
     *
     * @param base64Data
     * @return
     * @throws Exception
     */
    public static String decryptData(String base64Data,String key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING, "BC");
        SecretKeySpec secretKeySpec = new SecretKeySpec(MD532(key).getBytes(), ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        return new String(cipher.doFinal(decodeBase64(base64Data)));
    }

    public static void main(String[] args) throws Exception {

        String A="wvm81DNoEyMiBbF7gjHh51JWMpdVsxwGuQ/Ej4DcGR408/bJzsu0zsCGcm9t3OMuDCoI+oXQKZBX/iBFo/+TfOf0w8YMbeDZELHUBShN+Pw7It0rzsBY0/ScN9EFRAg9uFE5AKFzlEMdDnHdEk5IX8bn+q/iNyuZ20Zfatx/n1u1r/+IDKtrxUDyPpRxzeHlc0eIzN7tNfnW+BjWenmlK+/qzqb58s8VEJ8vwKjXnIuBRUnt8IXouCqR4pZjQ7VY/pasfJcb8wBvCMOJ8szpoZv2kB0UglhQ/f8VgMNVzSd7Jlbr/Gvikb4reFPoZuSQgCkL0wcT1txmPqF2hNd7Ct91Gp3CYwjGo1DSIl6UVedPYb9XRh2vhUkmn1L9ri4VtXvKxMZeda+gzcpzFkQtroQUHCtKvWYyDUzTIDT2uLvyusZLPrhI1t3r7Gqu4b7J9aGADUuCh4CmeoR5eIcOoagtiNcrd6H5HBnYuqHCw6mf4/9/+3E60SOkiTr2hhijwkaqif7r2jpwluHBZ2bxQ896Lnlh7WiwFZ6KxVL9TwKTUa0EvMW+xcT7Sh4hawjgEuZCzOjixNl1tcfqwBGxJDMHODTV2GBX7uHntPCGfiMbuuU/0CVn5VGuJETQXmOM7KT6kaK22TCJj7d3yw7dtROSJ2WIg7O5ieodfHYSrBlw3ZLnaLUx63K0BcJCrNUYJ8tO28x3swgVrNZHcQccsR/35rdrUuyd2E1CCdFW2/XnniY8ZJSrd33Xy+9dCjR5almlJwyLWAXHTIQJHMQjs5yhBNL9PJRYk9lxb3DulIAS37fHEzKiuNm8aA46SMz7EsDnTYdkPA7I+vf3GED6IiKeTjLD4z/LBN2FvUXxwmInaGAZJjZeWUa3cUSJCR+hyxRvAcUwWr9w1Yu+KHqXIAbMdrPYO+SCnK9nDBOVO/JL62Nb5gV5xpAIpeJW9dvABDPpEJyxtPTmL3J+5KPF0fNt678eLbYHJdp731j3JojVYvNx++eDJGg9LxLT88ECqFX1II+x2FiCSt2MG2JTR1OlWCtOMrpO1e5lzg0ScSU=";
        A=AESUtil.decryptData(A,"3c6e0b8a9c15224a8228b9a98ca1531d");
        System.out.println(A);

    }


    /**
     * 进行Base64解密
     * @param binaryData
     * @return
     * @throws Exception
     */
    public static byte[] decodeBase64(String binaryData) throws Exception {
        byte[] result = Base64.decodeBase64(binaryData.getBytes());
        return  result;
    }
    /**
     * 进行Base64加密
     * @param binaryData
     * @return
     * @throws Exception
     */
    public static byte[] encodeBase64(byte[] binaryData) throws Exception {
        byte[] result = Base64.encodeBase64(binaryData);
        return  result;
    }
    public static String MD532(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
            // 16位的加密
            //return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 大写MD5
     * @param plainText
     * @return
     */
    public static String dxMD532(String plainText) {
        return MD532(plainText).toUpperCase();
    }

}
