package com.sdust.news.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * ===================================================================
 * 
 * 版 权：山东科技大学 电物学院 毕业设计 2015
 * 
 * 作 者：左 岩
 * 
 * 版 本：1.0
 * 
 * 创建日期：2015-3-18 上午10:16:16
 * 
 * 描 述： SharedPreferencesUtil
 * 
 * 修订历史：
 * 
 * ===================================================================
 */
public class SPUtil {
	public static final String SP_NAME = "config";
	private static SharedPreferences sp;

	/**
	 * Method to save a boolean in SP
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void saveBoolean(Context context, String key, boolean value) {
		if (sp == null) {
			sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		}
		sp.edit().putBoolean(key, value).commit();
	}

	/**
	 * Method to get a boolean from SP
	 * 
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static boolean getBoolean(Context context, String key,
			boolean defValue) {
		if (sp == null) {
			sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		}
		return sp.getBoolean(key, defValue);
	}

	/**
	 * Method to save a string in SP
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void saveString(Context context, String key, String value) {
		if (sp == null) {
			sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		}
		sp.edit().putString(key, value).commit();
	}

	/**
	 * Method to get a string from SP
	 * 
	 * @param context
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static String getString(Context context, String key, String defValue) {
		if (sp == null) {
			sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		}
		return sp.getString(key, defValue);
	}
}
