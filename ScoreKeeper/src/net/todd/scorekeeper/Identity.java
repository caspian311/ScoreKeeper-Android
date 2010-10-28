package net.todd.scorekeeper;

import java.io.Serializable;
import java.util.UUID;

public class Identity implements Serializable {
	private static final long serialVersionUID = 8227938350309711590L;
	
	private final String identifier = UUID.randomUUID().toString();

	public String getIdentitifer() {
		return identifier;
	}
}
