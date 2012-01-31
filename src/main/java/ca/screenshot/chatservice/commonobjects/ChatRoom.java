package ca.screenshot.chatservice.commonobjects;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * User: PLMorin
 * Date: 26/01/12
 * Time: 2:22 PM
 */
@XmlRootElement
public class ChatRoom {
	private String name;
	private List<String> users;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getUsers() {
		return users;
	}

	public void setUsers(List<String> users) {
		this.users = users;
	}
}
