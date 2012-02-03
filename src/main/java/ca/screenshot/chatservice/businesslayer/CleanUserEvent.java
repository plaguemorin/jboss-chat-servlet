package ca.screenshot.chatservice.businesslayer;

/**
 * User: PLMorin
 * Date: 03/02/12
 * Time: 1:57 PM
 */
public class CleanUserEvent {
	private String userId;

	public CleanUserEvent(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
