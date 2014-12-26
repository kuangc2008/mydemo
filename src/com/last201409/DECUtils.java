package com.last201409;

import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/***
 * Des文件加密解密
 *
 * @author spring sky<br>
 * Email:vipa1888@163.com<br>
 * QQ:840950105
 *
 */
public class DECUtils {
    /**
     * 加密后的文件路径列表
     */
    private static final String KEY = "spring sky";

    public DECUtils() {
        super();
        getKey(KEY);
        initCipher();
    }

    private Key key;

    /***
     * 解密密码
     */
    private Cipher cipherDecrypt;
    /**
     * 加密密码
     */
    private Cipher cipherEncrypt;

    private static DECUtils utils = null;

    public static final DECUtils getUtil() {
        if(utils == null) {
            utils = new DECUtils();
        }
        return utils;
    }


    /**
     * 加密文件的核心
     *
     * @param file
     *            要加密的文件
     * @param destFile
     *            加密后存放的文件名
     */
    public void encrypt(String file, String destFile) throws Exception {
        InputStream is = new FileInputStream(file);
        OutputStream out = new FileOutputStream(destFile);

        CipherInputStream cis = new CipherInputStream(is, cipherEncrypt);
        byte[] buffer = new byte[1024];
        int r;
        while ((r = cis.read(buffer)) > 0) {
            out.write(buffer, 0, r);
        }
        cis.close();
        is.close();
        out.close();
    }


    /**
     * 加密文件的核心
     *
     *            要加密的文件
     * @param destFile
     *            加密后存放的文件名
     */
    public void encrypt(InputStream is, String destFile) throws Exception {
        OutputStream out = new FileOutputStream(destFile);

        CipherInputStream cis = new CipherInputStream(is, cipherEncrypt);
        byte[] buffer = new byte[1024];
        int r;
        while ((r = cis.read(buffer)) > 0) {
            out.write(buffer, 0, r);
        }
        cis.close();
        is.close();
        out.close();
    }

    /***
     * 解密文件
     * @param destFile
     */
    public String decrypt(String destFile) {
        try {
            InputStream is = new FileInputStream(destFile);
            CipherInputStream cis = new CipherInputStream(is, cipherDecrypt);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[]  buffer = new byte[1024];
            int len = 0;
            while ((len = cis.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
            os.flush();
            os.close();
            cis.close();
            is.close();
            return os.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void initCipher() {
        try {
            // 加密的cipher
            cipherEncrypt = Cipher.getInstance("DES");
            cipherEncrypt.init(Cipher.ENCRYPT_MODE, this.key);
            // 解密的cipher
            cipherDecrypt = Cipher.getInstance("DES");
            cipherDecrypt.init(Cipher.DECRYPT_MODE, this.key);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 自定义一个key
     *
     */
    public Key getKey(String keyRule) {
        // Key key = null;
        byte[] keyByte = keyRule.getBytes();
        // 创建一个空的八位数组,默认情况下为0
        byte[] byteTemp = new byte[8];
        // 将用户指定的规则转换成八位数组
        for (int i = 0; i < byteTemp.length && i < keyByte.length; i++) {
            byteTemp[i] = keyByte[i];
        }
        key = new SecretKeySpec(byteTemp, "DES");
        return key;
    }

    /**
     * 解密密码
     *
     * @return
     */
    public Cipher getCipherEdcrypt() {
        return cipherDecrypt;
    }

    /**
     * 加密密码
     *
     * @return
     */
    public Cipher getCipherEncrypt() {
        return cipherEncrypt;
    }

}


