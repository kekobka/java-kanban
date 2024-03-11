package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Epic;
import model.SubTask;
import model.Task;

public class TaskManager {
    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, Epic> epics;
    private final HashMap<Integer, SubTask> subtasks;
    private int seq = 0;

    private int generateId() {
        return ++seq;
    }

    public TaskManager() {
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subtasks = new HashMap<>();

    }

    public Task createTask(Task task) {
        task.setId(generateId());
        tasks.put(task.getId(), task);
        return task;
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }

    public void deleteTask(int id) {
        tasks.remove(id);
    }

    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public Epic createEpic(Epic epic) {
        epic.setId(generateId());
        epics.put(epic.getId(), epic);
        return epic;
    }

    public Task getEpic(int id) {
        return epics.get(id);
    }

    public void deleteEpic(int id) {
        Epic removedEpic = epics.remove(id);
        if (removedEpic == null) {
            return;
        }

        ArrayList<SubTask> sTasks = removedEpic.getSubTasks();
        sTasks.forEach((v) -> {
            subtasks.remove(v.getId());
        });

    }

    public void updateEpic(Epic epic) {
        Epic saved = epics.get(epic.getId());
        saved.setName(epic.getName());
        epic.calculateEpicStatus();
    }

    public SubTask createSubTask(SubTask task) {
        task.setId(generateId());
        subtasks.put(task.getId(), task);
        return task;
    }

    public SubTask getSubTask(int id) {
        return subtasks.get(id);
    }

    public void updateSubTask(SubTask subTask) {
        subtasks.put(subTask.getId(), subTask);
        Integer epicId = subTask.getEpicId();
        Epic savedEpic = epics.get(epicId);

        updateEpic(savedEpic);
    }

    public void deleteSubTask(int id) {
        SubTask removedSubTask = subtasks.remove(id);
        if (removedSubTask == null) {
            return;
        }

        int epicId = removedSubTask.getEpicId();
        Epic epicSaved = epics.get(epicId);
        epicSaved.removeTask(removedSubTask);
    }

    public void deleteAll() {
        tasks.clear();
        epics.clear();
        subtasks.clear();
    }
    @Override
    public String toString() {
        return "TaskManager{" +
                "tasks=" + tasks +
                ", epics=" + epics +
                ", subtasks=" + subtasks +
                '}';
    }
}