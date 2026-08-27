package edith.command;

import edith.Storage;
import edith.Ui;
import edith.task.Task;
import edith.task.TaskList;

/** Deletes a task and persists the updated list. */
public class DeleteCommand extends Command {
    private final int index;
    /** Creates a delete command for a one-based task number. */
    public DeleteCommand(int taskNumber) { index = taskNumber - 1; }
    /** Removes the selected task, persists the list, and reports the result. */
    @Override public void execute(TaskList tasks, Ui ui, Storage storage) { Task removed = tasks.remove(index); storage.save(tasks); ui.showDeletedTask(removed, tasks.size()); }
}
