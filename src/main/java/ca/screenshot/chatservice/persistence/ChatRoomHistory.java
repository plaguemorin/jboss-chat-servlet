package ca.screenshot.chatservice.persistence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * User: PLMorin
 * Date: 01/02/12
 * Time: 5:04 PM
 */
@Entity
public class ChatRoomHistory {
	@Id
	@GeneratedValue
	private long id;

	@OneToMany
	private List<ChatRoomHistoryMessage> roomHistoryMessages;

	public List<ChatRoomHistoryMessage> getRoomHistoryMessages() {
		return roomHistoryMessages;
	}

	public void setRoomHistoryMessages(List<ChatRoomHistoryMessage> roomHistoryMessages) {
		this.roomHistoryMessages = roomHistoryMessages;
	}
}
