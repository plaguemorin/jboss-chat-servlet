package com.hybris.chatservice.commonobjects;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * User: PLMorin
 * Date: 26/01/12
 * Time: 11:42 AM
 */

@XmlRootElement
public class UserLeaveRoomNotification extends Notification {

	public UserLeaveRoomNotification() {
		super();
	}

	public UserLeaveRoomNotification(String roomId, String userId) {
		super(roomId);
		super.setUserId(userId);
	}
}
