package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Epic;
import model.SubTask;
import model.Task;

public class TaskManager {
    final HashMap<Integer, Task> tasks;
    private int seq = 0;
    private int generateId() {
        return ++seq;
    }
    public TaskManager() {
        this.tasks = new HashMap<>();

    }

    public Task create(Task task) {
        task.setId(generateId());
        tasks.put(task.getId(), task);
        return task;
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }
    public void update(Task task) {
        if(getTask(task.getId()) == null) {
            return;
        }
        if(task instanceof Epic t) {
            updateEpic(t);
        } else if (task instanceof SubTask t) {
            updateSubTask(t);
        }
        tasks.put(task.getId(), task);
    }
    private void updateEpic(Epic epic) {
        Epic saved = (Epic) tasks.get(epic.getId());
        saved.setName(epic.getName());
        epic.calculateEpicStatus();
    }
    private void updateSubTask(SubTask subTask) {
        Integer epicId = subTask.getEpicId();
        Epic savedEpic = (Epic) tasks.get(epicId);

        updateEpic(savedEpic);
    }
    public List<Task> getAll() {
        return new ArrayList<>(tasks.values());
    }
    public void delete(int id) {
        tasks.remove(id);
    }
    public void deleteAll() {
        tasks.clear();
    }
    public void deleteSubTask(int id) {
        SubTask removedSubTask = (SubTask) tasks.remove(id);
        if (removedSubTask == null) {
            return;
        }

        int epicId = removedSubTask.getEpicId();
        Epic epicSaved = (Epic) tasks.get(epicId);
        epicSaved.removeTask(removedSubTask);
    }

}