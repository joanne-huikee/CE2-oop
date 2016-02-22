import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

public class CommandTest {

	@Test
	public void testGetActionWord() {
		Command cmdObj = new Command("add abcef");
		Command cmdObj2 = new Command("delete hello i sucks right");
		Command cmdObj3 = new Command("clear bobobo");
		Command cmdObj4 = new Command("display 123445");
		assertEquals("add", cmdObj.getActionWord());
		assertEquals("delete", cmdObj2.getActionWord());
		assertEquals("clear", cmdObj3.getActionWord());
		assertEquals("display", cmdObj4.getActionWord());

	}

	@Test
	public void testExecuteAdd() {
		String[] args = {"mytestfile.txt"};
		TextBuddy.setFileName(args);
		TextBuddy.openFile();
		Command cmdObj = new Command("add abcef");
		Command cmdObj2 = new Command("add hello i sucks right");
		Command cmdObj3 = new Command("add bobobo");
		Command cmdObj4 = new Command("add ");
		assertEquals("Added to mytestfile.txt: \"abcef\"", cmdObj.executeAdd());
		assertEquals("Added to mytestfile.txt: \"hello i sucks right\"", cmdObj2.executeAdd());
		assertEquals("Added to mytestfile.txt: \"bobobo\"", cmdObj3.executeAdd());
		assertEquals("Invalid command. Nothing specified for addition", cmdObj4.executeAdd());
	}

	@Test
	public void testGetInputMsg() {
		Command cmdObj = new Command("add lalala");
		Command cmdObj2 = new Command("add hello wru");
		Command cmdObj3 = new Command("search xoxo");
		Command cmdObj4 = new Command("search mehmeh");
		assertEquals("lalala", cmdObj.getInputMsg());
		assertEquals("hello wru", cmdObj2.getInputMsg());
		assertEquals("xoxo", cmdObj3.getInputMsg());
		assertEquals("mehmeh", cmdObj4.getInputMsg());
	}

	@Test
	public void testExecuteDelete() {
		String[] args = {"mytestfile.txt"};
		TextBuddy.setFileName(args);
		TextBuddy.openFile();
		Command cmdObj = new Command("delete 123");
		Command cmdObj2 = new Command("delete abc");
		Command cmdObj3 = new Command("delete ");
		assertEquals("Invalid command. Line number as specified does not exist for deletion", cmdObj.executeDelete());
		assertEquals("Invalid command. Line number as specified does not exist for deletion", cmdObj2.executeDelete());
		assertEquals("Invalid command. Line number as specified does not exist for deletion", cmdObj3.executeDelete());
	}

	@Test
	public void testGetLineNumForDel() {
		String[] args = {"mytestfile.txt"};
		TextBuddy.setFileName(args);
		TextBuddy.openFile();
		Command cmdObj = new Command("delete -12");
		Command cmdObj2 = new Command("delete 12345");
		Command cmdObj3 = new Command("delete i am a girl");
		Command cmdObj4 = new Command("delete ");
		assertEquals(-12, cmdObj.getLineNumForDel());
		assertEquals(12345, cmdObj2.getLineNumForDel());
		assertEquals(-1, cmdObj3.getLineNumForDel());
		assertEquals(-1, cmdObj4.getLineNumForDel());
	}


	@Test
	public void testIsEmpty() {
		File file = new File("TESTmytextfile.txt");
		Command cmdObj = new Command(" ");
		assertTrue(cmdObj.isEmpty(file));
	}


	@Test
	public void testExecuteClear() {
		String[] args = {"mytestfile.txt"};
		TextBuddy.setFileName(args);
		TextBuddy.openFile();
		Command cmdObj = new Command("clear");
		Command cmdObj2 = new Command("clear 123");
		Command cmdObj3 = new Command("clear ");
		assertEquals("All content deleted from mytestfile.txt", cmdObj.executeClear());
		assertEquals("All content deleted from mytestfile.txt", cmdObj2.executeClear());
		assertEquals("All content deleted from mytestfile.txt", cmdObj3.executeClear());
	}

	@Test
	public void testExecuteSort() {
		String[] args = {"mytestfile.txt"};
		TextBuddy.setFileName(args);
		TextBuddy.openFile();
		Command cmdObj = new Command("sort");
		cmdObj.executeClear();
		assertEquals("File is empty. No content to be sorted", cmdObj.executeSort());
		Command cmdObj2 = new Command("add abc");
		cmdObj2.executeAdd();
		assertEquals("mytestfile.txt has been sorted alphabetically", cmdObj2.executeSort());
	}

	@Test
	public void testExecuteSearch() {
		String[] args = {"mytestfile.txt"};
		TextBuddy.setFileName(args);
		TextBuddy.openFile();
		Command cmdObj = new Command("search abc");
		assertEquals("The above are all the lines found containing searched word", cmdObj.executeSearch());
	}
}
