package com.last201409;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by kuangcheng on 2014/12/8.
 */
public class AECUtils {
    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    public static final String KEY = "qIhU360_Qihoo";

    private static Cipher initAESCipher(String skey, int cipherMode) {
        Cipher cipher = null;
        try {
            SecretKeySpec key = new SecretKeySpec("aaaa".getBytes(), KEY_ALGORITHM);
            cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            //初始化
            cipher.init(cipherMode, key);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvalidKeyException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return cipher;
    }


    /**
     * 对文件进行AES加密
     */
    public static File encryptFile(File sourceFile,String desFilePath){
        //新建临时加密文件
        File encrypfile = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(sourceFile);
            outputStream = new FileOutputStream(desFilePath);
            Cipher cipher = initAESCipher(KEY, Cipher.ENCRYPT_MODE);
            //以加密流写入文件
            CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher);
            byte[] cache = new byte[1024];
            int nRead = 0;
            while ((nRead = cipherInputStream.read(cache)) != -1) {
                outputStream.write(cache, 0, nRead);
                outputStream.flush();
            }
            cipherInputStream.close();
        }  catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }  catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return encrypfile;
    }



    /**
     * 对文件进行AES加密
     */
    public static File encryptFile(InputStream inputStream,String desFilePath){
        //新建临时加密文件
        File encrypfile = null;
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(desFilePath);
            Cipher cipher = initAESCipher(KEY, Cipher.ENCRYPT_MODE);
            //以加密流写入文件
            CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher);
            byte[] cache = new byte[1024];
            int nRead = 0;
            while ((nRead = cipherInputStream.read(cache)) != -1) {
                outputStream.write(cache, 0, nRead);
            }
            cipherInputStream.close();
        }  catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }  catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return encrypfile;
    }





//    /**
//     * AES方式解密文件
//     * @param sourceFile
//     * @return
//     */
//    public static File decryptFile(File sourceFile,String fileType, String sKey){
//        File decryptFile = null;
//        InputStream inputStream = null;
//        OutputStream outputStream = null;
//        try {
//            decryptFile = File.createTempFile(sourceFile.getName(), fileType);
//            Cipher cipher = initAESCipher(sKey,Cipher.DECRYPT_MODE);
//            inputStream = new FileInputStream(sourceFile);
//            outputStream = new FileOutputStream(decryptFile);
//            CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher);
//            byte [] buffer = new byte [1024];
//            int r;
//            while ((r = inputStream.read(buffer)) >= 0) {
//                cipherOutputStream.write(buffer, 0, r);
//            }
//            cipherOutputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }finally {
//            try {
//                inputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//            }
//            try {
//                outputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//            }
//        }
//        return decryptFile;
//    }


    /**
     * AES方式解密文件
     * @param sourceFile
     * @return
     */
    public static String decryptFile(File sourceFile){
        File decryptFile = null;
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = null;
        try {
            Cipher cipher = initAESCipher(KEY,Cipher.DECRYPT_MODE);
            inputStream = new FileInputStream(sourceFile);
            outputStream = new ByteArrayOutputStream();
            CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher);
            byte [] buffer = new byte [1024];
            int r;
            while ((r = inputStream.read(buffer)) >= 0) {
                cipherOutputStream.write(buffer, 0, r);
            }
            cipherOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return outputStream.toString();
    }
}
