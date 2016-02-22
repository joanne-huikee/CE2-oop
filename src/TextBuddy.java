import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * 
 * @author Joanne
 * 
 *         CS2103 CE2 This is th main class used for editing a given
 *         parameter(textfile) based on user command. The commands available for
 *         TextBuddy are add, delete, display, clear, search and sort. 
 *         add <string>: appends a new string(all the words following the add command)to the text file 
 *         display: displays all lines, with their respective line number (following chronological order, 
 *         the order in which they are keyed in), present in the file. 
 *         delete <int>: delete the line in the file which corresponds to the line number specified by user 
 *         sort: sort textfile in alphabetical order 
 *         search <string>: search for all lines in textfile that contain searched word.
 *         clear: empty the file
 * 
 *         Assumptions: 
 *         1) All other commands are regarded as invalid command and will not be perform when specified by user. 
 *         2) An invalid re-enter command prompt will be given when an invalid command was entered. 
 *         3) When an unavailable line number was entered for deletion, an error prompt will be given.
 *         4) Program deals with all leading and trailing whitespaces aka program still recognises add regardless of
 *         additional whitespaces in front 
 *         5) For clear and display command, as long as the command keyword is entered correctly (the first word is
 *         clear/display), the command will be performed regardless of what data were behind it. 
 *         eg. clear bbchfkag will still result in the file being cleared.
 *         6) For search, typing search only w/o further argument will result in search all aka showing entire textfile 
 *         7) For add and delete, typing the command w/o any further argument will result in an invalid prompt 
 *         8)File is saved to disk after each user operation to prevent loss of data from unintended termination since no
 *         "are u sure?" prompt will be given to double confirm the user operation that was meant to be carried out.
 */

public class TextBuddy {

	private static final int ARGS_PARAM = 0;
	private static final String MESSAGE_WELCOME = "Welcome to TextBuddy. %1$s is ready for use";
	private static final String MESSAGE_NO_FILE_ERROR = "Pls specify a file name for program to run and work on.";
	private static final String MESSAGE_COMMAND = "command: ";

	/**
	 * private attributes of main TextBuddy class. This main class is like an UI
	 * which initiates the program, deals with user input and show feedback to
	 * user
	 ************************/

	private static String _fileName;
	private static File _userFile;
	private static Command cmd;

	/************************************************************************/

	public static void main(String args[]) {
		checkForValidArg(args);
		initialiseProg(args, _userFile);
		printFeedback(String.format(MESSAGE_WELCOME, _fileName));
		executeProg();
	}

	public static String getFileName() {
		return _fileName;
	}

	public static File getUserFile() {
		return _userFile;
	}

	public static void checkForValidArg(String[] args) {
		if (isNullArg(args)) {
			printFeedback(MESSAGE_NO_FILE_ERROR);
			System.exit(ARGS_PARAM);
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
		_fileName = args[ARGS_PARAM];
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
		String commandLine = null;
		loopPromptTilExit(sc, commandLine);
		sc.close();
	}

	public static void loopPromptTilExit(Scanner sc, String commandLine) {
		do {
			printFeedbackNoNextLn(MESSAGE_COMMAND);
			commandLine = sc.nextLine();
			cmd = new Command(commandLine); // construct Command class object to
											// process command
			cmd.executeCommand();
		} while (!commandLine.equals("exit"));
	}

	public static void printFeedbackNoNextLn(String message) {
		System.out.print(message);
	}
}
