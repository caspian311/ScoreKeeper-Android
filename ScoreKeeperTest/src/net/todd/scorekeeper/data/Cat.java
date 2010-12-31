package net.todd.scorekeeper.data;

import java.io.Serializable;

public class Cat implements Serializable {
	private static final long serialVersionUID = 6943534089094250754L;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
