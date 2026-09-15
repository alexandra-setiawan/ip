package edith;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import edith.task.SortOrder;
import edith.task.TaskList;
import edith.task.ToDo;

/** Tests loading and saving task data. */
class StorageTest {
    @TempDir
    private Path temporaryDirectory;

    @Test
    void saveAndLoad_sortedTasks_canRestorePersistedInsertionOrder() {
        Storage storage = new Storage(temporaryDirectory.resolve("tasks.txt").toString());
        TaskList tasks = new TaskList();
        tasks.add(new ToDo("write report"));
        tasks.add(new ToDo("buy groceries"));
        tasks.sort(SortOrder.ALPHABETICAL);
        storage.save(tasks);

        TaskList loadedTasks = storage.load();
        loadedTasks.sort(SortOrder.ADDED);

        assertEquals("write report", loadedTasks.get(0).getDescription());
        assertEquals("buy groceries", loadedTasks.get(1).getDescription());
    }
}
