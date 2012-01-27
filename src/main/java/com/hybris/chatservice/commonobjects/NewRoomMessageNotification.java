package com.hybris.chatservice.commonobjects;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NewRoomMessageNotification extends Notification  {
	private String message;

	public NewRoomMessageNotification() {
		super();
	}

	public NewRoomMessageNotification(String roomId, String message) {
		super(roomId);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
