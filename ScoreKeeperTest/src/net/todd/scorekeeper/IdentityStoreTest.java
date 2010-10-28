package net.todd.scorekeeper;

import static org.junit.Assert.*;

import org.junit.Test;

public class IdentityStoreTest extends AbstractStoreTest {
	@Test
	public void thereIsNoIdentityInitially() {
		assertNull(getIdentityStore().loadIdentity());
	}
	
	@Test
	public void onceCreatedTheIdentifierIsNotEmpty() {
		getIdentityStore().createIdentity();
		Identity identity = getIdentityStore().loadIdentity();
		
		assertTrue(identity.getIdentitifer().length() > 0);
	}

	@Test
	public void onceCreatedTheAllSubsequentCallsToLoadTheIdentyGiveBackSameIdentity() {
		getIdentityStore().createIdentity();

		String identifier = getIdentityStore().loadIdentity().getIdentitifer();
		
		assertEquals(identifier, getIdentityStore().loadIdentity().getIdentitifer());
		assertEquals(identifier, getIdentityStore().loadIdentity().getIdentitifer());
		assertEquals(identifier, getIdentityStore().loadIdentity().getIdentitifer());
		assertEquals(identifier, getIdentityStore().loadIdentity().getIdentitifer());
	}
	
	private IdentityStore getIdentityStore() {
		return new IdentityStore(getContext());
	}
}
