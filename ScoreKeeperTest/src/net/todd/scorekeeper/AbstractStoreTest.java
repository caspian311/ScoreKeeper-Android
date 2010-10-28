package net.todd.scorekeeper;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import android.content.Context;

public abstract class AbstractStoreTest {
	private File tempFile;
	private Context context;

	@BeforeClass
	public static void setUpLogger() {
		Logger.setTestMode(true);
	}

	@AfterClass
	public static void tearDownLogger() {
		Logger.setTestMode(false);
	}
	
	@Before
	public void setUpContextAndTempFile() throws Exception {
		tempFile = File.createTempFile(getClass().getName(), ".data");

		context = mock(Context.class);

		when(context.openFileInput(anyString())).thenAnswer(new Answer<FileInputStream>() {
			@Override
			public FileInputStream answer(InvocationOnMock invocation) throws Throwable {
				return new FileInputStream(tempFile);
			}
		});

		when(context.openFileOutput(anyString(), anyInt())).thenAnswer(
				new Answer<FileOutputStream>() {
					@Override
					public FileOutputStream answer(InvocationOnMock invocation) throws Throwable {
						return new FileOutputStream(tempFile);
					}
				});

	}

	@After
	public void tearDown() throws Exception {
		tempFile.delete();
	}
	
	Context getContext() {
		return context;
	}
}
