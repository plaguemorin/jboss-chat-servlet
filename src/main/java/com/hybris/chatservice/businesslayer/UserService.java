package com.hybris.chatservice.businesslayer;

import com.hybris.chatservice.commonobjects.User;

/**
 * User: PLMorin
 * Date: 26/01/12
 * Time: 9:28 PM
 */
public interface UserService {
	String loginAsUser(final String nick) throws InvalidUserException;

	User info(final String userId);

	boolean isValidUser(final String userId);

	void changeNick(String userKey, String newNickname);
}
