package io.github.yylyingy;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Yangyl on 2017/1/6.
 */

public class PasswordHashCodeTool {
    private static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp = "";
        for (byte aB : b) {
            stmp = (Integer.toHexString(aB & 0XFF));
            if (stmp.length() == 1) {
                hs.append('0');
                hs.append(stmp);
//                hs = hs.append("0") + stmp;
            } else {
                hs.append(stmp);
//                hs = hs + stmp;
            }
        }
        return hs.toString();
    }
    //SHA1 加密实例
    public static String encryptToSHA(String info) {
        byte[] digesta = null;
        try {
            // 得到一个SHA-1的消息摘要
            MessageDigest alga = MessageDigest.getInstance("SHA-256");
            // 添加要进行计算摘要的信息
            alga.update(info.getBytes());
            // 得到该摘要
            digesta = alga.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 将摘要转为字符串
        return byte2hex(digesta);
    }
}
