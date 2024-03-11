import model.Epic;
import model.SubTask;
import model.Task;
import model.TaskStatus;
import service.TaskManager;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Task task = taskManager.createTask(new Task("Новая задача", TaskStatus.NEW, "описание"));
        Task task2 = taskManager.createTask(new Task("Новая задача2", TaskStatus.NEW, "описание 2"));

        Epic epic = taskManager.createEpic(new Epic("Новый эпик ", TaskStatus.NEW, "описание"));

        taskManager.createSubTask(new SubTask("подзадача1", TaskStatus.IN_PROGRESS, "sub1", epic.getId()));
        epic.addSubTask(taskManager.createSubTask(new SubTask("подзадача2", TaskStatus.NEW, "sub2")));
        taskManager.updateEpic(epic);
        System.out.println(epic);
        System.out.println("Create Epic with 2 subtask: " + epic);

        Epic epic2 = taskManager.createEpic(new Epic("Новый эпик 2", TaskStatus.NEW, "описание"));
        epic2.addSubTask(taskManager.createSubTask(new SubTask("Новый subtask", TaskStatus.NEW, "описание")));
        taskManager.updateEpic(epic2);
        System.out.println("Create Epic with 1 subtask: " +  epic2);

        taskManager.deleteTask(task.getId());
        taskManager.deleteAllEpic();

        System.out.println(taskManager);
    }
}
