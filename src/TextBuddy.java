import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class TextBuddy {

	private static final String MESSAGE_WELCOME = "Welcome to TextBuddy. %1$s is ready for use";
	private static final String MESSAGE_NO_FILE_ERROR = "Pls specify a file name for program to run and work on.";
	private static final String MESSAGE_COMMAND = "command: ";
	private static String _fileName;
	private static File _userFile;

	public static void main(String args[]) {
		checkForValidArg(args);
		initialiseProg(args, _userFile);
		printFeedback(String.format(MESSAGE_WELCOME, _fileName));
		executeProg();
	}
	
	public static String getFileName() {
		return _fileName;
	}
	
	public static File getUserFile(){
		return _userFile;
	}
	
	public static void checkForValidArg(String[] args) {
		if (isNullArg(args)) {
			printFeedback(MESSAGE_NO_FILE_ERROR);
			System.exit(0);
		}
	}

	public static boolean isNullArg(String[] args) {
		if (args.length == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static void initialiseProg(String[] args, File userfile) {
		setFileName(args);
		openFile();
	}

	public static void setFileName(String[] args) {
		_fileName = args[0];
	}
	
	private static void openFile() {
		_userFile = new File(_fileName);
		try {
			if (!_userFile.exists()) {
				_userFile.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void printFeedback(String message) {
		System.out.println(message);
	}

	public static void executeProg() {
		Scanner sc = new Scanner(System.in);
		String commandLine;
		do {
			printFeedbackNoNextLn(MESSAGE_COMMAND);
			commandLine = sc.nextLine();
			Command cmd = new Command(commandLine);
			cmd.executeCommand();
		} while (!commandLine.equals("exit"));
		sc.close();
	}

	private static void printFeedbackNoNextLn(String message) {
		System.out.print(message);
	}
}
