package com.writchie.framework.utils;

import java.io.UnsupportedEncodingException;

import android.util.Base64;


/**
 * 
 * Base64 加密解密
 * 
 * @author abu
 *
 */

public class Base64Util {

	public static String decode(String str) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		return new String(Base64.decode(str.getBytes(), Base64.DEFAULT),
				"UTF-8");
	}

	public static String encode(String str) {
		// TODO Auto-generated method stub
		return new String(Base64.encode(str.getBytes(), Base64.DEFAULT));
	}
}
