package com.hybris.chatservice.businesslayer.impl;

import com.hybris.chatservice.businesslayer.ChatRoomPrivate;
import com.hybris.chatservice.businesslayer.RoomService;
import com.hybris.chatservice.commonobjects.ChatRoom;
import com.hybris.chatservice.commonobjects.Notification;
import com.hybris.chatservice.commonobjects.User;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * User: PLMorin
 * Date: 26/01/12
 * Time: 3:29 PM
 */
@Singleton
@Named
public class DefaultRoomServiceImpl implements RoomService {
	private Logger logger = Logger.getLogger(DefaultRoomServiceImpl.class.getName());
	private Map<String, ChatRoomPrivate> chatRoomPrivate;


	@PostConstruct
	public void init() {
		this.chatRoomPrivate = new ConcurrentHashMap<String, ChatRoomPrivate>();
	}

	@Override
	public void postNotification(final Notification notification) {
		if (notification.getRoomId() != null) {
			this.chatRoomPrivate.get(notification.getRoomId()).addNotification(notification);
		}
	}

	@Override
	public Notification pollNextNotification(final String roomId, final String userId) {
		logger.info("polling: " + roomId + " " + userId);
		return this.chatRoomPrivate.get(roomId).pollNotification(userId);
	}

	@Override
	public ChatRoom info(String roomId) {
		return this.chatRoomPrivate.get(roomId);
	}

	@Override
	public void createRoom(final String id) {
		logger.info("Creating room " + id);

		final ChatRoomPrivate chatRoom = new ChatRoomPrivate();
		chatRoom.setName(id);
		chatRoom.setUsers(new LinkedList<User>());

		this.chatRoomPrivate.put(id, chatRoom);
	}

	@Override
	public void registerUserToRoom(String roomId, String userId) {
		logger.info("Registering user " + userId + " to room " + roomId);
		this.chatRoomPrivate.get(roomId).newUser(userId);
	}

	@Override
	public List<User> listUsers(String roomId) {
		return this.chatRoomPrivate.get(roomId).getUsers();
	}

}
