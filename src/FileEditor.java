import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

public class FileEditor {

	private static final String MESSAGE_ERROR = "Error occurred while processing file.";
	private static final int COUNTER_START = 0;

	private File userFile;
	private FileWriter fw;
	private BufferedWriter bw;
	private FileReader fr;
	private BufferedReader br;

	public FileEditor(File file) {
		userFile = file;
	}

	public void write(String input) {
		try {
			fw = new FileWriter(userFile, true);
			bw = new BufferedWriter(fw);
			bw.append(input).append("\n").toString();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Vector<String> readAndStore() {
		Vector<String> temp = new Vector<String>();
		String line = null;
		try {
			fr = new FileReader(userFile);
			br = new BufferedReader(fr);
			while ((line = br.readLine()) != null) {
				storeToTemp(temp, line);
			}
			br.close();
		} catch (FileNotFoundException ex) {
			TextBuddy.printFeedback(MESSAGE_ERROR);
		} catch (IOException ex) {
			TextBuddy.printFeedback(MESSAGE_ERROR);
		}
		return temp;
	}

	public void storeToTemp(Vector<String> temp, String line) {
		temp.add(line);	
	}
	
	public void printStorage(Vector<String> temp) {
		Iterator<String> i = temp.iterator();
		int lineNum = COUNTER_START;
		while (i.hasNext()) {
			lineNum++;
			System.out.println(lineNum + ". " + i.next());
		}
	}
	
	public void deleteLine(Vector<String> temp, int num) {
		temp.remove(num-1);
	}
}
