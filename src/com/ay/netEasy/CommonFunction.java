package com.ay.netEasy;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.ay.jfds.sys.pojo.User;
import com.ay.netEasy.im.ConnectToApi;

public class CommonFunction {

	public static String getToken(String responseToken) throws JSONException{
		
		JSONObject jo = new JSONObject(responseToken);
		JSONObject infoJo = jo.getJSONObject("info");
		String token = infoJo.getString("token");
		return token;
	}
	
	public static void main(String[] args) {
		try {
			String resoponseToken = ConnectToApi.friendShip("wangkai", "chengzehao", 1, "");
			//String resoponseToken = ConnectToApi.insertNewChatUser("lisi", "李四");
			//String resoponseToken = ConnectToApi.getNewToken("zhangsan");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 新用户添加所有用户作为好友
	 * @param accid
	 * @param userList
	 * @return
	 */
	public static String newUserRelation(String accid,List<User> userList){
		String rtn = null;
		for(User user : userList){
			try {
				String resoponseToken = ConnectToApi.friendShip(accid, user.getAccount(), 1, "");
				rtn = resoponseToken;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rtn;
	} 
}
