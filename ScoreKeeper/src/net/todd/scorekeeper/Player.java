package net.todd.scorekeeper;

import java.io.Serializable;

public class Player implements Serializable {
	private static final long serialVersionUID = -3804201764603746312L;
	
	private final String id;
	private final String name;

	private boolean selected;

	public Player(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean istSelected() {
		return selected;
	}
	
	@Override
	public String toString() {
		return id + ":" + name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (selected ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (selected != other.selected)
			return false;
		return true;
	}
	
	
}
