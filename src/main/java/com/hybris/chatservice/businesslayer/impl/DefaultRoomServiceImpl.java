package com.hybris.chatservice.businesslayer.impl;

import com.hybris.chatservice.businesslayer.ChatRoomPrivate;
import com.hybris.chatservice.businesslayer.RoomService;
import com.hybris.chatservice.commonobjects.ChatRoom;
import com.hybris.chatservice.commonobjects.InvalidChatRoomException;
import com.hybris.chatservice.commonobjects.Notification;
import com.hybris.chatservice.commonobjects.User;

import javax.annotation.PostConstruct;
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
		if (notification.getRoomId() != null && this.chatRoomPrivate.containsKey(notification.getRoomId())) {
			this.chatRoomPrivate.get(notification.getRoomId()).addNotification(notification);
		}
	}

	@Override
	public Notification pollNextNotification(final String roomId, final String userId) throws InvalidChatRoomException {
		logger.info("polling: " + roomId + ", user: " + userId);

		if (!this.chatRoomPrivate.containsKey(roomId)) {
			throw new InvalidChatRoomException("Room " + roomId + " does not exists");
		}

		return this.chatRoomPrivate.get(roomId).pollNotification(userId);
	}

	@Override
	public ChatRoom info(String roomId) {
		return this.chatRoomPrivate.get(roomId);
	}

	@Override
	public void createRoom(final String id) throws InvalidChatRoomException {
		logger.info("Creating room " + id);

		if (this.chatRoomPrivate.containsKey(id)) {
			throw new InvalidChatRoomException("Room " + id + " already exists");
		}

		final ChatRoomPrivate chatRoom = new ChatRoomPrivate();
		chatRoom.setName(id);
		chatRoom.setUsers(new LinkedList<User>());

		this.chatRoomPrivate.put(id, chatRoom);
	}

	@Override
	public void registerUserToRoom(String roomId, String userId) throws InvalidChatRoomException {
		logger.info("Registering user " + userId + " to room " + roomId);
		
		if (this.chatRoomPrivate.containsKey(roomId)) {
			this.chatRoomPrivate.get(roomId).newUser(userId);
		} else {
			throw new InvalidChatRoomException("Room " + roomId + " does not exists");
		}
	}

	@Override
	public List<User> listUsers(String roomId) {
		return this.chatRoomPrivate.get(roomId).getUsers();
	}

}
