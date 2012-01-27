package com.hybris.chatservice.service;

import com.hybris.chatservice.commonobjects.User;

import java.util.List;

/**
 * Sub resource
 *
 * User: PLMorin
 * Date: 26/01/12
 * Time: 9:51 PM
 */
public interface ChatRoomMembershipService {
	List<User> list();

	void subscribe(final String userId);

	void unSubscribe(final String userId);
}
