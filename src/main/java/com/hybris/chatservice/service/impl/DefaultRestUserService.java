package com.hybris.chatservice.service.impl;

import com.hybris.chatservice.commonobjects.User;
import com.hybris.chatservice.service.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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

	@PathParam("key")
	private String userKey;

	@Inject
	private com.hybris.chatservice.businesslayer.UserService userService;

	@Override
	@GET
	public User info() {
		// TODO: We shouldn't return the email here
		return this.userService.info(userKey);
	}

	@Override
	@POST
	@Path("nickname")
	public void setNickname(@QueryParam("nick") String newNickname) {
		if (this.userService.isValidUser(userKey)) {
			this.userService.changeNick(userKey, newNickname);
		}
	}
}
