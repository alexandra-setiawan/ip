/**
 * Represents a task that must be completed by a specified time or date.
 */
public class Deadline extends Task {
    /** The deadline text supplied by the user. */
    protected String by;

    /**
     * Creates a deadline task.
     *
     * @param description the task description
     * @param by the deadline text
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    protected String getTypeCode() {
        return "D";
    }

    @Override
    public String toFileFormat() {
        return super.toFileFormat() + " | " + by;
    }

    /**
     * Returns this task with its type, status, and deadline.
     *
     * @return the formatted deadline task
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
