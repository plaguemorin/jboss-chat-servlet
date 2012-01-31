package ca.screenshot.chatservice.service;

import ca.screenshot.chatservice.commonobjects.User;

/**
 * User: PLMorin
 * Date: 30/01/12
 * Time: 12:40 PM
 */
public interface UserService {

	/**
	 * @param userKey
	 * @return
	 */
	User info(final String userKey);

	/**
	 * @param userKey the user
	 * @param newNickname new nick name
	 */
	void setNickname(final String userKey, final String newNickname);


	void logout(final String userKey);
}
