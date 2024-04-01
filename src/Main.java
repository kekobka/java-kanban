import model.Epic;
import model.SubTask;
import model.Task;
import model.TaskStatus;
import service.Managers;
import service.TaskManager;

import java.util.Map;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefaults();

        int taskId = taskManager.addNewTask(new Task("Новая задача", TaskStatus.NEW, "описание"));
        int taskId2 = taskManager.addNewTask(new Task("Новая задача2", TaskStatus.NEW, "описание 2"));

        Epic epic = new Epic("Новый эпик ", TaskStatus.NEW, "описание");
        int epicId = taskManager.addNewEpic(epic);

        SubTask subtask = new SubTask("подзадача1", TaskStatus.IN_PROGRESS, "sub1", epic.getId());
        epic.addSubTask(subtask);
        taskManager.addNewSubTask(subtask);

        taskManager.addNewSubTask(new SubTask("подзадача2", TaskStatus.NEW, "sub2", epicId));
        taskManager.updateEpic(epic);

        Epic epic2 = new Epic("Новый эпик 2", TaskStatus.NEW, "описание");
        int epicId2 = taskManager.addNewEpic(epic2);

        SubTask subtask2 = new SubTask("Новый subtask", TaskStatus.NEW, "описание", epicId);
        epic2.addSubTask(subtask2);
        int subtaskId2 = taskManager.addNewSubTask(subtask2);
        Task _task = taskManager.getTask(taskId); // task@1 in history
        Task _epic = taskManager.getEpic(epicId2); // epic in history
        Task _subtask = taskManager.getSubTask(subtaskId2); // epic in history
        taskManager.updateEpic(epic2);


        taskManager.deleteTask(taskId);
        taskManager.deleteAllEpic();

        printAllTasks(taskManager);
    }
    private static void printAllTasks(TaskManager manager) {
        System.out.println("Задачи:");
        for (Map.Entry<Integer, Task> task : manager.getTasks().entrySet()) {
            System.out.println("\t" + task);
        }
        System.out.println("\nЭпики:");
        for (Map.Entry<Integer, Epic> epic : manager.getEpics().entrySet()) {
            System.out.println("\t" + epic);

            for (Task task : epic.getValue().getSubTasks()) {
                System.out.println("--> " + task);
            }
        }
        System.out.println("\nПодзадачи:");
        for (Map.Entry<Integer, SubTask> subtask : manager.getSubTasks().entrySet()) {
            System.out.println("\t" + subtask);
        }

        System.out.println("\nИстория:");
        for (Task task : manager.getHistory()) {
            System.out.println("\t" + task);
        }
    }
}
