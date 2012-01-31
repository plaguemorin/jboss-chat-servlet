package com.hybris.chatservice.service.impl;

import com.hybris.chatservice.businesslayer.InvalidUserException;
import com.hybris.chatservice.businesslayer.RoomService;
import com.hybris.chatservice.commonobjects.InvalidChatRoomException;
import com.hybris.chatservice.commonobjects.User;
import com.hybris.chatservice.service.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 * User: PLMorin
 * Date: 30/01/12
 * Time: 12:41 PM
 */
@Path("/user/{key}/")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
@Produces({MediaType.APPLICATION_JSON})
@Provider
public class DefaultRestUserService implements UserService {

	@Inject
	private com.hybris.chatservice.businesslayer.UserService userService;

	@Inject
	private RoomService roomService;

	@Override
	@GET
	public User info(@PathParam("key") final String userKey) {
		// TODO: We shouldn't return the email here
		return this.userService.info(userKey);
	}


	@Override
	@POST
	@Path("nickname")
	public void setNickname(@PathParam("key") final String userKey, @FormParam("nick") String newNickname) {
		try {
			this.userService.changeNick(userKey, newNickname);
		} catch (InvalidUserException e) {
			throw new WebApplicationException(e, Response.Status.BAD_REQUEST);
		}
	}

	@Override
	@DELETE
	public void logout(@PathParam("key") final String userKey) {
		if (!this.userService.isValidUser(userKey)) {
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}

		try {
			// Logout the user from all chats
			for (final String room : this.roomService.listRooms()) {
				if (this.roomService.listUsers(room).contains(userKey)) {
					this.roomService.removeUserFromRoom(room, userKey);
				}
			}

			this.userService.removeUser(userKey);
		} catch (InvalidUserException e) {
			throw new WebApplicationException(e, Response.Status.BAD_REQUEST);
		} catch (InvalidChatRoomException e) {
			throw new WebApplicationException(e, Response.Status.BAD_REQUEST);
		}
	}
}
