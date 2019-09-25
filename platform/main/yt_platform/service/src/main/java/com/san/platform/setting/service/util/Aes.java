package com.san.platform.setting.service.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class Aes {
    /**
     * 加密
     * @param content 需要加密的内容
     * @param password 加密密码
     * @return
     */
    public String encrypt(String content, String password) {
        try {
            //KeyGenerator kgen = KeyGenerator.getInstance("AES");//生成指定算法的密钥生成器对象
            //kgen.init(128, new SecureRandom(password.getBytes()));//初始化此密钥生成器,AES要求密钥长度为128
            //SecretKey secretKey = kgen.generateKey();//生成一个密钥
        	//byte[] enCodeFormat = secretKey.getEncoded();
        	
            SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
            
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//创建密码器
            cipher.init(Cipher.ENCRYPT_MODE, key);//密钥初始化，设置为加密模式
            
            byte[] result = cipher.doFinal(content.getBytes("utf-8"));//执行操作
            return parseByte2HexStr(result);
        } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
        } catch (NoSuchPaddingException e) {
                e.printStackTrace();
        } catch (InvalidKeyException e) {
                e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
        } catch (BadPaddingException e) {
                e.printStackTrace();
        }
        return null;
    }
    
    /**解密
     * @param content  待解密内容
     * @param password 解密密钥
     * @return
     */
    public String decrypt(String content, String password) {
        try {
        	byte[] decryptFrom = parseHexStr2Byte(content);
        	//KeyGenerator kgen = KeyGenerator.getInstance("AES");//生成指定算法的密钥生成器对象
            //kgen.init(128, new SecureRandom(password.getBytes()));//初始化此密钥生成器,AES要求密钥长度为128
            //SecretKey secretKey = kgen.generateKey();//生成一个密钥
        	//byte[] enCodeFormat = secretKey.getEncoded();
        	
            SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");   
            
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);//密钥初始化，设置为解密模式
            
            byte[] result = cipher.doFinal(decryptFrom);//执行操作
            return new String(result);
        } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
        } catch (NoSuchPaddingException e) {
                e.printStackTrace();
        } catch (InvalidKeyException e) {
                e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
        } catch (BadPaddingException e) {
                e.printStackTrace();
        }
        return null;
    }
    
    /**将二进制转换成16进制
     * @param buf
     * @return
     */
    public String parseByte2HexStr(byte buf[]) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < buf.length; i++) {
                    String hex = Integer.toHexString(buf[i] & 0xFF);
                    if (hex.length() == 1) {
                            hex = '0' + hex;
                    }
                    sb.append(hex.toUpperCase());
            }
            return sb.toString();
    }
    
    /**将16进制转换为二进制
     * @param hexStr
     * @return
     */
    public byte[] parseHexStr2Byte(String hexStr) {
            if (hexStr.length() < 1)
                    return null;
            byte[] result = new byte[hexStr.length()/2];
            for (int i = 0;i< hexStr.length()/2; i++) {
                    int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
                    int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
                    result[i] = (byte) (high * 16 + low);
            }
            return result;
    }
	
	/**
	 * @author chenjie
	 * @date 2015-7-27
	 * @Description: TODO
	 * @param args
	 */
//	public static void main(String[] args) {
//		Aes des = new Aes();
//        String content = "ecard123123123";
//        String password = "xkp0000000000000";
//        System.out.println("加密前：" + content);
//        //加密
//        String encryptResultStr = des.encrypt(content, password);
//        System.out.println("加密后：" + encryptResultStr);
//        //解密
//        String decryptResultStr = des.decrypt(encryptResultStr,password);
//        System.out.println("解密后：" + decryptResultStr);
//        //解密
//        String decryptResultStr2 = des.decrypt("C30D5A7D87F4FC76F46DBB411A57BCD8",password);
//        System.out.println("解密后：" + decryptResultStr2);
//	}
}
