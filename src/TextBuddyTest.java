import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

public class TextBuddyTest {

	@Test
	public void testGetFileName() {
		String[] args = {"hello.txt"};
		TextBuddy.setFileName(args);
		assertEquals("hello.txt", TextBuddy.getFileName());
	}
	
	@Test
	public void testGetUserFile() {
		File file = new File("mytestfile.txt");
		String[] args = {"mytestfile.txt"};
		TextBuddy.setFileName(args);
		TextBuddy.openFile();
		assertEquals(file, TextBuddy.getUserFile());
	}


	@Test
	public void testIsNullArg() {
		String[] args = {"hello.txt"};
		assertFalse(TextBuddy.isNullArg(args));
		String[] args2 = {};
		assertTrue(TextBuddy.isNullArg(args2));
		String[] args3 = {"lala.txt"};
		assertFalse(TextBuddy.isNullArg(args3));
	}

}
