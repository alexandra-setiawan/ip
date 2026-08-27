package duke.task;

import java.util.ArrayList;
import java.util.Iterator;

/** Owns Edith's tasks and the operations on the task collection. */
public class TaskList implements Iterable<Task> {
    private final ArrayList<Task> tasks = new ArrayList<>();
    /** Adds a task to the list. */
    public void add(Task task) { tasks.add(task); }
    /** Returns a task by zero-based index. */
    public Task get(int index) { return tasks.get(index); }
    /** Removes and returns a task by zero-based index. */
    public Task remove(int index) { return tasks.remove(index); }
    /** Returns the number of tasks. */
    public int size() { return tasks.size(); }
    @Override public Iterator<Task> iterator() { return tasks.iterator(); }
}
