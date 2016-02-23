import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

public class FileEditorTest {

	@Test
	public void testReadAndStore() { //check if stores string into vector correctly
		String[] args = {"mytestfile.txt"};
		TextBuddy.setFileName(args);
		TextBuddy.openFile();
		Command cmdObj = new Command("clear");
		cmdObj.executeClear();
		Command cmdObj2 = new Command("add abcgdj");
		cmdObj2.executeAdd();
		FileEditor myEditor = new FileEditor(TextBuddy.getUserFile());
		Vector<String> test = new Vector<String>();
		test.add("abcgdj");
		assertEquals(test, myEditor.readAndStore());
	}
	
	@Test
	public void testDeleteLine() {  //check if line to be deleted is correct
		Vector<String> test = new Vector<String>();
		test.add("abcgdj");
		test.add("fngjgkg");
		test.add("sjjmfvv");
		String line = "sjjmfvv";
		FileEditor myEditor = new FileEditor(TextBuddy.getUserFile());
		assertEquals(line, myEditor.deleteLine(test,3));
	}

}
