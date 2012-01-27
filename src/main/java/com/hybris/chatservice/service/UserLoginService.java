package com.hybris.chatservice.service;

/**
 * User: PLMorin
 * Date: 26/01/12
 * Time: 11:50 AM
 */
public interface UserLoginService {
	String loginNormalUser(final String nick);

	String loginModerator(final String userName, final String password);
}
