/** Changes a task's completion status. */
public class MarkCommand extends Command {
    private final int index;
    private final boolean done;
    /** Creates a command for a one-based task number. */
    public MarkCommand(int taskNumber, boolean done) { index = taskNumber - 1; this.done = done; }
    @Override public void execute(TaskList tasks, Ui ui, Storage storage) { Task task = tasks.get(index); if (done) { task.markAsDone(); ui.showMarked(task); } else { task.unmarkAsDone(); ui.showUnmarked(task); } storage.save(tasks); }
}
