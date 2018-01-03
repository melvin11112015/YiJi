package com.mlstudio.encryptdecrypt.secrect;


import com.mlstudio.encryptdecrypt.secrect.base64.BASE64Decoder;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * Created by Yangyl on 2016/12/2.
 */

public class EncryptDecryptTool {

    private static final String RSA = "RSA";// 非对称加密密钥算法
    private static final String NONE_PKCS1_PADDING = "RSA/ECB/PKCS1Padding";//加密填充方式
    private static final int DEFAULT_KEY_SIZE = 2048;//秘钥默认长度
    private static final byte[] DEFAULT_SPLIT = "#PART#".getBytes();    // 当要加密的内容超过bufferSize，则采用partSplit进行分块加密
    private static final int DEFAULT_BUFFERSIZE = (DEFAULT_KEY_SIZE / 8) - 11;// 当前秘钥支持加密的最大字节数
    public EncryptDecryptTool(){

    }

    /**
     *
     * @param data the data will be encrypt
     * @param publicKey publickey
     * @return secret byte data
     * @throws Exception
     */
    public byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
        return encryptByPublicKey(data,(new BASE64Decoder()).decodeBuffer(publicKey));
    }

    /**
     *
     * @param encrypted secret byte data
     * @param privateKey privateKey
     * @return  plaintext byte data
     * @throws Exception
     */
    public byte[] decryptByPrivateKey(byte[] encrypted, String privateKey) throws Exception{
        return decryptByPrivateKey(encrypted,(new BASE64Decoder()).decodeBuffer(privateKey));
    }

    public byte[] encryptByPublicKey(byte[] data, byte[] publicKey) throws Exception {
        // 得到公钥
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
        KeyFactory kf = KeyFactory.getInstance(RSA);
        PublicKey keyPublic = kf.generatePublic(keySpec);
        // 加密数据
        Cipher cp = Cipher.getInstance(NONE_PKCS1_PADDING);
        cp.init(Cipher.ENCRYPT_MODE, keyPublic);
        return cp.doFinal(data);
    }

    /**
     * 使用私钥进行解密
     */
    public byte[] decryptByPrivateKey(byte[] encrypted, byte[] privateKey) throws Exception {
        // 得到私钥
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey);
        KeyFactory kf = KeyFactory.getInstance(RSA);
        PrivateKey keyPrivate = kf.generatePrivate(keySpec);

        // 解密数据
        Cipher cp = Cipher.getInstance(NONE_PKCS1_PADDING);
        cp.init(Cipher.DECRYPT_MODE, keyPrivate);
        byte[] arr = cp.doFinal(encrypted);
        return arr;
    }
}
