package com.sdust.news.utils;

/**
 * ===================================================================
 * 
 * 版 权：山东科技大学 电物学院 毕业设计 2015
 * 
 * 作 者：左 岩
 * 
 * 版 本：1.0
 * 
 * 创建日期：2015-3-20 下午4:02:37
 * 
 * 描 述：
 * 		MD5工具类
 * 修订历史：
 * 
 * ===================================================================
 */
import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;

public class MD5Utils {

	/**
	 * Get MD5 of file
	 * 
	 * @param filePath
	 * @return MD5
	 */
	public static String getFileMd5(String filePath) {
		String md5Str = null;

		try {
			MessageDigest md = MessageDigest.getInstance("md5");

			FileInputStream inputStream = new FileInputStream(
					new File(filePath));
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = inputStream.read(buffer)) != -1) {
				md.update(buffer, 0, len);
			}

			byte[] digest = md.digest();
			StringBuffer sb = new StringBuffer();
			for (byte b : digest) {
				int num = b & 0XFF;
				String hexString = Integer.toHexString(num);

				if (hexString.length() == 1) {
					sb.append("0" + hexString);
				} else {
					sb.append(hexString);
				}
			}
			md5Str = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return md5Str;
	}

	/**
	 * Compare if the MD5stings are same
	 * 
	 * @param filePath
	 * @param md5
	 * @return result
	 */
	public static boolean compareFileMD5(String filePath, String md5) {
		return getFileMd5(filePath) == md5 ? true : false;
	}
}
