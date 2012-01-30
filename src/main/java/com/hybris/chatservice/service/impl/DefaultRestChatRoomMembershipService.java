package com.hybris.chatservice.service.impl;

import com.hybris.chatservice.businesslayer.RoomService;
import com.hybris.chatservice.businesslayer.UserService;
import com.hybris.chatservice.commonobjects.InvalidChatRoomException;
import com.hybris.chatservice.commonobjects.User;
import com.hybris.chatservice.commonobjects.UserEnterRoomNotification;
import com.hybris.chatservice.commonobjects.UserLeaveRoomNotification;
import com.hybris.chatservice.service.ChatRoomMembershipService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.LinkedList;
import java.util.List;

/**
 * User: PLMorin
 * Date: 26/01/12
 * Time: 9:51 PM
 */

public class DefaultRestChatRoomMembershipService implements ChatRoomMembershipService {
	@PathParam("id")
	private String roomId;

	@Inject
	private RoomService roomService;

	@Inject
	private UserService userService;

	@Override
	@GET
	public List<User> list() {
		final List<String> userIdList = this.roomService.listUsers(roomId);
		final List<User> userList = new LinkedList<User>();

		for (final String userId: userIdList) {
			userList.add(this.userService.info(userId));
		}

		return userList;
	}

	@Override
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
		this.roomService.postNotification(messageNotification);
	}

	@Override
	@DELETE
	public void unSubscribe(@QueryParam("userId") final String userId) {
		this.roomService.postNotification(new UserLeaveRoomNotification(roomId, userId));
	}
}
