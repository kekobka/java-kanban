import model.Epic;
import model.SubTask;
import model.Task;
import model.TaskStatus;
import service.TaskManager;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Task task = taskManager.create(new Task("Новая задача", TaskStatus.NEW, "описание"));
        Task task2 = taskManager.create(new Task("Новая задача2", TaskStatus.NEW, "описание 2"));

        Epic epic = (Epic) taskManager.create(new Epic("Новый эпик ", TaskStatus.NEW, "описание"));
        epic.addSubTask((SubTask) taskManager.create(new SubTask("подзадача1", TaskStatus.NEW, "sub1")));
        epic.addSubTask((SubTask) taskManager.create(new SubTask("подзадача2", TaskStatus.IN_PROGRESS, "sub2")));
        taskManager.update(epic);
        System.out.println(epic);
        System.out.println("Create Epic with 2 subtask: " + epic);

        Epic epic2 = (Epic) taskManager.create(new Epic("Новый эпик 2", TaskStatus.NEW, "описание"));
        epic2.addSubTask((SubTask) taskManager.create(new SubTask("Новый subtask", TaskStatus.NEW, "описание")));
        taskManager.update(epic2);
        System.out.println("Create Epic with 1 subtask: " +  epic2);
        taskManager.delete(task.getId());
        taskManager.delete(epic.getId());

        System.out.println("print all: " +  taskManager.getAll());
    }
}
