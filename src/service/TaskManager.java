package service;

import model.Epic;
import model.SubTask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface TaskManager {


    int addNewTask(Task task);

    Task getTask(int id);

    void deleteTask(int id);

    void updateTask(Task task);

    int addNewEpic(Epic epic);

    Epic getEpic(int id);

    void deleteEpic(int id);

    void updateEpic(Epic epic);

    ArrayList<SubTask> getEpicSubTasks(int id);

    int addNewSubTask(SubTask task);

    SubTask getSubTask(int id);

    void updateSubTask(SubTask subTask);

    void deleteSubTask(int id);

    void deleteAll();

    void deleteAllTask();

    void deleteAllEpic();

    void deleteAllSubTask();

    List<Task> getHistory();

    HashMap<Integer, Task> getTasks();

    HashMap<Integer, Epic> getEpics();

    HashMap<Integer, SubTask> getSubTasks();

}
