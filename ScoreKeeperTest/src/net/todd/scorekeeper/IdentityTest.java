package net.todd.scorekeeper;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class IdentityTest {
	@Test
	public void identitiesAreNeverNullOrEmpty() {
		for (int i = 0; i < 20; i++) {
			String identitifer = new Identity().getIdentitifer();
			assertNotNull(identitifer);
			assertTrue(identitifer.length() > 0);
		}
	}
	
	@Test
	public void identitiesAreUnique() {
		List<String> identifiers = new ArrayList<String>();
		for (int i = 0; i < 20; i++) {
			String identifier = new Identity().getIdentitifer();
			assertFalse(identifiers.contains(identifier));
			identifiers.add(identifier);
		}
	}
}
