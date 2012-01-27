package com.hybris.chatservice.commonobjects;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * User: PLMorin
 * Date: 26/01/12
 * Time: 11:42 AM
 */
@XmlRootElement
public class UserEnterRoomNotification extends Notification {

	public UserEnterRoomNotification() {
		super();
	}

	public UserEnterRoomNotification(String roomId, String userId) {
		super(roomId);
		super.setUserId(userId);
		super.setMessage("User " + userId + " has joined to room " + roomId);
	}
}
