package com.seancheer.utils;

import com.seancheer.common.BlogConstants;
import com.seancheer.exception.BlogBaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Base64;

/**
 * 进行aes解密需要的类，验证用户身份时会使用
 *
 * @author seancheer
 * @date 2018年3月8日
 */
public class AESHelper {

    private static final Logger logger = LoggerFactory.getLogger(AESHelper.class);

    private static final String ALGORITHM = "AES";

    /**
     * 加密
     *
     * @param source
     * @param key 加解密的key应当一致
     */
    public static String encrypt(String source, String key) throws BlogBaseException {
        try {
            Key secretKey = getKey(key);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] plain = source.getBytes(BlogConstants.DEFAULT_ENCODING);
            byte[] result = cipher.doFinal(plain);
            Base64.Encoder encoder = Base64.getEncoder();
            return encoder.encodeToString(result);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException
                | BadPaddingException | IllegalBlockSizeException e) {
            logger.error("Encrypting failed!", e);
            throw new BlogBaseException(e);
        }
    }

    /**
     * 使用密钥对密文进行解密
     *
     * @param encryptText
     * @param key 加解密的key应当一致
     * @return
     */
    public static String decrypt(String encryptText, String key) throws BlogBaseException {
        try {
            Key secretKey = getKey(key);
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] decodeEncryptText = decoder.decode(encryptText);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] result = cipher.doFinal(decodeEncryptText);
            return new String(result, BlogConstants.DEFAULT_ENCODING);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException
                | BadPaddingException | IllegalBlockSizeException e) {
            logger.error("Encrypting failed!", e);
            throw new BlogBaseException(e);
        }
    }

    /**
     * 生成加解密需要的key对象
     *
     * @param aesKey
     * @return
     */
    private static Key getKey(String aesKey) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.setSeed(aesKey.getBytes(BlogConstants.DEFAULT_ENCODING));
        KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM);
        generator.init(secureRandom);
        return generator.generateKey();
    }

    /**
     * 测试加解密方法是否正常工作
     * @param args
     * @throws BlogBaseException
     */
    public static void main(String[] args) throws BlogBaseException {
    	final String DEFAULT_SECRET_KEY = "seancheer'ssecretkey";
        String key = DEFAULT_SECRET_KEY;
        String plainText = "hellohelloo";
        String encryptText = AESHelper.encrypt(plainText,key);
        System.out.println(encryptText);

        System.out.println(AESHelper.decrypt(encryptText,key));
    }
}
