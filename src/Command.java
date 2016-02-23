import java.io.File;
import java.util.Iterator;
import java.util.Vector;

/**
 * This class is used to process and execute user's command. Most of the methods
 * return string or boolean for the ease of unit testing lolol
 */

public class Command {

	private static final int EMPTY_FILE_COUNTER = 0;
	private static final int COUNTER_START = 0;
	private static final int COUNTER_INVALID = -1;
	private static final int ARGS_PARAM = 0;
	private static final String MESSAGE_INVALID_COMMAND = "This is an invalid command. Pls re-enter command.";
	private static final String MESSAGE_EMPTY_ADD = "Invalid command. Nothing specified for addition";
	private static final String MESSAGE_NUM_ERROR = "An invalid number format was specified. Fail to process";
	private static final String MESSAGE_EMPTY_DELETION = "Invalid command. %1$s is empty. Nothing to be deleted.";
	private static final String MESSAGE_INVALID_DELETE = "Invalid command. Line number as specified does not exist for deletion";
	private static final String MESSAGE_ADD = "Added to %1$s: \"%2$s\"";
	private static final String MESSAGE_DELETE = "Deleted from %1$s: \"%2$s\"";
	private static final String MESSAGE_CLEAR = "All content deleted from %1$s";
	private static final String MESSAGE_SORT = "%1$s has been sorted alphabetically";
	private static final String MESSAGE_EMPTY_SORT = "File is empty. No content to be sorted";
	private static final String MESSAGE_SEARCH_NO_MATCH = "No match found for search";
	private static final String MESSAGE_SEARCH = "The above are all the lines found containing searched word";
	private static final String COMMAND_ADD = "add";
	private static final String COMMAND_DISPLAY = "display";
	private static final String COMMAND_DELETE = "delete";
	private static final String COMMAND_CLEAR = "clear";
	private static final String COMMAND_SORT = "sort";
	private static final String COMMAND_SEARCH = "search";
	private static final String COMMAND_EXIT = "exit";

	/**
	 * Private attributes of Command class. Every Command object has a task
	 * description and FileEditor to manipulate the textfile according to the
	 * command specified
	 */

	private String _description;
	private FileEditor _myEditor;

	/*************************************************************************************/

	public Command(String cmd) {
		_description = cmd;  // constructor of Command class
	}

	public void executeCommand() {
		String cmdKey = getActionWord();
		processCommand(cmdKey);
	}

	public String getActionWord() { // this method extracts the command word from user input ignoring trailing & leading whitespaces
		String[] tokens = _description.trim().split("\\s");
		StringBuilder sb = new StringBuilder();
		return sb.insert(0, tokens[0]).toString();
	}

	public void processCommand(String cmdKey) { // This method matches the userinput w the predefinedsystem fn and execute it w respective feedback returned
		switch (cmdKey) {
		case COMMAND_ADD:
			String feedbackOfAdd = executeAdd();
			TextBuddy.printFeedback(feedbackOfAdd);
			break;
		case COMMAND_DISPLAY:
			executeDisplay();
			break;
		case COMMAND_DELETE:
			String feedbackOfDel = executeDelete();
			TextBuddy.printFeedback(feedbackOfDel);
			break;
		case COMMAND_CLEAR:
			String feedbackOfClr = executeClear();
			TextBuddy.printFeedback(feedbackOfClr);
			break;
		case COMMAND_SORT:
			String feedbackofSort = executeSort();
			TextBuddy.printFeedback(feedbackofSort);
			break;
		case COMMAND_SEARCH:
			String feedbackofSearch = executeSearch();
			TextBuddy.printFeedback(feedbackofSearch);
			break;
		case COMMAND_EXIT:
			executeExit();
		default:
			TextBuddy.printFeedback(MESSAGE_INVALID_COMMAND);
		}
		return;
	}

	public String executeAdd() {
		String userInput = getInputMsg();
		_myEditor = new FileEditor(TextBuddy.getUserFile());
		String feedbackMsg = checkValidAddition(userInput);
		return feedbackMsg;
	}

	public String getInputMsg() {
		String message = _description.replace(getActionWord(), " ");
		return message.trim();
	}

	public String checkValidAddition(String userInput) {
		if (userInput.isEmpty()) {
			return MESSAGE_EMPTY_ADD;
		} else {
			_myEditor.write(userInput);
			return String.format(MESSAGE_ADD, TextBuddy.getFileName(), userInput);
		}
	}

	private boolean executeDisplay() {
		_myEditor = new FileEditor(TextBuddy.getUserFile());
		_myEditor.readAndPrint();
		return true;
	}

	public String executeDelete() {
		_myEditor = new FileEditor(TextBuddy.getUserFile());
		int x = getLineNumForDel();
		String feedbackMsg = checkValidDel(x);
		return feedbackMsg;
	}

	public int getLineNumForDel() {  //extract linenum(int) from a string
		int num = COUNTER_START;
		try {
			num = Integer.parseInt(getInputMsg());
			return num;
		} catch (NumberFormatException ex) {
			TextBuddy.printFeedback(MESSAGE_NUM_ERROR);
		}
		return COUNTER_INVALID;   //return invalid counter to indicate num format exception
	}

	public String checkValidDel(int num) {
		if (isEmpty(TextBuddy.getUserFile())) {
			return String.format(MESSAGE_EMPTY_DELETION, TextBuddy.getFileName());
		} else {
			Vector<String> storage = _myEditor.readAndStore();
			return checkDelLineExist(storage, num);
		}
	}

	public boolean isEmpty(File userFile) {
		return userFile.length() <= EMPTY_FILE_COUNTER;
	}

	public String checkDelLineExist(Vector<String> storage, int num) {
		if (isInvalidDel(storage, num)) {
			return MESSAGE_INVALID_DELETE;
		} else {
			String delLine = _myEditor.deleteLine(storage, num);
			_myEditor.appendVectorToFile(storage);
			return String.format(MESSAGE_DELETE, TextBuddy.getFileName(), delLine);
		}
	}

	public boolean isInvalidDel(Vector<String> storage, int num) {
		return (num > storage.size() || num <= 0);
	}

	public String executeClear() {
		_myEditor = new FileEditor(TextBuddy.getUserFile());
		_myEditor.clearFile();
		return String.format(MESSAGE_CLEAR, TextBuddy.getFileName());
	}

	public String executeSort() {
		_myEditor = new FileEditor(TextBuddy.getUserFile());
		if (isEmpty(TextBuddy.getUserFile())) {
			return MESSAGE_EMPTY_SORT;
		} else {
			_myEditor.sortAlpha();
			return String.format(MESSAGE_SORT, TextBuddy.getFileName());
		}
	}

	public String executeSearch() {
		String searchInput = getInputMsg();
		_myEditor = new FileEditor(TextBuddy.getUserFile());
		Vector<String> storage = _myEditor.findAndStore(searchInput);
		printMatchingSearch(storage);
		return MESSAGE_SEARCH;
	}

	public void printMatchingSearch(Vector<String> storage) {
		Iterator<String> i = storage.iterator();
		if (storage.isEmpty()) {
			TextBuddy.printFeedback(MESSAGE_SEARCH_NO_MATCH);
		} else {
			while (i.hasNext()) {
				TextBuddy.printFeedback(i.next());
			}
		}
	}

	public void executeExit() {
		System.exit(ARGS_PARAM);
	}
}
