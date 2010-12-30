package net.todd.scorekeeper.data;

import java.util.List;

import org.simpleframework.xml.ElementList;

public class CollectionOfItems<T> {
	@ElementList
	private List<T> items;

	public void setItems(List<T> items) {
		this.items = items;
	}

	public List<T> getItems() {
		return items;
	}
}
