package ca.screenshot.chatservice.service;

import ca.screenshot.chatservice.commonobjects.ChatRoom;

/**
 * User: PLMorin
 * Date: 26/01/12
 * Time: 11:50 AM
 */

public interface ChatRoomService {

	ChatRoom info(final String roomId);

	/**
	 * Creates a room, only a moderator can create a room
	 *
	 * @param roomId
	 */
	void createRoom(final String roomId);

	/**
	 * Deletes a room
	 *
	 * @param roomId
	 */
	void deleteRoom(final String roomId);

	/**
	 * Returns the membership service for this room
	 * @param roomId
	 * @return membership service
	 */
	ChatRoomMembershipService membership(final String roomId);

	/**
	 *
	 * @param roomId
	 * @return the sub-service
	 */
	ChatRoomMessagesService messages(final String roomId);
}
