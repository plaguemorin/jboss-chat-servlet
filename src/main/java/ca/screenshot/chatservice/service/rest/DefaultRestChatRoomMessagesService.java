package ca.screenshot.chatservice.service.rest;

import ca.screenshot.chatservice.businesslayer.RoomService;
import ca.screenshot.chatservice.commonobjects.NewRoomMessageNotification;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.logging.Logger;

/**
 * User: PLMorin
 * Date: 30/01/12
 * Time: 1:42 PM
 */
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
@Produces({MediaType.APPLICATION_JSON})
public class DefaultRestChatRoomMessagesService {
	private Logger logger = Logger.getLogger(DefaultRestChatRoomMessagesService.class.getName());

	@PathParam("id")
	private String roomId;

	@Inject
	private RoomService roomService;

	@PUT
	public void postMessage(@FormParam("message") String message, @FormParam("userId") final String userId) {
		logger.info("Posting to room " + roomId + " message = " + message);
		final NewRoomMessageNotification messageNotification = new NewRoomMessageNotification(roomId, message);
		messageNotification.setUserId(userId);

		this.roomService.postNotification(messageNotification);
	}


}
