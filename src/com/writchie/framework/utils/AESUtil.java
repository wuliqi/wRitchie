package com.writchie.framework.utils;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

 
/**
 * AES���ļ����ַ������м��ܽ��ܹ���
 * @author ������
 *
 */
public class AESUtil {
    // 
	/**
	 * ������Կ�����ļ���
	 * @param sSrc �����ַ���
	 * @param sKey ��Կ
	 * @return String ������null��ʾ����ʧ�ܣ����򷵻ؼ��ܺ���ַ���
	 */
    public static String Encrypt(String sSrc, String sKey)  {
        try {
			if (sKey == null) {
			    System.out.print("KeyΪ��null");
			    return null;
			}
			// �ж�Key�Ƿ�Ϊ16λ
			if (sKey.length() != 16) {
			    System.out.print("Key���Ȳ���16λ");
			    return null;
			}
			byte[] raw = sKey.getBytes();
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			byte[] encrypted = cipher.doFinal(sSrc.getBytes());
			return byte2hex(encrypted).toLowerCase();
		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		} 
    }

    /**
     * ������Կ�����Ľ���
     * @param sSrc �����ַ���
     * @param sKey ��Կ
     * @return String ������null��ʾ����ʧ�ܣ����򷵻ؼ��ܺ���ַ���
     */
    public static String Decrypt(String sSrc, String sKey) {
        try {
            // �ж�Key�Ƿ���ȷ
            if (sKey == null) {
                System.out.print("KeyΪ��null");
                return null;
            }
            // �ж�Key�Ƿ�Ϊ16λ
            if (sKey.length() != 16) {
                System.out.print("Key���Ȳ���16λ");
                return null;
            }
            byte[] raw = sKey.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec("0102030405060708"
                    .getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = hex2byte(sSrc);
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original);
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public static byte[] hex2byte(String strhex) {
        if (strhex == null) {
            return null;
        }
        int l = strhex.length();
        if (l % 2 == 1) {
            return null;
        }
        byte[] b = new byte[l / 2];
        for (int i = 0; i != l / 2; i++) {
            b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2),
                    16);
        }
        return b;
    }

    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }

    /**
     * �����ļ�
     * @param filePath:�������ļ�����/sdcard/encryxml/xml2DB.xml
     * @param sKey:������Կ,a_z,A_Z,0_9������ĸ���
     * @param destFilePath:�ļ����ܺ��·������/sdcard/decryptxml/
     */
    public static void encryptFile(String filePath, String sKey,String destFilePath)
    {
    	
    	BufferedReader bufferReader=null;
    	BufferedWriter writer=null;
    	try {
			bufferReader=new BufferedReader(new FileReader(filePath));
			File destFilePathFolder=new File(destFilePath);
			if(!destFilePathFolder.exists())destFilePathFolder.mkdirs();
			int slashLoc=filePath.lastIndexOf(File.separator);
			String fileName=filePath.substring(slashLoc+1,filePath.length());
			writer=new BufferedWriter(new FileWriter(destFilePath+File.separator+fileName));
			String data=null;
			while((data=bufferReader.readLine())!=null)
			{
				String encryStr=AESUtil.Encrypt(data, sKey);
				writer.write(encryStr);
				writer.newLine();
				writer.flush();
			}
		} catch (Exception e) {
			System.out.println("�����ļ��쳣��"+e);
		}finally{
			try {
				writer.close();
				bufferReader.close();
			} catch (IOException e) {
				System.out.println("�����ļ��ر����쳣:"+e);
			}
		}
    }
   
    /**
     * �����ļ�
     * @param filePath:�������ļ�����/sdcard/decryptxml/xml2DB.xml
     * @param sKey:������Կ,a_z,A_Z,0_9������ĸ���
     * @param destFilePath:�ļ����ܺ��·������/sdcard/encryptxml/
     */
    public static void decryptFile(String filePath, String sKey,String destFilePath)
    {
    	BufferedReader bufferReader=null;
    	BufferedWriter writer=null;
    	try {
    		bufferReader=new BufferedReader(new FileReader(filePath));
    		File destFilePathFolder=new File(destFilePath);
    		if(!destFilePathFolder.exists())destFilePathFolder.mkdirs();
    		int slashLoc=filePath.lastIndexOf(File.separator);
    		String fileName=filePath.substring(slashLoc+1,filePath.length());
    		writer=new BufferedWriter(new FileWriter(destFilePath+File.separator+fileName));
    		String data=null;
    		while((data=bufferReader.readLine())!=null)
    		{
    			String decryStr=AESUtil.Decrypt(data, sKey);
    			writer.write(decryStr);
    			writer.newLine();
    			writer.flush();
    		}
    	} catch (Exception e) {
    		System.out.println("�����ļ��쳣��"+e);
    	}finally{
    		try {
    			writer.close();
    			bufferReader.close();
    		} catch (IOException e) {
    			System.out.println("�����ļ��ر����쳣��"+e);
    		}
    	}
    }
}