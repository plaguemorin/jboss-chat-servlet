package com.hybris.chatservice.service.impl;

import com.hybris.chatservice.businesslayer.RoomService;
import com.hybris.chatservice.commonobjects.*;
import com.hybris.chatservice.service.ChatRoomMembershipService;
import com.hybris.chatservice.service.ChatRoomService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.util.logging.Logger;

/**
 * User: PLMorin
 * Date: 26/01/12
 * Time: 2:11 PM
 */
@Path("/room/{id}/")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
@Produces({MediaType.APPLICATION_JSON})
@Provider
public class DefaultRestChatRoomService implements ChatRoomService {
	private Logger logger = Logger.getLogger(DefaultRestChatRoomService.class.getName());

	@Context
	private SecurityContext securityContext;

	@PathParam("id")
	private String roomId;

	@Inject
	private RoomService roomService;

	@Inject
	private ChatRoomMembershipService defaultMembershipService;

	@Override
	@GET
	public ChatRoom info() {
		return this.roomService.info(roomId);
	}

	@Override
	@POST
	public void createRoom() {
		try {
			this.roomService.createRoom(roomId);
		} catch (InvalidChatRoomException e) {
			throw new WebApplicationException(e, Response.Status.BAD_REQUEST);
		}
	}

	@Override
	@DELETE
	public void deleteRoom() {

	}

	@Override
	@PUT
	public void postMessage(@FormParam("message") String message, @FormParam("userId") final String userId) {
		logger.info("Posting to room " + roomId + " message = " + message);
		final NewRoomMessageNotification messageNotification = new NewRoomMessageNotification(roomId, message);
		messageNotification.setUserId(userId);

		this.roomService.postNotification(messageNotification);
	}

	@Override
	@POST
	@Path("/kick/")
	public void kickUser(@QueryParam("kickedUserId") String userId) {
		final UserKickedNotification messageNotification = new UserKickedNotification();
		this.roomService.postNotification(messageNotification);

	}

	@Override
	@POST
	@Path("/ban/")
	public void banUser(@QueryParam("bannedUserId") String userId) {
		final UserBannedNotification messageNotification = new UserBannedNotification();
		this.roomService.postNotification(messageNotification);
	}

	@Override
	@Path("/membership/")
	public ChatRoomMembershipService membership() {
		// if this room is private, we should return a sub type of DefaultRestChatRoomMembershipService
		return this.defaultMembershipService;
	}

}
