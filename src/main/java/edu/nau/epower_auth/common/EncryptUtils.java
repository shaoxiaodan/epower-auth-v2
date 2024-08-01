package edu.nau.epower_auth.common;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 加密工具类
 * 
 * @ClassName: EncryptUtils
 * @Description: TODO
 * @author Xiaodan Shao(xs94@nau.edu)
 * @date 2024-07-08 07:45:33
 */
public class EncryptUtils {

	// apache的MD5加密(128位)
	public static String getMD5(String inputStr) {
		return DigestUtils.md5Hex(inputStr);
	}

	// apache的SHA256加密(256位)
	public static String getSHA256(String inputStr) {
		return DigestUtils.sha256Hex(inputStr);
	}

	// 测试方法
	public static void main(String[] args) {

		String ins = "123";
		String md5 = EncryptUtils.getMD5(ins);
		String sha256 = EncryptUtils.getSHA256(ins);

		System.out.println("md5=" + md5);
		System.out.println("sha256=" + sha256);
	}

}
