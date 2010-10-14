package net.todd.scorekeeper;

import java.util.ArrayList;
import java.util.List;

public class ListenerManager {
	private final List<Listener> listeners = new ArrayList<Listener>();
	
	public void addListener(Listener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public void removeListener(Listener listener) {
		listeners.remove(listener);
	}
	
	public void notifyListeners() {
		for (Listener listener : listeners) {
			listener.handle();
		}
	}
}
