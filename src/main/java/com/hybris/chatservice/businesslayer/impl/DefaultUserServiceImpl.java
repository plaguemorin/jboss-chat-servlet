package com.hybris.chatservice.businesslayer.impl;


import com.hybris.chatservice.businesslayer.InvalidUserException;
import com.hybris.chatservice.businesslayer.UserPrivate;
import com.hybris.chatservice.businesslayer.UserService;
import com.hybris.chatservice.commonobjects.User;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.inject.Singleton;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
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
	private Map<String, UserPrivate> userMaps;

	@PostConstruct
	public void init() {
		this.userMaps = new ConcurrentHashMap<String, UserPrivate>();
	}

	private String makeKeyForUser(final String email) {
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
		final String userKey = this.makeKeyForUser(email);

		if (this.userMaps.containsKey(userKey)) {
			throw new InvalidUserException("User " + email + " is already logged in");
		}

		final UserPrivate userPrivate = new UserPrivate();
		userPrivate.setId(userKey);
		userPrivate.setEmail(email);
		userPrivate.setRegisterDate(Calendar.getInstance().getTimeInMillis());
		userPrivate.setLastActive(userPrivate.getRegisterDate());
		userPrivate.setAvatarUrl("http://www.gravatar.com/avatar/" + userKey);

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
	public void changeNick(String userKey, String newNickname) {
		if (this.isValidUser(userKey)) {
			this.userMaps.get(userKey).setNickname(newNickname);
		}
	}
}
