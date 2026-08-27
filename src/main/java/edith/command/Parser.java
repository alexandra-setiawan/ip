package edith.command;

import edith.EdithException;
import edith.task.Deadline;
import edith.task.Event;
import edith.task.ToDo;

/** Converts raw user commands into executable command objects. */
public class Parser {
    /** Parses a command and its arguments.
     * @param command raw command entered by the user
     * @return executable command represented by the input
     * @throws EdithException if the input is not a recognized command
     */
    public static Command parse(String command) throws EdithException {
        if (command.equals("bye")) return new ExitCommand();
        if (command.equals("list")) return new ListCommand();
        if (command.startsWith("mark ")) return new MarkCommand(Integer.parseInt(command.substring(5)), true);
        if (command.startsWith("unmark ")) return new MarkCommand(Integer.parseInt(command.substring(7)), false);
        if (command.startsWith("delete ")) return new DeleteCommand(Integer.parseInt(command.substring(7)));
        if (command.equals("todo") || command.startsWith("todo ")) {
            String description = command.substring(4).trim();
            if (description.isEmpty()) throw new EdithException("OOPS!!! The description of a todo cannot be empty.");
            return new AddCommand(new ToDo(description));
        }
        if (command.startsWith("deadline ")) {
            String[] parts = command.substring(9).split(" /by ", 2);
            if (parts.length == 2) return new AddCommand(new Deadline(parts[0], parts[1]));
        }
        if (command.startsWith("event ")) {
            String[] parts = command.substring(6).split(" /from ", 2);
            if (parts.length == 2) { String[] times = parts[1].split(" /to ", 2); if (times.length == 2) return new AddCommand(new Event(parts[0], times[0], times[1])); }
        }
        throw new EdithException("OOPS!!! I'm sorry, but I don't know what that means :-(");
    }
}
