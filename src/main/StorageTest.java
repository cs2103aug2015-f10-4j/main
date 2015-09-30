package main;

import java.io.IOException;

import junit.framework.TestCase;

public class StorageTest extends TestCase {
	// tests whether the file specified will be created when the constructor is called
		public void testStorageConstructor() {
			try {
				Storage testStorage = new Storage("mytasks.txt");
				assertTrue(testStorage.fileExist());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
}
