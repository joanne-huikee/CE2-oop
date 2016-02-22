import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;
import java.util.regex.Pattern;


public class FileEditor {

	private static final int COUNTER_START = 0;
	private static final String MESSAGE_ERROR = "Error occurred while processing file.";
	private static final String MESSAGE_EMPTY_FILE = "%1$s is empty. Nothing to be displayed";

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

	public void readAndPrint() {
		if (userFile.length() <= 0) {
			TextBuddy.printFeedback(String.format(MESSAGE_EMPTY_FILE, TextBuddy.getFileName()));
		} else {
			outputFileContent();
		}
	}

	public void outputFileContent() {
		String line = null;
		int lineNum = COUNTER_START;
		try {
			fr = new FileReader(userFile);
			br = new BufferedReader(fr);
			loopAndPrintLine(line, lineNum);
			br.close();
		} catch (FileNotFoundException ex) {
			TextBuddy.printFeedback(MESSAGE_ERROR);
		} catch (IOException ex) {
			TextBuddy.printFeedback(MESSAGE_ERROR);
		}
	}

	private void loopAndPrintLine(String line, int lineNum) throws IOException {
		while ((line = br.readLine()) != null) {
			lineNum++;
			System.out.println(lineNum + ". " + line);
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

	public String deleteLine(Vector<String> temp, int num) {
		String deletedLine = temp.get(num - 1);
		temp.remove(num - 1);
		return deletedLine;
	}

	public void appendVectorToFile(Vector<String> temp) {
		Iterator<String> i = temp.iterator();
		try {
			fw = new FileWriter(userFile, false);
			bw = new BufferedWriter(fw);
			while (i.hasNext()) {
				bw.append(i.next()).append("\n").toString();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void clearFile() {
		try {
			fw = new FileWriter(userFile, false);
			bw = new BufferedWriter(fw);
			bw.close();
		} catch (IOException e) {
			TextBuddy.printFeedback(MESSAGE_ERROR);
			e.printStackTrace();
		}
	}

	public void sortAlpha() {
		Vector<String> storage = readAndStore();
		Collections.sort(storage, String.CASE_INSENSITIVE_ORDER);
		appendVectorToFile(storage);
	}

	public Vector<String> findAndStore(String searchInput) {
		Vector<String> temp = new Vector<String>();
		String line = null;
		int lineNum = COUNTER_START;
		try {
			fr = new FileReader(userFile);
			br = new BufferedReader(fr);
			getLinesContainSearched(line, lineNum, temp, searchInput);
			br.close();
		} catch (FileNotFoundException ex) {
			TextBuddy.printFeedback(MESSAGE_ERROR);
		} catch (IOException ex) {
			TextBuddy.printFeedback(MESSAGE_ERROR);
		}
		return temp;
	}
	
	public void getLinesContainSearched(String line, int lineNum, Vector<String> temp, String searchInput) throws IOException {
		while ((line = br.readLine()) != null) {
			lineNum++;
			if (Pattern.compile(Pattern.quote(searchInput), Pattern.CASE_INSENSITIVE).matcher(line).find()) {
				storeToTemp(temp, lineNum + ". " + line);
			}
		}
	}
}
