package com.hybris.chatservice.service;

/**
 * User: PLMorin
 * Date: 26/01/12
 * Time: 11:50 AM
 */
public interface UserLoginService {
	/**
	 *
	 * @param email the email to join
	 * @return a unique user-id
	 */
	String loginNormalUser(final String email);
}
