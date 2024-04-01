package service;

import model.Epic;
import model.SubTask;
import model.Task;
import model.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("InMemoryTaskManager")
class InMemoryTaskManagerTest {

    static InMemoryTaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = new InMemoryTaskManager(new InMemoryHistoryManager());

        Task task = new Task("Test addNewTask", TaskStatus.NEW, "Test addNewTask description");
        taskManager.addNewTask(task);

        Epic epic = new Epic("Test addNewSubTask", TaskStatus.NEW, "Test addNewSubTask description");
        int epicId = taskManager.addNewEpic(epic);

        SubTask subTask = new SubTask("Test addNewSubTask", TaskStatus.NEW, "Test addNewSubTask description", epicId);
        taskManager.addNewSubTask(subTask);
    }

    @Test
    void addNewTask() {
        Task task = new Task("Test addNewTask", TaskStatus.NEW, "Test addNewTask description");
        final int taskId = taskManager.addNewTask(task);

        final Task savedTask = taskManager.getTask(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final HashMap<Integer, Task> tasks = taskManager.getTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(2, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(taskId), "Задачи не совпадают.");
    }

    @Test
    void addNewSubTask() {
        Epic epic = new Epic("Test addNewSubTask", TaskStatus.NEW, "Test addNewSubTask description");
        int epicId = taskManager.addNewEpic(epic);

        SubTask task = new SubTask("Test addNewSubTask", TaskStatus.NEW, "Test addNewSubTask description", epicId);
        final int taskId = taskManager.addNewSubTask(task);

        final Task savedTask = taskManager.getSubTask(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final HashMap<Integer, SubTask> tasks = taskManager.getSubTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(2, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(taskId), "Задачи не совпадают.");
    }

    @Test
    void addNewEpic() {
        Epic task = new Epic("Test addNewSubTask", TaskStatus.NEW, "Test addNewSubTask description");
        final int taskId = taskManager.addNewEpic(task);

        final Epic savedTask = taskManager.getEpic(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final HashMap<Integer, Epic> tasks = taskManager.getEpics();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(2, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(taskId), "Задачи не совпадают.");
    }

    private void assertManagerMaps(int taskExpected, int subtaskExpected, int epicExpected) {
        final HashMap<Integer, Task> tasks = taskManager.getTasks();
        final HashMap<Integer, SubTask> subTasks = taskManager.getSubTasks();
        final HashMap<Integer, Epic> epics = taskManager.getEpics();

        assertEquals(taskExpected, tasks.size(), "Неверное количество tasks.");
        assertEquals(subtaskExpected, subTasks.size(), "Неверное количество subTasks.");
        assertEquals(epicExpected, epics.size(), "Неверное количество epics.");
    }
    @Test
    void deleteAll() {
        taskManager.deleteAll();
        assertManagerMaps(0,0,0);
    }

    @Test
    void deleteAllTask() {

        taskManager.deleteAllTask();

        assertManagerMaps(0,1,1);
    }

    @Test
    void deleteAllEpic() {
        taskManager.deleteAllEpic();

        final HashMap<Integer, Task> tasks = taskManager.getTasks();
        assertManagerMaps(1,0,0);
    }

    @Test
    void deleteAllSubTask() {
        taskManager.deleteAllSubTask();

        final HashMap<Integer, Task> tasks = taskManager.getTasks();
        assertManagerMaps(1,0,1);
    }
}