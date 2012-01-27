package com.hybris.chatservice.service.impl;

import com.hybris.chatservice.businesslayer.UserService;
import com.hybris.chatservice.service.UserLoginService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

/**
 * User: PLMorin
 * Date: 26/01/12
 * Time: 9:26 PM
 */
@Path("/user/")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
@Produces({MediaType.APPLICATION_JSON})
@Provider
public class DefaultRestUserLoginServiceImpl implements UserLoginService {
	@Inject
	private UserService userService;
	
	@Override
	public String loginNormalUser(String nick) {
		return this.userService.loginAsUser(nick);
	}

	@Override
	public String loginModerator(String userName, String password) {
		return this.userService.loginAsModerator(userName, password);
	}
}
