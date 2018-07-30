package com.diet.admin.utils;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Security;

/**
 * @author LiuYu
 * @date 2018/6/29
 */
public class WxDecode {
    public static void main(String[] args) {
        String en = "ou2iTeFRulXL1QomSyhur++kQtlfe4xRdah4tm22CJMDfrHEyy2iQD0pJX5ntSDnxVQD/UDfuqhr1fNg3iNkahq5XvlAPj8Wy9OgraXDiWdiAlEX15W6hoWML2ArM7e1AaHD7ObE7IrBMVf/kcwYvnUJ7iVLuyVMkELFII5/zm/5TZ13b0TMZMHMyLl9GOLZ1Vt4Ir0eG9X6Yypn5QvFdksmlX5vAuWwT2arykhweoOX3pcCSSjt3omts3Rgl9Q3lP48DRarc47pdhdGvoMH6/1Wbo6pdzY4w7vUq6DJJPsIYUUTXnKvel2fA26IkVE/jRBR1YQB7mZfXmzS4wrGEQgsseH8zw69Lfoxz4IV6u7xqkX7tALczDx2Oz/dw9RqH/ZuWY2cXgDtZ9VXmWpoXZkfvibpl1/hS4MUB/otalSQ7i2wj+aXXdIJIS+0wK0Z7HUFYR0Fo7eUgd3IaPf8A8oeJ/9r0j8iTYX/Ke3wVcM=";
        String session_key = "qWaYjaegDCZoUTUfs0V1Jw==";
        String iv = "aHy/8r8HdwYf22fLnCHZow==";
        System.out.println(getUserInfo(en, session_key, iv).toJSONString());
    }

    public static JSONObject getUserInfo(String encryptedData, String sessionKey, String iv){
        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);

        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding","BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            // 初始化
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSONObject.parseObject(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
