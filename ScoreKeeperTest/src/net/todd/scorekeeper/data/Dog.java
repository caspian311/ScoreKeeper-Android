package net.todd.scorekeeper.data;

import org.simpleframework.xml.Element;

public class Dog {
	@Element
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
