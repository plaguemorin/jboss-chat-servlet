package com.hybris.chatservice.service;

import com.hybris.chatservice.commonobjects.ChatRoom;

/**
 * User: PLMorin
 * Date: 26/01/12
 * Time: 11:50 AM
 */

public interface ChatRoomService {

	ChatRoom info();

	/**
	 * Creates a room, only a moderator can create a room
	 *
	 */
	void createRoom();

	/**
	 * Deletes a room
	 *
	 */
	void deleteRoom();

	/**
	 * Kicks userId from roomId
	 *
	 * @param userId the user to kick
	 */
	void kickUser(final String userId);

	/**
	 * Ban userId from roomId
	 *
	 * @param userId the user to kick
	 */
	void banUser(final String userId);

	/**
	 * Returns the membership service for this room
	 * @return membership service
	 */
	ChatRoomMembershipService membership();


	/**
	 *
	 * @return the sub-service
	 */
	ChatRoomMessagesService messages();
}
