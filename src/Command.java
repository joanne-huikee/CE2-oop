import java.io.File;
import java.util.Iterator;
import java.util.Vector;

public class Command {

	private static final int EMPTY_FILE_COUNTER = 0;
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
	private static final String MESSAGE_SEARCH_NO_MATCH = "No match found for search";
	private static final String MESSAGE_SEARCH = "The above are all the lines found containing searched word";
	private static final String COMMAND_ADD = "add";
	private static final String COMMAND_DISPLAY = "display";
	private static final String COMMAND_DELETE = "delete";
	private static final String COMMAND_CLEAR = "clear";
	private static final String COMMAND_SORT = "sort";
	private static final String COMMAND_SEARCH = "search";
	private static final String COMMAND_EXIT = "exit";

	private String description;
	private FileEditor myEditor;

	public Command(String cmd) {
		description = cmd;
	}

	public void executeCommand() {
		String cmdKey = getActionWord();
		processCommand(cmdKey);
	}

	public String getActionWord() {
		String[] tokens = description.trim().split("\\s");
		StringBuilder sb = new StringBuilder();
		return sb.insert(0, tokens[0]).toString();
	}

	public void processCommand(String cmdKey) {
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
		myEditor = new FileEditor(TextBuddy.getUserFile());
		String feedbackMsg = checkValidAddition(myEditor, userInput);
		return feedbackMsg;
	}

	public String getInputMsg() {
		String message = description.replace(getActionWord(), " ");
		return message.trim();
	}

	public String checkValidAddition(FileEditor myEditor, String userInput) {
		if (userInput.isEmpty()) {
			return MESSAGE_EMPTY_ADD;
		} else {
			myEditor.write(userInput);
			return String.format(MESSAGE_ADD, TextBuddy.getFileName(), userInput);
		}
	}

	private boolean executeDisplay() {
		myEditor = new FileEditor(TextBuddy.getUserFile());
		myEditor.readAndPrint();
		return true;
	}

	public String executeDelete() {
		myEditor = new FileEditor(TextBuddy.getUserFile());
		int x = getLineNumForDel();
		String feedbackMsg = checkValidDel(myEditor, x);
		return feedbackMsg;
	}

	public int getLineNumForDel() {
		int num = 0;
		try {
			num = Integer.parseInt(getInputMsg());
			return num;
		} catch (NumberFormatException ex) {
			TextBuddy.printFeedback(MESSAGE_NUM_ERROR);
		}
		return -1;
	}

	public String checkValidDel(FileEditor myEditor, int num) {
		if (isEmpty(TextBuddy.getUserFile())) {
			return String.format(MESSAGE_EMPTY_DELETION, TextBuddy.getFileName());
		} else {
			Vector<String> storage = myEditor.readAndStore();
			return checkDelLineExist(myEditor, storage, num);
		}
	}

	public boolean isEmpty(File userFile) {
		return userFile.length() <= EMPTY_FILE_COUNTER;
	}

	public String checkDelLineExist(FileEditor myEditor, Vector<String> storage, int num) {
		if (isInvalidDel(storage, num)) {
			return MESSAGE_INVALID_DELETE;
		} else {
			String delLine = myEditor.deleteLine(storage, num);
			myEditor.appendVectorToFile(storage);
			return String.format(MESSAGE_DELETE, TextBuddy.getFileName(), delLine);
		}
	}

	public boolean isInvalidDel(Vector<String> storage, int num) {
		return (num > storage.size() || num <= 0);
	}

	public String executeClear() {
		myEditor = new FileEditor(TextBuddy.getUserFile());
		myEditor.clearFile();
		return String.format(MESSAGE_CLEAR, TextBuddy.getFileName());
	}

	public String executeSort() {
		myEditor = new FileEditor(TextBuddy.getUserFile());
		myEditor.sortAlpha();
		return String.format(MESSAGE_SORT, TextBuddy.getFileName());
	}

	public String executeSearch() {
		String searchInput = getInputMsg();
		myEditor = new FileEditor(TextBuddy.getUserFile());
		Vector<String> storage = myEditor.findAndStore(searchInput);
		printMatchingSearch(storage);
		return MESSAGE_SEARCH;
	}

	public void printMatchingSearch(Vector<String> storage) {
		Iterator<String> i = storage.iterator();
		if (storage.isEmpty()) {
			System.out.println(MESSAGE_SEARCH_NO_MATCH);
		} else {
			while (i.hasNext()) {
				System.out.println(i.next());
			}
		}
	}

	public void executeExit() {
		System.exit(ARGS_PARAM);
	}
}
