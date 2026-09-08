package edith.task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

/** Owns Edith's tasks and the operations on the task collection. */
public class TaskList implements Iterable<Task> {
    private final ArrayList<Task> tasks = new ArrayList<>();

    /** Adds a task to the list. */
    public void add(Task task) {
        assert task != null : "Task list must not contain null tasks";
        tasks.add(task);
    }

    /** Returns a task by zero-based index. */
    public Task get(int index) {
        return tasks.get(index);
    }

    /** Removes and returns a task by zero-based index. */
    public Task remove(int index) {
        return tasks.remove(index);
    }

    /**
     * Returns the zero-based indices of tasks whose descriptions contain the keyword.
     *
     * @param keyword text to search for
     * @return indices of matching tasks in their original order
     */
    public List<Integer> findMatchingIndices(String keyword) {
        return IntStream.range(0, tasks.size())
                .filter(index -> tasks.get(index).matchesDescription(keyword))
                .boxed()
                .toList();
    }

    /** Returns the number of tasks. */
    public int size() {
        return tasks.size();
    }

    @Override
    public Iterator<Task> iterator() {
        return tasks.iterator();
    }
}
