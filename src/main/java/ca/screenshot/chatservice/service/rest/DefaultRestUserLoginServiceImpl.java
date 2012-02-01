package ca.screenshot.chatservice.service.rest;

import ca.screenshot.chatservice.businesslayer.InvalidUserException;
import ca.screenshot.chatservice.businesslayer.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 * User: PLMorin
 * Date: 26/01/12
 * Time: 9:26 PM
 */
@Path("/registration/")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
@Produces({MediaType.APPLICATION_JSON})
@Provider
public class DefaultRestUserLoginServiceImpl {
	@Inject
	private UserService userService;

	@PUT
	public String loginNormalUser(@QueryParam("userEmail") String email) {
		try {
			return this.userService.loginAsUser(email);
		} catch (InvalidUserException e) {
			throw new WebApplicationException(e, Response.Status.BAD_REQUEST);
		}
	}


}
