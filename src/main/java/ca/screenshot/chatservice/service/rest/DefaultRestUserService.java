package ca.screenshot.chatservice.service.rest;

import ca.screenshot.chatservice.businesslayer.InvalidUserException;
import ca.screenshot.chatservice.businesslayer.RoomService;
import ca.screenshot.chatservice.businesslayer.UserService;
import ca.screenshot.chatservice.commonobjects.InvalidChatRoomException;
import ca.screenshot.chatservice.commonobjects.User;
import org.apache.commons.beanutils.BeanUtils;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.InvocationTargetException;

/**
 * User: PLMorin
 * Date: 30/01/12
 * Time: 12:41 PM
 */
@Path("/user/{key}/")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
@Produces({MediaType.APPLICATION_JSON})
@Provider
public class DefaultRestUserService {

	@Inject
	private UserService userService;

	@Inject
	private RoomService roomService;

	@GET
	public User info(@PathParam("key") final String userKey) {
		final User retUser = new User();

		try {
			BeanUtils.copyProperties(retUser, this.userService.info(userKey));
		} catch (IllegalAccessException e) {
			throw new WebApplicationException(e, Response.Status.BAD_REQUEST);
		} catch (InvocationTargetException e) {
			throw new WebApplicationException(e, Response.Status.BAD_REQUEST);
		}

		retUser.setEmail("");
		return retUser;
	}


	@POST
	@Path("nickname")
	public void setNickname(@PathParam("key") final String userKey, @FormParam("nick") String newNickname) {
		try {
			this.userService.changeNick(userKey, newNickname);
		} catch (InvalidUserException e) {
			throw new WebApplicationException(e, Response.Status.BAD_REQUEST);
		}
	}

	@POST
	@Path("logout")
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
