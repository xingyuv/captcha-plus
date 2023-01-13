package com.example.verificationcodejavademo.utils;


import android.text.TextUtils;
import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Date:2020/5/20
 * author:wuyan
 */
public class AESUtil {
    private final static String ALGORITHMSTR = "AES/ECB/PKCS7Padding";
    private final static String ALGORITHM = "AES";

    public static String encode(String content, String key) {
        if (TextUtils.isEmpty(content)) {
            return content;
        }
        if (key == null || key.isEmpty()) {
            return content;
        }
        try {
            byte[] result = encrypt(key, content);

            String s = Base64.encodeToString(result, Base64.DEFAULT);
            if (s.contains("\n")) {
                return s.replace("\n", "");
            }
            return s;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static byte[] encrypt(String encryptKey, String content) throws Exception {
        // 创建AES秘钥
        SecretKeySpec secretKeySpec = new SecretKeySpec(encryptKey.getBytes(), ALGORITHM);
        // 创建密码器
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        // 初始化加密器
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        // 加密
        return cipher.doFinal(content.getBytes("UTF-8"));
    }
}
