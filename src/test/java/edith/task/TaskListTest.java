package edith.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

class TaskListTest {
    @Test
    void findMatchingIndices_multipleMatches_returnsOriginalIndices() {
        TaskList tasks = new TaskList();
        tasks.add(new ToDo("read book"));
        tasks.add(new ToDo("exercise"));
        tasks.add(new ToDo("return BOOK"));

        assertEquals(List.of(0, 2), tasks.findMatchingIndices("book"));
    }

    @Test
    void findMatchingIndices_noMatches_returnsEmptyList() {
        TaskList tasks = new TaskList();
        tasks.add(new ToDo("read book"));

        assertEquals(List.of(), tasks.findMatchingIndices("exercise"));
    }
}
