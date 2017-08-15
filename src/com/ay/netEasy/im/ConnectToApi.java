package com.ay.netEasy.im;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

public class ConnectToApi {
	
	//设置服务端信息
	public static final String APPKEY = "e052d8e53b356b1d2944e82ad68d12b2";
	public static final String APPSECRIT = "f0340fc2e0d0";
	public static final String NONCE = "337962";

	public static void main(String[] args) throws Exception {
		
		

	}
	
	/**
	 * 创建云信用户
	 * @param accid
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public static String insertNewChatUser(String accid,String name) throws Exception{
		String curTime = String.valueOf((new Date()).getTime() / 1000L);
		String checkSum = CheckSumBuilder.getCheckSum(APPSECRIT, NONCE, curTime); 
		String url = "https://api.netease.im/nimserver/user/create.action";
		String rtn = null;
		String s = "accid="+accid+"&name="+name;
		
		URL urill = new URL(url);
		
		HttpURLConnection hulc = (HttpURLConnection)urill.openConnection();
		
		hulc.setRequestMethod("POST");
		hulc.setDoOutput(true);
		hulc.setDoInput(true);
		hulc.setUseCaches(false);
		
		hulc.setConnectTimeout(6000);
		hulc.setReadTimeout(6000);
		
		hulc.setRequestProperty("AppKey", APPKEY);
		hulc.setRequestProperty("Nonce", NONCE);
		hulc.setRequestProperty("CurTime", curTime);
		hulc.setRequestProperty("CheckSum", checkSum);
		hulc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		
		OutputStream os = hulc.getOutputStream();
		os.write(s.getBytes("UTF-8"));
		os.flush();
		os.close();
		int responseCode = hulc.getResponseCode();
		System.out.println(responseCode);
		if(responseCode == 200){
			BufferedReader br = new BufferedReader(new InputStreamReader(hulc.getInputStream(), "UTF-8"));
			rtn = br.readLine();
		}
		System.out.println(rtn);
		return rtn;
	}

	public static String getNewToken(String accid) throws Exception{
		String curTime = String.valueOf((new Date()).getTime() / 1000L);
		String checkSum = CheckSumBuilder.getCheckSum(APPSECRIT, NONCE, curTime); 
		String url = "https://api.netease.im/nimserver/user/refreshToken.action";
		String rtn = null;
		String s = "accid="+accid;
		
		URL urill = new URL(url);
		
		HttpURLConnection hulc = (HttpURLConnection)urill.openConnection();
		
		hulc.setRequestMethod("POST");
		hulc.setDoOutput(true);
		hulc.setDoInput(true);
		hulc.setUseCaches(false);
		
		hulc.setConnectTimeout(6000);
		hulc.setReadTimeout(6000);
		
		hulc.setRequestProperty("AppKey", APPKEY);
		hulc.setRequestProperty("Nonce", NONCE);
		hulc.setRequestProperty("CurTime", curTime);
		hulc.setRequestProperty("CheckSum", checkSum);
		hulc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		
		OutputStream os = hulc.getOutputStream();
		os.write(s.getBytes("UTF-8"));
		os.flush();
		os.close();
		int responseCode = hulc.getResponseCode();
		System.out.println(responseCode);
		if(responseCode == 200){
			BufferedReader br = new BufferedReader(new InputStreamReader(hulc.getInputStream(), "UTF-8"));
			rtn = br.readLine();
		}
		System.out.println(rtn);
		return rtn;
	}
	/**
	 * 好友关系
	 * @param fromAccid
	 * @param toAccid
	 * @param type
	 * @param message
	 * @return
	 * @throws Exception 
	 */
	public static String friendShip(String fromAccid,String toAccid,int type,String message) throws Exception{
		
		String curTime = String.valueOf((new Date()).getTime() / 1000L);
		String checkSum = CheckSumBuilder.getCheckSum(APPSECRIT, NONCE, curTime); 
		String url = "https://api.netease.im/nimserver/friend/add.action";
		String rtn = null;
		String s = "accid="+fromAccid+"&faccid="+toAccid+"&type="+type+"&msg="+message;
		
		URL urill = new URL(url);
		
		HttpURLConnection hulc = (HttpURLConnection)urill.openConnection();
		
		hulc.setRequestMethod("POST");
		hulc.setDoOutput(true);
		hulc.setDoInput(true);
		hulc.setUseCaches(false);
		
		hulc.setConnectTimeout(6000);
		hulc.setReadTimeout(6000);
		
		hulc.setRequestProperty("AppKey", APPKEY);
		hulc.setRequestProperty("Nonce", NONCE);
		hulc.setRequestProperty("CurTime", curTime);
		hulc.setRequestProperty("CheckSum", checkSum);
		hulc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		
		OutputStream os = hulc.getOutputStream();
		os.write(s.getBytes("UTF-8"));
		os.flush();
		os.close();
		int responseCode = hulc.getResponseCode();
		System.out.println(responseCode);
		if(responseCode == 200){
			BufferedReader br = new BufferedReader(new InputStreamReader(hulc.getInputStream(), "UTF-8"));
			rtn = br.readLine();
		}
		System.out.println(rtn);
		return rtn;
	}
}
