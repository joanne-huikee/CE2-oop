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

/**
 * This class is used to manipulate the content of a file. Any action that
 * requires the access and edition of textfile will be made through FileEditor
 * object.
 */

public class FileEditor {

	private static final int COUNTER_START = 0;
	private static final int EMPTY_FILE_COUNTER = 0;
	private static final String MESSAGE_ERROR = "Error occurred while processing file.";
	private static final String MESSAGE_EMPTY_FILE = "%1$s is empty. Nothing to be displayed";

	/**
	 * Private attributes of FileEditor class. Every file editor object has a
	 * file, filewriter, buffered writer, filereader and buffered reader
	 */

	private File _userFile;
	private FileWriter _fw;
	private BufferedWriter _bw;
	private FileReader _fr;
	private BufferedReader _br;

	/******************************************************************************************************/

	public FileEditor(File file) { // constructor of FileEditor obj
		_userFile = file;
	}

	public void write(String input) { // This method appends a new line given
										// into existing file
		try {
			_fw = new FileWriter(_userFile, true);
			_bw = new BufferedWriter(_fw);
			_bw.append(input).append("\n").toString();
			_bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readAndPrint() {
		if (_userFile.length() <= EMPTY_FILE_COUNTER) {
			TextBuddy.printFeedback(String.format(MESSAGE_EMPTY_FILE, TextBuddy.getFileName()));
		} else {
			outputFileContent();
		}
	}

	public void outputFileContent() { // print content of textfile line by line
										// w linenum
		String line = null;
		int lineNum = COUNTER_START;
		try {
			_fr = new FileReader(_userFile);
			_br = new BufferedReader(_fr);
			loopAndPrintLine(line, lineNum);
			_br.close();
		} catch (FileNotFoundException ex) {
			TextBuddy.printFeedback(MESSAGE_ERROR);
		} catch (IOException ex) {
			TextBuddy.printFeedback(MESSAGE_ERROR);
		}
	}

	private void loopAndPrintLine(String line, int lineNum) throws IOException {
		while ((line = _br.readLine()) != null) {
			lineNum++;
			TextBuddy.printFeedback(lineNum + ". " + line);
		}
	}

	public Vector<String> readAndStore() { // store content in textfile line by
											// line into string vector
		Vector<String> temp = new Vector<String>();
		String line = null;
		try {
			_fr = new FileReader(_userFile);
			_br = new BufferedReader(_fr);
			while ((line = _br.readLine()) != null) {
				storeToTemp(temp, line);
			}
			_br.close();
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

	public String deleteLine(Vector<String> temp, int num) {   //remove line specified from string vector
		String deletedLine = temp.get(num - 1);
		temp.remove(num - 1);
		return deletedLine;
	}

	public void appendVectorToFile(Vector<String> temp) { // append contents of string vector back to an empty file
		Iterator<String> i = temp.iterator();
		try {
			_fw = new FileWriter(_userFile, false);
			_bw = new BufferedWriter(_fw);
			while (i.hasNext()) {
				_bw.append(i.next()).append("\n").toString();
			}
			_bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void clearFile() {
		try {
			_fw = new FileWriter(_userFile, false);
			_bw = new BufferedWriter(_fw);
			_bw.close();
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
			_fr = new FileReader(_userFile);
			_br = new BufferedReader(_fr);
			getLinesContainSearched(line, lineNum, temp, searchInput);
			_br.close();
		} catch (FileNotFoundException ex) {
			TextBuddy.printFeedback(MESSAGE_ERROR);
		} catch (IOException ex) {
			TextBuddy.printFeedback(MESSAGE_ERROR);
		}
		return temp;
	}

	public void getLinesContainSearched(String line, int lineNum, Vector<String> temp, String searchInput) throws IOException {
		while ((line = _br.readLine()) != null) {
			lineNum++;
			if (Pattern.compile(Pattern.quote(searchInput), Pattern.CASE_INSENSITIVE).matcher(line).find()) {
				storeToTemp(temp, lineNum + ". " + line); // find line containing search word regardless of letter casing
															// and store to vector
			}
		}
	}
}
