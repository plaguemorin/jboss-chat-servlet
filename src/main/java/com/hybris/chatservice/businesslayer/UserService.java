package com.hybris.chatservice.businesslayer;

/**
 * User: PLMorin
 * Date: 26/01/12
 * Time: 9:28 PM
 */
public interface UserService {
	String loginAsUser(final String nick);

	String loginAsModerator(final String userName, final String password);

	boolean isUserModerator(final String userId);
}
