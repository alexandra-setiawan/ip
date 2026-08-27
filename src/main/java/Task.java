/**
 * Represents a task that Edith can track and mark as complete.
 */
public class Task {
    /** The description supplied by the user. */
    protected String description;

    /** The current completion status of this task. */
    protected TaskStatus status;

    /**
     * Creates a task that is initially not done.
     *
     * @param description the task description
     */
    public Task(String description) {
        this.description = description;
        this.status = TaskStatus.NOT_DONE;
    }

    /**
     * Returns the icon that represents this task's completion status.
     *
     * @return {@code X} for a completed task, otherwise a space
     */
    public String getStatusIcon() {
        return status.getIcon();
    }

    /** Marks this task as complete. */
    public void markAsDone() {
        status = TaskStatus.DONE;
    }

    /** Marks this task as not yet complete. */
    public void unmarkAsDone() {
        status = TaskStatus.NOT_DONE;
    }

    /**
     * Returns a simple representation suitable for saving to disk.
     *
     * @return task type, completion status, and description
     */
    public String toFileFormat() {
        return getTypeCode() + " | " + (status == TaskStatus.DONE ? "1" : "0")
                + " | " + description;
    }

    /**
     * Returns the type code used in the save file.
     *
     * @return the task type code
     */
    protected String getTypeCode() {
        return "T";
    }

    /**
     * Returns this task in the format used when displaying a task list.
     *
     * @return the status icon and task description
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
