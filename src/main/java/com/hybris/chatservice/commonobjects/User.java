package com.hybris.chatservice.commonobjects;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * User: PLMorin
 * Date: 26/01/12
 * Time: 11:41 AM
 */
@XmlRootElement
public class User {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
