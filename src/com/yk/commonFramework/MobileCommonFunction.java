package com.yk.commonFramework;

import com.ay.framework.core.action.BaseAction;
import com.ay.framework.core.utils.web.struts.Struts2Utils;
import com.ay.netEasy.im.ConnectToApi;
import com.yk.commonFramework.paramObj.FriendShip;

/**
 * 手机端通用功能
 * @author yzbyp
 *
 */
public class MobileCommonFunction extends BaseAction{
	
	private FriendShip friendShip;
	
	public void friendShips(){
		try {
			String rtn = ConnectToApi.friendShip(friendShip.getFromAccid(), friendShip.getToAccid(), friendShip.getType(), friendShip.getMessage());
			Struts2Utils.renderText(rtn);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public FriendShip getFriendShip() {
		return friendShip;
	}

	public void setFriendShip(FriendShip friendShip) {
		this.friendShip = friendShip;
	}
	
	
	
	
}
