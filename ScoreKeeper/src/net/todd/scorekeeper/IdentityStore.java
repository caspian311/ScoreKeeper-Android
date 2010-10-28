package net.todd.scorekeeper;

import java.util.Arrays;

import android.content.Context;

public class IdentityStore {
	private final Persistor<Identity> persistor;

	public IdentityStore(Context context) {
		persistor = Persistor.create(Identity.class, context);
	}

	public Identity loadIdentity() {
		return persistor.load().size() == 0 ? null : persistor.load().get(0);
	}

	public void createIdentity() {
		persistor.persist(Arrays.asList(new Identity()));
	}
}
