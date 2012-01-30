package com.hybris.chatservice.service.impl;

import com.hybris.chatservice.businesslayer.RoomService;
import com.hybris.chatservice.commonobjects.NewRoomMessageNotification;
import com.hybris.chatservice.service.ChatRoomMessagesService;

import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import java.util.logging.Logger;

/**
 * User: PLMorin
 * Date: 30/01/12
 * Time: 1:42 PM
 */
public class DefaultRestChatRoomMessagesService implements ChatRoomMessagesService {
	private Logger logger = Logger.getLogger(DefaultRestChatRoomMessagesService.class.getName());

	@PathParam("id")
	private String roomId;

	@Inject
	private RoomService roomService;

	@Override
	@PUT
	public void postMessage(@FormParam("message") String message, @FormParam("userId") final String userId) {
		logger.info("Posting to room " + roomId + " message = " + message);
		final NewRoomMessageNotification messageNotification = new NewRoomMessageNotification(roomId, message);
		messageNotification.setUserId(userId);

		this.roomService.postNotification(messageNotification);
	}


}
