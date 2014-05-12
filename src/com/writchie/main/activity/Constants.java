package com.writchie.main.activity;

import android.os.Environment;


public class Constants {
	/**
	 ******************************************* ����������Ϣ��ʼ ******************************************
	 */

	// Ӧ������
	public static String APP_NAME = "";

	// ͼƬ·��
	public static final String IMAGE_URL = "http://58.211.5.34:8080/studioms/staticmedia/images/#";

	// ��Ƶ·��
	public static final String VIDEO_URL = "http://58.211.5.34:8080/studioms/staticmedia/video/#";

	// ��������ļ�������
	public static final String SHARED_PREFERENCE_NAME = "itau_jingdong_prefs";

	// SDCard·��
	public static final String SD_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath();

	// ͼƬ�洢·��
	public static final String BASE_PATH = SD_PATH + "/iTau/jingdong/";

	// ����ͼƬ·��
	public static final String BASE_IMAGE_CACHE = BASE_PATH + "cache/images/";

	// ��Ҫ������ͼƬ
	public static final String SHARE_FILE = BASE_PATH + "QrShareImage.png";

	// �ֻ�IMEI����
	public static String IMEI = "";

	// �ֻ�����
	public static String TEL = "";

	// ��Ļ�߶�
	public static int SCREEN_HEIGHT = 800;

	// ��Ļ����
	public static int SCREEN_WIDTH = 480;

	// ��Ļ�ܶ�
	public static float SCREEN_DENSITY = 1.5f;

	// �����ɹ�
	public static final int SHARE_SUCCESS = 0X1000;

	// ����ȡ��
	public static final int SHARE_CANCEL = 0X2000;

	// ����ʧ��
	public static final int SHARE_ERROR = 0X3000;

	// ��ʼִ��
	public static final int EXECUTE_LOADING = 0X4000;

	// ����ִ��
	public static final int EXECUTE_SUCCESS = 0X5000;

	// ִ�����
	public static final int EXECUTE_FAILED = 0X6000;

	// �������ݳɹ�
	public static final int LOAD_DATA_SUCCESS = 0X7000;

	// ��������ʧ��
	public static final int LOAD_DATA_ERROR = 0X8000;

	// ��̬��������
	public static final int SET_DATA = 0X9000;

	// δ��¼
	public static final int NONE_LOGIN = 0X10000;
	//
	public static final String HTTP_GET = "get";
	
	
	public static final String HTTP_POST= "post";
	
	public static final String GRAPH_SIMPLE_USER_INFO= "https://graph.qq.com/user/get_simple_userinfo";

	/**
	 ******************************************* ����������Ϣ���� ******************************************
	 */
}