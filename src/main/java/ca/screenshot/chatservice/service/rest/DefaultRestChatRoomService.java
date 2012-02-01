package ca.screenshot.chatservice.service.rest;

import ca.screenshot.chatservice.businesslayer.RoomService;
import ca.screenshot.chatservice.commonobjects.ChatRoom;
import ca.screenshot.chatservice.commonobjects.InvalidChatRoomException;

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
public class DefaultRestChatRoomService {
	private Logger logger = Logger.getLogger(DefaultRestChatRoomService.class.getName());

	@Context
	private SecurityContext securityContext;

	@Inject
	private RoomService roomService;

	@Inject
	private DefaultRestChatRoomMembershipService defaultMembershipService;

	@Inject
	private DefaultRestChatRoomMessagesService chatRoomMessagesService;

	@GET
	public ChatRoom info(@PathParam("id") final String roomId) {
		return this.roomService.info(roomId);
	}

	@POST
	public void createRoom(@PathParam("id") final String roomId) {
		try {
			this.roomService.createRoom(roomId);
		} catch (InvalidChatRoomException e) {
			throw new WebApplicationException(e, Response.Status.BAD_REQUEST);
		}
	}

	@DELETE
	public void deleteRoom(@PathParam("id") final String roomId) {

	}

	@Path("/membership/")
	public DefaultRestChatRoomMembershipService membership(@PathParam("id") final String roomId) {
		// if this room is private, we should return a sub type of DefaultRestChatRoomMembershipService
		return this.defaultMembershipService;
	}

	@Path("/messages/")
	public DefaultRestChatRoomMessagesService messages(@PathParam("id") final String roomId) {
		return this.chatRoomMessagesService;
	}

}
