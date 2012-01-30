package com.hybris.chatservice.service;

import com.hybris.chatservice.commonobjects.User;

/**
 * User: PLMorin
 * Date: 30/01/12
 * Time: 12:40 PM
 */
public interface UserService {

	/**
	 * @return
	 */
	User info();

	/**
	 * @param newNickname new nick name
	 */
	void setNickname(final String newNickname);
}
