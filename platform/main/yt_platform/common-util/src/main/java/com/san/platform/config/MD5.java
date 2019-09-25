package com.san.platform.config;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 系统用户密码MD5+salt
 */
public class MD5 {

	private static final String[] GOAL = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	private static String salt = "yang_platform";    //盐值

	public static String getMD5Password(String object){
		String result = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			//digest在底层也调用了update方法
			result = byteArrayToString(digest.digest(addSalt(object).getBytes()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return result.toUpperCase();
	}

	/**
	 * 在加密对象后加盐
	 * @param object
	 * @return
	 */
	private static String addSalt(String object){
		if(object == null){
			object = "";
		}
		if(salt == null || "".equals(salt)){
			return object;
		} else {
			return object + "{"+ salt.toString() +"}";
		}
	}

	private static String byteArrayToString(byte[] ss){
		StringBuffer result = new StringBuffer();
		for(int i = 0; i < ss.length; i++){
			result.append(byteToString(ss[i]));
		}
		return result.toString();
	}

	private static String byteToString(byte ss){
		int temp;
		temp = ss < 0 ? ss + 256 : ss;
		return GOAL[temp / 16] + GOAL[temp % 16];       //自己实现转化

	}

	public static void main(String[] args) {
		System.out.println(MD5.getMD5Password("1qaz@WSX"));
	}
}
