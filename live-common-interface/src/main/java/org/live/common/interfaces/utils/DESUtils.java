package org.live.common.interfaces.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * @Author LIMBO0523
 * @Date 2024/10/3 10:23
 */
public class DESUtils {
    // 算法名称
    public static final String KEY_ALGORITHM = "DES";
    // 算法名称/加密模式/填充方式
    // DES 共有四种工作模式-->>ECB：电子密码本模式、CBC：加密分组链接模式、CFB：加密反馈模式、OFB：输出反馈模式
    public static final String CIPHER_ALGORITHM = "DES/ECB/PKCS5Padding";
    public static final String PUBLIC_KEY = "BAS9j2C3D4E5F60708";
    /**
     * 生成密钥 key 对象
     *
     * @param keyStr 密钥字符串
     * @return 密钥对象
     * @throws Exception
     */
    private static SecretKey keyGenerator(String keyStr) {
        byte input[] = HexString2Bytes(keyStr);
        DESKeySpec desKey = null;
        try {
            desKey = new DESKeySpec(input);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        // 创建一个密匙工厂，然后用它把 DESKeySpec 转换成
        SecretKeyFactory keyFactory = null;
        try {
            keyFactory = SecretKeyFactory.getInstance("DES");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        SecretKey securekey = null;
        try {
            securekey = keyFactory.generateSecret(desKey);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        return securekey;
    }

    private static int parse(char c) {
        if (c >= 'a')
            return (c - 'a' + 10) & 0x0f;
        if (c >= 'A')
            return (c - 'A' + 10) & 0x0f;
        return (c - '0') & 0x0f;
    }
    // 从十六进制字符串到字节数组转换
    public static byte[] HexString2Bytes(String hexstr) {
        byte[] b = new byte[hexstr.length() / 2];
        int j = 0;
        for (int i = 0; i < b.length; i++) {
            char c0 = hexstr.charAt(j++);
            char c1 = hexstr.charAt(j++);
            b[i] = (byte) ((parse(c0) << 4) | parse(c1));
        }
        return b;
    }
    /**
     * 加密数据
     *
     * @param data 待加密数据
     * @return 加密后的数据
     */
    public static String encrypt(String data) {

        Key deskey = null;
        try {
            deskey = keyGenerator(PUBLIC_KEY);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 实例化 Cipher 对象，它用于完成实际的加密操作
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
        SecureRandom random = new SecureRandom();
        // 初始化 Cipher 对象，设置为加密模式
        try {
            cipher.init(Cipher.ENCRYPT_MODE, deskey, random);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        byte[] results = null;
        try {
            results = cipher.doFinal(data.getBytes());
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
        // 执行加密操作。加密后的结果通常都会用 Base64 编码进行传输
        return Base64.encodeBase64String(results);
    }

    /**
     * 解密数据
     *
     * @param data 待解密数据
     * @return 解密后的数据
     */
    public static String decrypt(String data) throws RuntimeException {
        Key deskey = null;
        try {
            deskey = keyGenerator(PUBLIC_KEY);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
        // 初始化 Cipher 对象，设置为解密模式
        try {
            cipher.init(Cipher.DECRYPT_MODE, deskey);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        // 执行解密操作
        try {
            return new String(cipher.doFinal(Base64.decodeBase64(data)));
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }
}
