package com.hybris.chatservice.commonobjects;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NewRoomMessageNotification extends Notification  {

	public NewRoomMessageNotification() {
		super();
	}

	public NewRoomMessageNotification(String roomId, String message) {
		super(roomId);
		super.setMessage(message);
	}

}
