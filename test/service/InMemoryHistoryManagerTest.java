package service;

import model.Task;
import model.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("InMemoryHistoryManager")
class InMemoryHistoryManagerTest {
    private HistoryManager manager;
    private static int getIdCounter = 0;

    private int generateId() {
        return ++getIdCounter;
    }

    protected Task newTask() {
        return new Task("Test addNewTask", TaskStatus.NEW, "Test addNewTask description");
    }

    @BeforeEach
    public void beforeEach() {
        getIdCounter = 0;
        manager = new InMemoryHistoryManager();
    }

    @Test
    void add() {
        Task task = newTask();
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        historyManager.add(task);
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
    }

    @Test
    public void addTasksToHistoryTest() {
        Task task1 = newTask();
        int newTaskId1 = generateId();
        task1.setId(newTaskId1);
        Task task2 = newTask();
        int newTaskId2 = generateId();
        task2.setId(newTaskId2);
        Task task3 = newTask();
        int newTaskId3 = generateId();
        task3.setId(newTaskId3);
        manager.add(task1);
        manager.add(task2);
        manager.add(task3);
        assertEquals(List.of(task1, task2, task3), manager.getHistory());
    }

    @Test
    public void removeTaskTest() {
        Task task1 = newTask();
        int newTaskId1 = generateId();
        task1.setId(newTaskId1);
        Task task2 = newTask();
        int newTaskId2 = generateId();
        task2.setId(newTaskId2);
        Task task3 = newTask();
        int newTaskId3 = generateId();
        task3.setId(newTaskId3);
        manager.add(task1);
        manager.add(task2);
        manager.add(task3);
        manager.remove(task3.getId());
        assertEquals(List.of(task1, task2), manager.getHistory());
    }

    @Test
    public void removeOnlyOneTaskTest() {
        Task task = newTask();
        int newTaskId = generateId();
        task.setId(newTaskId);
        manager.add(task);
        manager.remove(task.getId());
        assertEquals(Collections.EMPTY_LIST, manager.getHistory());
    }

    @Test
    public void historyIsEmptyTest() {
        Task task1 = newTask();
        int newTaskId1 = generateId();
        task1.setId(newTaskId1);
        Task task2 = newTask();
        int newTaskId2 = generateId();
        task2.setId(newTaskId2);
        Task task3 = newTask();
        int newTaskId3 = generateId();
        task3.setId(newTaskId3);
        manager.remove(task1.getId());
        manager.remove(task2.getId());
        manager.remove(task3.getId());
        assertEquals(Collections.EMPTY_LIST, manager.getHistory());
    }

    @Test
    public void notRemoveTaskWithBadIdTest() {
        Task task = newTask();
        int newTaskId = generateId();
        task.setId(newTaskId);
        manager.add(task);
        manager.remove(-1);
        assertEquals(List.of(task), manager.getHistory());
    }
}