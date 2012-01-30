package com.hybris.chatservice.service;

/**
 * User: PLMorin
 * Date: 30/01/12
 * Time: 1:42 PM
 */
public interface ChatRoomMessagesService {
	void postMessage(String message, final String userId);
}
