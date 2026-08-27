/**
 * Represents a task with a start time and an end time.
 */
public class Event extends Task {
    /** The start time or date text supplied by the user. */
    protected String from;

    /** The end time or date text supplied by the user. */
    protected String to;

    /**
     * Creates an event task.
     *
     * @param description the event description
     * @param from the event start text
     * @param to the event end text
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    protected String getTypeCode() {
        return "E";
    }

    @Override
    public String toFileFormat() {
        return super.toFileFormat() + " | " + from + " | " + to;
    }

    /**
     * Returns this task with its type, status, start, and end time.
     *
     * @return the formatted event task
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
