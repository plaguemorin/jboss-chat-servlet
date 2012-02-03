package ca.screenshot.chatservice.businesslayer.impl;


import ca.screenshot.chatservice.businesslayer.CleanUserEvent;
import ca.screenshot.chatservice.businesslayer.InvalidUserException;
import ca.screenshot.chatservice.businesslayer.UserPrivate;
import ca.screenshot.chatservice.businesslayer.UserService;
import ca.screenshot.chatservice.commonobjects.User;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Named;
import javax.inject.Singleton;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

/**
 * User: PLMorin
 * Date: 26/01/12
 * Time: 9:29 PM
 */
@Singleton
@Named
public class DefaultUserServiceImpl implements UserService {
	private Logger logger = Logger.getLogger(DefaultUserServiceImpl.class.getName());

	private Map<String, UserPrivate> userMaps;

	@PostConstruct
	public void init() {
		this.userMaps = new ConcurrentHashMap<String, UserPrivate>();
	}

	private String makeMd5OfEmail(final String email) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(email.trim().toLowerCase().getBytes());
			byte byteData[] = md.digest();

			//convert the byte to hex format method 1
			StringBuilder sb = new StringBuilder();
			for (byte aByteData : byteData) {
				sb.append(Integer.toString((aByteData & 0xff) + 0x100, 16).substring(1));
			}

			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public String loginAsUser(String email) throws InvalidUserException {
		for (final User user : this.userMaps.values()) {
			if (user.getEmail().equalsIgnoreCase(email)) {
				throw new InvalidUserException("User " + email + " is already logged in");
			}
		}

		final String userKey = UUID.randomUUID().toString();

		final UserPrivate userPrivate = new UserPrivate();
		userPrivate.setId(userKey);
		userPrivate.setEmail(email);
		userPrivate.setRegisterDate(Calendar.getInstance().getTimeInMillis());
		userPrivate.setLastActive(userPrivate.getRegisterDate());
		userPrivate.setAvatarUrl("http://www.gravatar.com/avatar/" + this.makeMd5OfEmail(email));

		this.userMaps.put(userKey, userPrivate);

		return userKey;
	}

	@Override
	public User info(String userId) {
		return this.userMaps.get(userId);
	}

	@Override
	public boolean isValidUser(String userId) {
		return this.userMaps.containsKey(userId);
	}

	@Override
	public void changeNick(String userKey, String newNickname) throws InvalidUserException {
		if (this.isValidUser(userKey)) {
			logger.info("User " + userKey + " has changed nick to " + newNickname);
			this.userMaps.get(userKey).setNickname(newNickname);
		} else {
			throw new InvalidUserException("User " + userKey + " does not exist");
		}
	}

	@Override
	public void removeUser(String userKey) throws InvalidUserException {
		if (this.isValidUser(userKey)) {
			logger.info("User " + userKey + " wants out !");

			this.userMaps.remove(userKey);
		} else {
			throw new InvalidUserException("User " + userKey + " does not exist");
		}
	}

	private void housecleaning(@Observes final CleanUserEvent cleanUserEvent) {
		logger.info("Performing clean of timed-out users");
	}
}
