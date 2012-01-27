package com.hybris.chatservice.commonobjects;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Calendar;

/**
 * User: PLMorin
 * Date: 26/01/12
 * Time: 11:41 AM
 */
@XmlRootElement
public class Notification {
	private String userId;
	private String roomId;
	private String message;
	private Long date;

	public Notification(String roomId) {
		this();
		this.roomId = roomId;
	}

	public Notification() {
		this.date = Calendar.getInstance().getTimeInMillis();
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}
}
