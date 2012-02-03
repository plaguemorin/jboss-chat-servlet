package ca.screenshot.chatservice.persistence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * User: PLMorin
 * Date: 01/02/12
 * Time: 5:05 PM
 */
@Entity
public class ChatRoomHistoryMessage {
	@Id
	@GeneratedValue
	private long id;

	@OneToMany
	private ChatRoomHistory roomHistory;

	private String message;
	private String user;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
}
