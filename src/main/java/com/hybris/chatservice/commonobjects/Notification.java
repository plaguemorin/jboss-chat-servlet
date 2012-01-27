package com.hybris.chatservice.commonobjects;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * User: PLMorin
 * Date: 26/01/12
 * Time: 11:41 AM
 */
@XmlRootElement
public class Notification {
	private String userId;

	private String roomId;

	public Notification(String roomId) {
		this.roomId = roomId;
	}

	public Notification() {

	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
}
