package ca.screenshot.chatservice.businesslayer;

import ca.screenshot.chatservice.commonobjects.Notification;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * User: PLMorin
 * Date: 26/01/12
 * Time: 5:19 PM
 */
public class ChatRoomPerUserPrivate {
	private BlockingQueue<Notification> notifications;

	public ChatRoomPerUserPrivate() {
		this.notifications = new LinkedBlockingQueue<Notification>();
	}

	public void addNotification(final Notification notification) {
		try {
			this.notifications.put(notification);
		} catch (InterruptedException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}

	public Notification poll() {
		try {
			return this.notifications.take();
		} catch (InterruptedException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}

		return null;
	}
}
