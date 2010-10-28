package net.todd.scorekeeper;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ApplicationInitializerTest {
	@Mock
	private IdentityStore identityStore;
	
	private ApplicationInitializer testObject;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		testObject = new ApplicationInitializer(identityStore);
	}
	
	@Test
	public void createAnIdentityIfNoIdentityWasFound() {
		doReturn(null).when(identityStore).loadIdentity();
		
		testObject.initialize();
		
		verify(identityStore).createIdentity();
	}
	
	@Test
	public void dontCreateAnIdentityIfAnIdentityWasFound() {
		Identity identity = mock(Identity.class);
		doReturn(identity).when(identityStore).loadIdentity();
		
		testObject.initialize();
		
		verify(identityStore, never()).createIdentity();
	}
}
