import java.util.Scanner;

/**
 * Edith is a chatbot that stores tasks and can mark them as done.
 */

public class Edith {
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
        String line = "____________________________________________________";
        Task[] tasks = new Task[100];
        int taskCount = 0;

        System.out.println(line);
        System.out.println(banner);
        System.out.println("\tHello! I'm Edith.");
        System.out.println("\tWhat can I do for you?");
        System.out.println(line);

        try (Scanner scanner = new Scanner(System.in)) {            
            while (scanner.hasNextLine()) {
                String command = scanner.nextLine();
                
                System.out.println(line);

                if (command.equals("bye")) {
                    System.out.println("\tBye. Hope to see you again soon!");
                    System.out.println(line);

                    break;

                } else if (command.equals("list")) {
                    System.out.println("\tHere are the tasks in your list:");

                    for (int i = 0; i < taskCount; i++) {
                        System.out.println("\t" + (i + 1) + "." + tasks[i]);
                    }

                    System.out.println(line);

                } else if (command.startsWith("mark ")) {
                    int taskNumber = Integer.parseInt(command.substring(5));
                    int taskIndex = taskNumber - 1;

                    tasks[taskIndex].markAsDone();

                    System.out.println("\tNice! I've marked this task as done:");
                    System.out.println("\t  " + tasks[taskIndex]);
                    System.out.println(line);

                } else if (command.startsWith("unmark ")) {
                    int taskNumber = Integer.parseInt(command.substring(7));
                    int taskIndex = taskNumber - 1;

                    tasks[taskIndex].unmarkAsDone();

                    System.out.println("\tOK, I've marked this task as not done yet:");
                    System.out.println("\t  " + tasks[taskIndex]);
                    System.out.println(line);
                    
                } else if (taskCount < tasks.length) {
                    tasks[taskCount] = new Task(command);
                    taskCount++;

                    System.out.println("\tadded: " + command);
                    System.out.println(line);
                }
            }
        }
    }
}
