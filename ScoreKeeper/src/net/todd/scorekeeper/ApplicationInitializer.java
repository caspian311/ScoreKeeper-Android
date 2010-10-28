package net.todd.scorekeeper;

import android.content.Context;

public class ApplicationInitializer {
	private final IdentityStore identityStore;

	public ApplicationInitializer(Context context) {
		this(new IdentityStore(context));
	}

	public ApplicationInitializer(IdentityStore identityStore) {
		this.identityStore = identityStore;
	}

	public void initialize() {
		if (identityStore.loadIdentity() == null) {
			identityStore.createIdentity();
		}
	}
}
