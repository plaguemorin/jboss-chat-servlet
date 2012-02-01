package ca.screenshot.chatservice.service.rest;

import ca.screenshot.chatservice.businesslayer.RoomService;
import ca.screenshot.chatservice.businesslayer.UserService;
import ca.screenshot.chatservice.commonobjects.*;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedList;
import java.util.List;

/**
 * User: PLMorin
 * Date: 26/01/12
 * Time: 9:51 PM
 */
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
@Produces({MediaType.APPLICATION_JSON})
public class DefaultRestChatRoomMembershipService {
	@PathParam("id")
	private String roomId;

	@Inject
	private RoomService roomService;

	@Inject
	private UserService userService;

	@GET
	public List<User> list() {
		final List<String> userIdList = this.roomService.listUsers(roomId);
		final List<User> userList = new LinkedList<User>();

		for (final String userId : userIdList) {
			userList.add(this.userService.info(userId));
		}

		return userList;
	}

	@POST
	public void subscribe(@QueryParam("userId") final String userId) {
		if (!this.userService.isValidUser(userId)) {
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}

		try {
			this.roomService.registerUserToRoom(roomId, userId);
		} catch (InvalidChatRoomException e) {
			throw new WebApplicationException(e, Response.Status.BAD_REQUEST);
		}

		final UserEnterRoomNotification messageNotification = new UserEnterRoomNotification(roomId, userId);
		messageNotification.setMessage("User " + this.userService.info(userId).getNickname() + " has entered the room");
		this.roomService.postNotification(messageNotification);
	}

	@DELETE
	public void unSubscribe(@QueryParam("userId") final String userId) {
		if (!this.userService.isValidUser(userId)) {
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}

		this.roomService.postNotification(new UserLeaveRoomNotification(roomId, userId));
	}


	@POST
	@Path("/kick/")
	public void kickUser(@QueryParam("kickedUserId") String userId) {
		final UserKickedNotification messageNotification = new UserKickedNotification();
		this.roomService.postNotification(messageNotification);

	}

	@POST
	@Path("/ban/")
	public void banUser(@QueryParam("bannedUserId") String userId) {
		final UserBannedNotification messageNotification = new UserBannedNotification();
		this.roomService.postNotification(messageNotification);
	}
}
