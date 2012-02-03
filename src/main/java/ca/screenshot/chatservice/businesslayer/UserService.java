package ca.screenshot.chatservice.businesslayer;

import ca.screenshot.chatservice.commonobjects.User;

import java.util.Set;

/**
 * User: PLMorin
 * Date: 26/01/12
 * Time: 9:28 PM
 */
public interface UserService {
	String loginAsUser(final String nick) throws InvalidUserException;

	User info(final String userId);

	boolean isValidUser(final String userId);

	void changeNick(String userKey, String newNickname) throws InvalidUserException;

	void removeUser(String userKey) throws InvalidUserException;

	Set<String> getIdleUsers();
}
