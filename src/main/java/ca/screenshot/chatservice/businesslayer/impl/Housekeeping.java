package ca.screenshot.chatservice.businesslayer.impl;

import ca.screenshot.chatservice.businesslayer.CleanUserEvent;
import ca.screenshot.chatservice.businesslayer.UserService;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

/**
 * User: PLMorin
 * Date: 03/02/12
 * Time: 1:54 PM
 */
@Stateless
public class Housekeeping {

	@Inject
	private Event<CleanUserEvent> usersEvent;

	@Inject
	private UserService userService;

	@Schedule(minute = "*/5")
	private void timeout() {
		usersEvent.fire(new CleanUserEvent());
	}
}
