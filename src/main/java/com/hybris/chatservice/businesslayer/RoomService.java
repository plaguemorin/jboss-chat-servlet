package com.hybris.chatservice.businesslayer;

import com.hybris.chatservice.commonobjects.ChatRoom;
import com.hybris.chatservice.commonobjects.InvalidChatRoomException;
import com.hybris.chatservice.commonobjects.Notification;

import java.util.List;
import java.util.Set;

/**
 * User: PLMorin
 * Date: 26/01/12
 * Time: 3:28 PM
 */
public interface RoomService {
	void postNotification(final Notification notification);

	Notification pollNextNotification(final String roomId, final String userId) throws InvalidChatRoomException;

	ChatRoom info(final String roomId);

	void createRoom(final String id) throws InvalidChatRoomException;

	void registerUserToRoom(String roomId, String userId) throws InvalidChatRoomException;

	List<String> listUsers(String roomId);

	Set<String> listRooms();

	void removeUserFromRoom(String roomId, String userId) throws InvalidChatRoomException;
}
