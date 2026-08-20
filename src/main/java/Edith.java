import java.util.ArrayList;
import java.util.Scanner;

/**
 * Edith is a chatbot that stores tasks and can mark them as done.
 */

public class Edith {
    /**
     * Adds a task and shows the confirmation used for all task types.
     *
     * @param tasks the task list
     * @param task the task to add
     * @param line the output separator line
     */
    private static void addTask(ArrayList<Task> tasks, Task task, String line) {
        tasks.add(task);
        System.out.println("\tGot it. I've added this task:");
        System.out.println("\t  " + task);
        System.out.println("\tNow you have " + tasks.size() + " tasks in the list.");
        System.out.println(line);
    }

    /**
     * Starts the chatbot.
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        String banner = " _____    _ _ _   _     \n"
                      + "| ____|__| (_) |_| |__  \n"
                      + "|  _| / _` | | __| '_ \\ \n"
                      + "| |__| (_| | | |_| | | |\n"
                      + "|_____\\__,_|_|\\__|_| |_|\n";
        String line = "_______________________________________________________________________________";
        ArrayList<Task> tasks = new ArrayList<>();

        System.out.println(line);
        System.out.println(banner);
        System.out.println("\tHello! I'm Edith.");
        System.out.println("\tWhat can I do for you?");
        System.out.println(line);

        try (Scanner scanner = new Scanner(System.in)) {            
            while (scanner.hasNextLine()) {
                String command = scanner.nextLine();
                
                System.out.println(line);

                try {
                    if (command.equals("bye")) {
                        System.out.println("\tBye. Hope to see you again soon!");
                        System.out.println(line);

                        break;

                    } else if (command.equals("list")) {
                        System.out.println("\tHere are the tasks in your list:");

                        for (int i = 0; i < tasks.size(); i++) {
                            System.out.println("\t" + (i + 1) + "." + tasks.get(i));
                        }

                        System.out.println(line);

                    } else if (command.startsWith("mark ")) {
                        int taskNumber = Integer.parseInt(command.substring(5));
                        int taskIndex = taskNumber - 1;

                        tasks.get(taskIndex).markAsDone();

                        System.out.println("\tNice! I've marked this task as done:");
                        System.out.println("\t  " + tasks.get(taskIndex));
                        System.out.println(line);

                    } else if (command.startsWith("unmark ")) {
                        int taskNumber = Integer.parseInt(command.substring(7));
                        int taskIndex = taskNumber - 1;

                        tasks.get(taskIndex).unmarkAsDone();

                        System.out.println("\tOK, I've marked this task as not done yet:");
                        System.out.println("\t  " + tasks.get(taskIndex));
                        System.out.println(line);

                    } else if (command.startsWith("delete ")) {
                        int taskNumber = Integer.parseInt(command.substring(7));
                        Task removedTask = tasks.remove(taskNumber - 1);
                        
                        System.out.println("\tNoted. I've removed this task:");
                        System.out.println("\t  " + removedTask);
                        System.out.println("\tNow you have " + tasks.size() + " tasks in the list.");
                        System.out.println(line);
                
                    } else if (command.equals("todo") || command.startsWith("todo ")) {
                        String description = command.substring(4).trim();
                        
                        if (description.isEmpty()) {
                            throw new EdithException("OOPS!!! The description of a todo cannot be empty.");
                        }
                        
                        addTask(tasks, new ToDo(description), line);
                
                    } else if (command.startsWith("deadline ")) {
                        String[] deadlineParts = command.substring(9).split(" /by ", 2);
                        
                        addTask(tasks, new Deadline(deadlineParts[0], deadlineParts[1]), line);
                    
                    } else if (command.startsWith("event ")) {
                        String[] eventParts = command.substring(6).split(" /from ", 2);
                        String[] timeParts = eventParts[1].split(" /to ", 2);
                        
                        addTask(tasks, new Event(eventParts[0], timeParts[0], timeParts[1]), line);
                    
                    } else {
                        throw new EdithException("OOPS!!! I'm sorry, but I don't know what that means :-(");
                    }
                } catch (EdithException error) {
                    System.out.println("\t" + error.getMessage());
                    System.out.println(line);
                }
            }
        }
    }
}
