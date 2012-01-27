package com.hybris.chatservice.businesslayer.impl;


import com.hybris.chatservice.businesslayer.UserService;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User: PLMorin
 * Date: 26/01/12
 * Time: 9:29 PM
 */
@Singleton
@Named
public class DefaultUserServiceImpl implements UserService {
	private Map<String, String> userMaps;

	@PostConstruct
	public void init() {
		this.userMaps = new ConcurrentHashMap<String, String>();
	}

	@Override
	public String loginAsUser(String nick) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public String loginAsModerator(String userName, String password) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public boolean isUserModerator(String userId) {
		return false;  //To change body of implemented methods use File | Settings | File Templates.
	}
}
