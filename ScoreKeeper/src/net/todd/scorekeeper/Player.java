package net.todd.scorekeeper;

import java.io.Serializable;

public class Player implements Serializable {
	private static final long serialVersionUID = -3804201764603746312L;
	
	private final int id;
	private final String name;

	public Player(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
}
