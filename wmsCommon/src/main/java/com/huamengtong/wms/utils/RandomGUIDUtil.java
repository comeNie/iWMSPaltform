package com.huamengtong.wms.utils;

import java.util.Random;

/**
 * 产生唯一的随机字符串
 * 
 */
public class RandomGUIDUtil {

	private static char[] NUMBER_POOL = new char[] { '0', '1', '2', '3', '4',
			'5', '6', '7', '8', '9' };

	private static Random random = new Random(System.currentTimeMillis());

	/**
	 * 产生唯一的随机字符串
	 * 
	 * @return
	 */
	public static String generateKey() {
		return new RandomGUID(true).toString();
	}

	/***
	 * 获取指定长度的随机数
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomNumber(int length) {
		if (length <= 0) {
			throw new IllegalArgumentException();
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int tmp = random.nextInt(10);
			sb.append(NUMBER_POOL[tmp]);
		}
		return sb.toString();
	}
}