package com.hybris.chatservice.businesslayer;

import com.hybris.chatservice.commonobjects.ChatRoom;
import com.hybris.chatservice.commonobjects.Notification;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * User: PLMorin
 * Date: 26/01/12
 * Time: 5:18 PM
 */
public class ChatRoomPrivate extends ChatRoom {
	private Logger logger = Logger.getLogger(ChatRoomPrivate.class.getName());

	private Map<String, ChatRoomPerUserPrivate> perUserData;

	public ChatRoomPrivate() {
		this.perUserData = new ConcurrentHashMap<String, ChatRoomPerUserPrivate>();
	}

	public void addNotification(final Notification notification) {
		logger.info("Adding notification " + notification.getClass().getName() + " to room " + this.getName());

		for (final ChatRoomPerUserPrivate chatRoomPerUserPrivate : perUserData.values()) {
			chatRoomPerUserPrivate.addNotification(notification);
		}
	}

	public Notification pollNotification(final String userId) {
		return this.perUserData.get(userId).poll();
	}

	public void newUser(String userId) {
		if (this.perUserData.containsKey(userId)) {
			return;
		}

		final ChatRoomPerUserPrivate chatRoomPerUserPrivate = new ChatRoomPerUserPrivate();
		this.perUserData.put(userId, chatRoomPerUserPrivate);

		this.getUsers().add(userId);
	}
}
