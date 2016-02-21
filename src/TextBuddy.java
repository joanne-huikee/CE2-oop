import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class TextBuddy {

	private static final String MESSAGE_WELCOME = "Welcome to TextBuddy. %1$s is ready for use";
	private static final String MESSAGE_NO_FILE_ERROR = "Pls specify a file name for program to run and work on.";
	private static String _fileName;
	private static File _userFile;

	public static void main(String args[]) {
		checkForValidArg(args);
		initialiseProg(_userFile);
		printFeedback(String.format(MESSAGE_WELCOME, _fileName));
		executeProg();
	}

	public static void checkForValidArg(String[] args) {
		if (isNullArg(args)) {
			printFeedback(MESSAGE_NO_FILE_ERROR);
			System.exit(0);
		} else {
			setFileName(args);
		}
	}

	public static boolean isNullArg(String[] args) {
		if (args.length == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static void setFileName(String[] args) {
		_fileName = args[0];	
	}
	
	public static void initialiseProg(File userfile) {
		userfile = openFile();
	}
		
	private static File openFile() {
		File file = new File(_fileName);
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}

	public static void printFeedback(String message) {
		System.out.println(message);
	}
	
	public static void executeProg() {
		Scanner sc = new Scanner(System.in);
		String commandLine;
		do {
			System.out.print("command: ");
			commandLine = sc.nextLine();
			constructCmdObj();
			
		
	}
}

