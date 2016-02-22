import java.io.File;
import java.util.Vector;

public class Command {

	private static final int EMPTY_FILE_COUNTER = 0;
	private static final int ARGS_PARAM = 0;
	private static final String MESSAGE_INVALID_COMMAND = "This is an invalid command. Pls re-enter command.";
	private static final String MESSAGE_NUM_ERROR = "An invalid number format was specified. Fail to process";
	private static final String MESSAGE_EMPTY_DELETION = "Invalid command. %1$s is empty. Nothing to be deleted.";
	private static final String MESSAGE_INVALID_DELETE = "Invalid command. Line number as specified does not exist for deletion";
	private static final String MESSAGE_ADD = "added to %1$s: \"%2$s\"";
	private static final String MESSAGE_DELETE = "deleted from %1$s: \"%2$s\"";
	private static final String MESSAGE_CLEAR = "all content deleted from %1$s";
	private static final String COMMAND_ADD = "add";
	private static final String COMMAND_DISPLAY = "display";
	private static final String COMMAND_DELETE = "delete";
	private static final String COMMAND_CLEAR = "clear";
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
		case COMMAND_EXIT:
			executeExit();
		default:
			TextBuddy.printFeedback(MESSAGE_INVALID_COMMAND);
		}
		return;
	}

	public String executeAdd() {
		String userInput = getInputMsg();
		myEditor = new FileEditor(TextBuddy.getUserFile()); // check for valid
															// add aka dont add
															// nth
		myEditor.write(userInput);
		return String.format(MESSAGE_ADD, TextBuddy.getFileName(), userInput);
	}

	public String getInputMsg() {
		String message = description.replace(getActionWord(), " ");
		return message.trim();
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
	
	public void executeExit() {
		System.exit(ARGS_PARAM);
	}
}
