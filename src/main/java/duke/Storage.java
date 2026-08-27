package duke;

import duke.task.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/** Loads and saves Edith's task list. */
public class Storage {
    private final String filePath;
    /** Creates storage backed by the given file path. */
    public Storage(String filePath) { this.filePath = filePath; }

    /** Loads valid tasks from disk. */
    public TaskList load() {
        TaskList tasks = new TaskList();
        File file = new File(filePath);
        if (!file.exists()) return tasks;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String savedTask;
            while ((savedTask = reader.readLine()) != null) {
                String[] parts = savedTask.split("\\s*\\|\\s*", -1);
                if (parts.length < 3) continue;
                Task task;
                if (parts[0].equals("T")) task = new ToDo(parts[2]);
                else if (parts[0].equals("D") && parts.length == 4) task = new Deadline(parts[2], parts[3]);
                else if (parts[0].equals("E") && parts.length == 5) task = new Event(parts[2], parts[3], parts[4]);
                else continue;
                if (parts[1].equals("1")) task.markAsDone();
                tasks.add(task);
            }
        } catch (IOException ignored) { }
        return tasks;
    }

    /** Saves every task, replacing the previous file contents. */
    public void save(TaskList tasks) {
        File file = new File(filePath);
        File parent = file.getParentFile();
        if (parent != null) parent.mkdirs();
        try (FileWriter writer = new FileWriter(file)) {
            for (Task task : tasks) { writer.write(task.toFileFormat()); writer.write(System.lineSeparator()); }
        } catch (IOException ignored) { }
    }
}
