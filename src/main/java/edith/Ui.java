package edith;

import java.util.Scanner;

import edith.task.Task;
import edith.task.TaskList;

/** Handles Edith's console input and output. */
public class Ui {
    private static final String LINE =
            "_______________________________________________________________________________";
    private static final String BANNER = " _____    _ _ _   _     \n"
            + "| ____|__| (_) |_| |__  \n"
            + "|  _| / _` | | __| '_ \\ \n"
            + "| |__| (_| | | |_| | | |\n"
            + "|_____\\__,_|_|\\__|_| |_|\n";
    private static final String HELP_TEXT = "Available commands:\n"
            + "  todo <description>\n"
            + "  deadline <description> /by <date>\n"
            + "  event <description> /from <start> /to <end>\n"
            + "  list\n"
            + "  find <keyword>\n"
            + "  mark <number>\n"
            + "  unmark <number>\n"
            + "  delete <number>\n"
            + "  sort alph\n"
            + "  sort alph desc\n"
            + "  sort date\n"
            + "  sort date desc\n"
            + "  sort status\n"
            + "  sort added\n"
            + "  sort added desc\n"
            + "  help\n"
            + "  bye";
    private final Scanner scanner = new Scanner(System.in);

    /** Shows Edith's welcome message. */
    public void showWelcome() {
        System.out.println(LINE);
        System.out.println(BANNER);
        System.out.println("\tHello! I'm Edith.");
        System.out.println("\tWhat can I do for you?");
        System.out.println(LINE);
    }
    /** Reads the next command, or null at end of input. */
    public String readCommand() {
        return scanner.hasNextLine() ? scanner.nextLine() : null;
    }
    /** Shows the divider line. */
    public void showLine() {
        System.out.println(LINE);
    }
    /** Prints the task list. */
    public void showList(TaskList tasks) {
        System.out.println("\tHere are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("\t" + (i + 1) + "." + tasks.get(i));
        }
    }
    /** Shows the syntax of every available command. */
    public void showHelp() {
        System.out.println("\t" + HELP_TEXT.replace("\n", "\n\t"));
    }

    /** Returns the help text for other user interfaces. */
    protected String getHelpText() {
        return HELP_TEXT;
    }
    /**
     * Prints tasks with descriptions that contain the keyword.
     *
     * @param tasks tasks to search
     * @param keyword text to match against task descriptions
     */
    public void showMatchingTasks(TaskList tasks, String keyword) {
        System.out.println("\tHere are the matching tasks in your list:");
        for (int index : tasks.findMatchingIndices(keyword)) {
            System.out.println("\t" + (index + 1) + "." + tasks.get(index));
        }
    }
    /** Prints an error message. */
    public void showError(String message) {
        System.out.println("\t" + message);
    }
    /** Shows the add confirmation. */
    public void showAddedTask(Task task, int count) {
        System.out.println("\tGot it. I've added this task:\n\t  " + task
                + "\n\tNow you have " + count + " tasks in the list.");
    }
    /** Shows a completed-task confirmation. */
    public void showMarked(Task task) {
        System.out.println("\tNice! I've marked this task as done:\n\t  " + task);
    }
    /** Shows an uncompleted-task confirmation. */
    public void showUnmarked(Task task) {
        System.out.println("\tOK, I've marked this task as not done yet:\n\t  " + task);
    }
    /** Shows the delete confirmation. */
    public void showDeletedTask(Task task, int count) {
        System.out.println("\tNoted. I've removed this task:\n\t  " + task
                + "\n\tNow you have " + count + " tasks in the list.");
    }
    /** Shows the farewell. */
    public void showBye() {
        System.out.println("\tBye. Hope to see you again soon!");
    }
}
