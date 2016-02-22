import java.util.Vector;

public class Command {

	private static final String MESSAGE_INVALID_COMMAND = "This is an invalid command. Pls re-enter command.";
	private static final String MESSAGE_NUM_ERROR = "An invalid number format was specified. Fail to process";
	private static final String MESSAGE_ADD = "added to %1$s: \"%2$s\"";
	private static final String COMMAND_ADD = "add";
	private static final String COMMAND_DISPLAY = "display";
	private static final String COMMAND_DELETE = "delete";

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
		default:
			TextBuddy.printFeedback(MESSAGE_INVALID_COMMAND);
		}
		return;
	}

	public String executeAdd() {
		String userInput = getInput();
		myEditor = new FileEditor(TextBuddy.getUserFile());
		myEditor.write(userInput);
		return String.format(MESSAGE_ADD, TextBuddy.getFileName(), userInput);
	}

	public String getInput() {
		String message = description.replace(getActionWord(), " ");
		return message.trim();
	}

	private boolean executeDisplay() {
		myEditor = new FileEditor(TextBuddy.getUserFile());
		myEditor.printStorage(myEditor.readAndStore());
		return true;
	}
	
	public String executeDelete() {
		myEditor = new FileEditor(TextBuddy.getUserFile());
		int x = getLineNumForDel();
		checkValidDeletion();
		myEditor.deleteLine(myEditor.readAndStore(), x);
		
	}

	public int getLineNumForDel() {
		int num = 0;
		try {
			num = Integer.parseInt(description);
			return num;
		} catch (NumberFormatException ex) {
			TextBuddy.printFeedback(MESSAGE_NUM_ERROR);
		}
		return -1;
	}
}
