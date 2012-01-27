package com.hybris.chatservice.service.impl;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: PLMorin
 * Date: 26/01/12
 * Time: 3:04 PM
 * To change this template use File | Settings | File Templates.
 */
@ApplicationPath("chatServices")
public class RestApplication extends Application {
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> s = new HashSet<Class<?>>();
		s.add(DefaultRestChatRoomService.class);
		s.add(DefaultRestUserLoginServiceImpl.class);
		return s;
	}
}
