import java.util.Scanner;

/**
 * Edith is a chatbot that echoes user commands until the user says goodbye.
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

        System.out.println(line);
        System.out.println(banner);
        System.out.println("\tHello! I'm Edith.");
        System.out.println("\tWhat can I do for you?");
        System.out.println(line);

        try (Scanner scanner = new Scanner(System.in)) {            
            while (true) {
                String command = scanner.nextLine();
                
                System.out.println(line);

                if (command.equals("bye")) {
                    System.out.println("\tBye. Hope to see you again soon!");
                    System.out.println(line);
                    break;
                }

                System.out.println("\t" + command + " " + command + " " + command);
                System.out.println(line);
            }
        }
    }
}