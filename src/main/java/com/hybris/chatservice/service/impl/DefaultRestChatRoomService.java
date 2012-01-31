package com.hybris.chatservice.service.impl;

import com.hybris.chatservice.businesslayer.RoomService;
import com.hybris.chatservice.commonobjects.ChatRoom;
import com.hybris.chatservice.commonobjects.InvalidChatRoomException;
import com.hybris.chatservice.service.ChatRoomMembershipService;
import com.hybris.chatservice.service.ChatRoomMessagesService;
import com.hybris.chatservice.service.ChatRoomService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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

//	@Context
//	private SecurityContext securityContext;

	@Inject
	private RoomService roomService;

	@Inject
	private ChatRoomMembershipService defaultMembershipService;

	@Inject
	private ChatRoomMessagesService chatRoomMessagesService;

	@Override
	@GET
	public ChatRoom info(@PathParam("id") final String roomId) {
		return this.roomService.info(roomId);
	}

	@Override
	@POST
	public void createRoom(@PathParam("id") final String roomId) {
		try {
			this.roomService.createRoom(roomId);
		} catch (InvalidChatRoomException e) {
			throw new WebApplicationException(e, Response.Status.BAD_REQUEST);
		}
	}

	@Override
	@DELETE
	public void deleteRoom(@PathParam("id") final String roomId) {

	}

	@Override
	@Path("/membership/")
	public ChatRoomMembershipService membership(@PathParam("id") final String roomId) {
		// if this room is private, we should return a sub type of DefaultRestChatRoomMembershipService
		return this.defaultMembershipService;
	}

	@Override
	@Path("/messages/")
	public ChatRoomMessagesService messages(@PathParam("id") final String roomId) {
		return this.chatRoomMessagesService;
	}

}
