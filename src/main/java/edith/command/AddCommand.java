package edith.command;

import edith.Storage;
import edith.Ui;
import edith.task.Task;
import edith.task.TaskList;

/** Adds a task and persists the updated list. */
public class AddCommand extends Command {
    private final Task task;
    /** Creates an add command for the given task. */
    public AddCommand(Task task) { this.task = task; }
    @Override public void execute(TaskList tasks, Ui ui, Storage storage) { tasks.add(task); storage.save(tasks); ui.showAddedTask(task, tasks.size()); }
}
