package service;

import model.Task;
import model.TaskStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("InMemoryHistoryManager")
class InMemoryHistoryManagerTest {

    @Test
    void add() {
        Task task = new Task("Test addNewTask", TaskStatus.NEW, "Test addNewTask description");
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        historyManager.add(task);
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
    }

}