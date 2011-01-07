package net.todd.scorekeeper.data;

import java.util.List;

public class CollectionOfItems<T> {
	private List<T> items;

	public void setItems(List<T> items) {
		this.items = items;
	}

	public List<T> getItems() {
		return items;
	}
}
