package com.hybris.chatservice.service.impl;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * User: PLMorin
 * Date: 26/01/12
 * Time: 3:04 PM
 */
@ApplicationPath("chatServices")
public class RestApplication extends Application {
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> s = new HashSet<Class<?>>();
		s.add(DefaultRestChatRoomService.class);
		s.add(DefaultRestUserLoginServiceImpl.class);
		s.add(DefaultRestUserService.class);
		return s;
	}
}
