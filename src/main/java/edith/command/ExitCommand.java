package edith.command;

import edith.Storage;
import edith.Ui;
import edith.task.TaskList;

/** Ends the Edith session. */
public class ExitCommand extends Command {
    /** Displays the farewell message for the ending session. */
    @Override public void execute(TaskList tasks, Ui ui, Storage storage) { ui.showBye(); }
    /** Returns {@code true} because this command ends the session. */
    @Override public boolean isExit() { return true; }
}
