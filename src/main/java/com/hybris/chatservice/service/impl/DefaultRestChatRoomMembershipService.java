package com.hybris.chatservice.service.impl;

import com.hybris.chatservice.businesslayer.RoomService;
import com.hybris.chatservice.commonobjects.User;
import com.hybris.chatservice.commonobjects.UserEnterRoomNotification;
import com.hybris.chatservice.commonobjects.UserLeaveRoomNotification;
import com.hybris.chatservice.service.ChatRoomMembershipService;

import javax.inject.Inject;
import javax.ws.rs.*;
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

	@Override
	@GET
	public List<User> list() {
		return this.roomService.listUsers(roomId);
	}

	@Override
	@POST
	public void subscribe(@QueryParam("userId") final String userId) {
		this.roomService.registerUserToRoom(roomId, userId);

		final UserEnterRoomNotification messageNotification = new UserEnterRoomNotification(roomId, userId);
		this.roomService.postNotification(messageNotification);
	}

	@Override
	@DELETE
	public void unSubscribe(@QueryParam("userId") final String userId) {
		this.roomService.postNotification(new UserLeaveRoomNotification(roomId, userId));
	}
}
