package com.jiangzuoyoupin.utils;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.crypto.Cipher;
import java.io.*;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAUtils {

    public static byte[] decrypt(byte[] encryptedBytes, PrivateKey privateKey, int keyLength, int reserveSize, String cipherAlgorithm) throws Exception {
        int keyByteSize = keyLength / 8;
        int decryptBlockSize = keyByteSize - reserveSize;
        int nBlock = encryptedBytes.length / keyByteSize;
        ByteArrayOutputStream outbuf = null;
        try {
            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            outbuf = new ByteArrayOutputStream(nBlock * decryptBlockSize);
            for (int offset = 0; offset < encryptedBytes.length; offset += keyByteSize) {
                int inputLen = encryptedBytes.length - offset;
                if (inputLen > keyByteSize) {
                    inputLen = keyByteSize;
                }
                byte[] decryptedBlock = cipher.doFinal(encryptedBytes, offset, inputLen);
                outbuf.write(decryptedBlock);
            }
            outbuf.flush();
            return outbuf.toByteArray();
        } catch (Exception e) {
            throw new Exception("DEENCRYPT ERROR:", e);
        } finally {
            try {
                if (outbuf != null) {
                    outbuf.close();
                }
            } catch (Exception e) {
                outbuf = null;
                throw new Exception("CLOSE ByteArrayOutputStream ERROR:", e);
            }
        }
    }

    public static byte[] encrypt(byte[] plainBytes, PublicKey publicKey, int keyLength, int reserveSize, String cipherAlgorithm) throws Exception {
        int keyByteSize = keyLength / 8;
        int encryptBlockSize = keyByteSize - reserveSize;
        int nBlock = plainBytes.length / encryptBlockSize;
        if ((plainBytes.length % encryptBlockSize) != 0) {
            nBlock += 1;
        }
        ByteArrayOutputStream outbuf = null;
        try {
            Cipher cipher = Cipher.getInstance(cipherAlgorithm);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            outbuf = new ByteArrayOutputStream(nBlock * keyByteSize);
            for (int offset = 0; offset < plainBytes.length; offset += encryptBlockSize) {
                int inputLen = plainBytes.length - offset;
                if (inputLen > encryptBlockSize) {
                    inputLen = encryptBlockSize;
                }
                byte[] encryptedBlock = cipher.doFinal(plainBytes, offset, inputLen);
                outbuf.write(encryptedBlock);
            }
            outbuf.flush();
            return outbuf.toByteArray();
        } catch (Exception e) {
            throw new Exception("ENCRYPT ERROR:", e);
        } finally {
            try {
                if (outbuf != null) {
                    outbuf.close();
                }
            } catch (Exception e) {
                outbuf = null;
                throw new Exception("CLOSE ByteArrayOutputStream ERROR:", e);
            }
        }
    }

    public static PrivateKey getPriKey(String privateKeyPath, String keyAlgorithm) {
        PrivateKey privateKey = null;
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(privateKeyPath);
            privateKey = getPrivateKey(inputStream, keyAlgorithm);
        } catch (Exception e) {
            System.out.println("加载私钥出错!");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    System.out.println("加载私钥,关闭流时出错!");
                }
            }
        }
        return privateKey;
    }

    public static PublicKey getPubKey(String keyAlgorithm) {
        PublicKey publicKey = null;
        InputStream inputStream = null;
        try {
            System.out.println("getPubkey 1......");
            inputStream = RSAUtils.class.getClassLoader().getResourceAsStream("certificate/rsa_publickey8.pem");
            publicKey = getPublicKey(inputStream, keyAlgorithm);
        } catch (Exception e) {
            e.printStackTrace();//EAD PUBLIC KEY ERROR
            System.out.println("加载公钥出错!");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    System.out.println("加载公钥,关闭流时出错!");
                }
            }
        }
        return publicKey;
    }

    public static PublicKey getPublicKey(InputStream inputStream, String keyAlgorithm) throws Exception {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String readLine = null;
            while ((readLine = br.readLine()) != null) {
                if (readLine.charAt(0) == '-') {
                    continue;
                } else {
                    sb.append(readLine);
                    sb.append('\r');
                }
            }
            X509EncodedKeySpec pubX509 = new X509EncodedKeySpec(decodeBase64(sb.toString()));
            KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
            //下行出错  java.security.spec.InvalidKeySpecException: java.security.InvalidKeyException: IOException: DerInputStream.getLength(): lengthTag=127, too big.
            PublicKey publicKey = keyFactory.generatePublic(pubX509);
            return publicKey;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("READ PUBLIC KEY ERROR:", e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                inputStream = null;
                throw new Exception("INPUT STREAM CLOSE ERROR:", e);
            }
        }
    }

    public static PrivateKey getPrivateKey(InputStream inputStream, String keyAlgorithm) throws Exception {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String readLine = null;
            while ((readLine = br.readLine()) != null) {
                if (readLine.charAt(0) == '-') {
                    continue;
                } else {
                    sb.append(readLine);
                    sb.append('\r');
                }
            }
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(decodeBase64(sb.toString()));
            KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
            PrivateKey privateKey = keyFactory.generatePrivate(priPKCS8);
            return privateKey;
        } catch (Exception e) {
            throw new Exception("READ PRIVATE KEY ERROR:", e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                inputStream = null;
                throw new Exception("INPUT STREAM CLOSE ERROR:", e);
            }
        }
    }

    //一下面是base64的编码和解码
    public static String encodeBase64(byte[] input) throws Exception {
        return Base64.encode(input);
    }

    /***
     * decode by Base64
     */
    public static byte[] decodeBase64(String input) throws Exception {
        return Base64.decode(input);
    }

    public static String encrypt(String value) {
        //RSA工具类提供了，根据加载PKCS8密钥文件的方法
        PublicKey pub = RSAUtils.getPubKey("RSA");
        //rsa是微信付款到银行卡要求我们填充的字符串
        String rsa = "RSA/ECB/OAEPWITHSHA-1ANDMGF1PADDING";
        // 进行加密
        byte[] estr = new byte[0];   //对银行账号进行base加密
        try {
            estr = RSAUtils.encrypt(value.getBytes(), pub, 2048, 11, rsa);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Base64.encode(estr);//并转为base64格式
    }

    public static void main(String[] args) throws Exception {
        //需要被加密的字符串
        String encBankAcctName = "6225"; //加密的银行账户名
        //定义自己公钥的路径
        String keyfile = "E:\\IdeaProjects\\merchant\\src\\main\\resources\\certificate\\rsa_publickey8.pem"; //
        //RSA工具类提供了，根据加载PKCS8密钥文件的方法
        PublicKey pub = RSAUtils.getPubKey("RSA");
        //rsa是微信付款到银行卡要求我们填充的字符串
        String rsa = "RSA/ECB/OAEPWITHSHA-1ANDMGF1PADDING";
        // 进行加密
        byte[] estr = RSAUtils.encrypt(encBankAcctName.getBytes(), pub, 2048, 11, rsa);   //对银行账号进行base加密
        encBankAcctName = Base64.encode(estr);//并转为base64格式
        //测试输出
        System.out.println(encBankAcctName);
    }

}