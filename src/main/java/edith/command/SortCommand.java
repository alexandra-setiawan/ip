package edith.command;

import edith.Storage;
import edith.Ui;
import edith.task.SortOrder;
import edith.task.TaskList;

/** Sorts and saves Edith's task list. */
public class SortCommand extends Command {
    private final SortOrder sortOrder;

    /**
     * Creates a command that arranges tasks in the given order.
     *
     * @param sortOrder order in which to arrange the tasks
     */
    public SortCommand(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        tasks.sort(sortOrder);
        storage.save(tasks);
        ui.showList(tasks);
    }
}
