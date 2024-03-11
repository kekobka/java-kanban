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
        epic.addSubTask(taskManager.createSubTask(new SubTask("подзадача1", TaskStatus.NEW, "sub1")));
        epic.addSubTask(taskManager.createSubTask(new SubTask("подзадача2", TaskStatus.IN_PROGRESS, "sub2")));
        taskManager.updateEpic(epic);
        System.out.println(epic);
        System.out.println("Create Epic with 2 subtask: " + epic);

        Epic epic2 = taskManager.createEpic(new Epic("Новый эпик 2", TaskStatus.NEW, "описание"));
        epic2.addSubTask(taskManager.createSubTask(new SubTask("Новый subtask", TaskStatus.NEW, "описание")));
        taskManager.updateEpic(epic2);
        System.out.println("Create Epic with 1 subtask: " +  epic2);
        taskManager.deleteTask(task.getId());
        taskManager.deleteEpic(epic.getId());

        System.out.println(taskManager);
    }
}
