package service;

import model.Epic;
import model.SubTask;
import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, Epic> epics;
    private final HashMap<Integer, SubTask> subtasks;
    private final HistoryManager historyManager;
    private int seq = 0;

    private int generateId() {
        return ++seq;
    }

    public InMemoryTaskManager(HistoryManager history) {
        this.historyManager = history;
        this.tasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.subtasks = new HashMap<>();

    }

    @Override
    public int addNewTask(Task task) {
        int id = generateId();
        task.setId(id);
        tasks.put(id, task);
        return id;
    }

    @Override
    public Task getTask(int id) {
        Task task = tasks.get(id);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public void deleteTask(int id) {
        tasks.remove(id);
    }

    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public int addNewEpic(Epic epic) {
        int id = generateId();
        epic.setId(id);
        epics.put(id, epic);
        return id;
    }

    @Override
    public Epic getEpic(int id) {
        Epic task = epics.get(id);
        if (task != null) {
            historyManager.add(task);
        }

        return task;
    }

    @Override
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

    @Override
    public void updateEpic(Epic epic) {
        Epic saved = epics.get(epic.getId());
        saved.setName(epic.getName());
        epic.calculateEpicStatus();
    }

    @Override
    public ArrayList<SubTask> getEpicSubTasks(int id) {
        return epics.get(id).getSubTasks();
    }

    @Override
    public int addNewSubTask(SubTask task) {
        int sid = generateId();
        task.setId(sid);
        subtasks.put(sid, task);
        int epicId = task.getEpicId();
        if (epicId != 0) {
            epics.get(epicId).addSubTask(task);
        }
        return sid;
    }

    @Override
    public SubTask getSubTask(int id) {
        SubTask task = subtasks.get(id);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        subtasks.put(subTask.getId(), subTask);
        Integer epicId = subTask.getEpicId();
        Epic savedEpic = epics.get(epicId);

        updateEpic(savedEpic);
    }

    @Override
    public void deleteSubTask(int id) {
        SubTask removedSubTask = subtasks.remove(id);
        if (removedSubTask == null) {
            return;
        }

        int epicId = removedSubTask.getEpicId();
        Epic epicSaved = epics.get(epicId);
        epicSaved.removeTask(removedSubTask);
    }

    @Override
    public void deleteAll() {
        tasks.clear();
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void deleteAllTask() {
        tasks.clear();
    }

    @Override
    public void deleteAllEpic() {
        epics.forEach((i, epic) -> {
            ArrayList<SubTask> sTasks = epic.getSubTasks();
            sTasks.forEach((v) -> {
                subtasks.remove(v.getId());
            });
        });
        epics.clear();
    }

    @Override
    public void deleteAllSubTask() {
        subtasks.forEach((i, st) -> {
            int epicId = st.getEpicId();
            Epic epicSaved = epics.get(epicId);
            epicSaved.removeTask(st);
            epicSaved.calculateEpicStatus();
        });
        subtasks.clear();
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public String toString() {
        return "InMemoryTaskManager{" +
                "tasks=" + tasks +
                ", epics=" + epics +
                ", subtasks=" + subtasks +
                '}';
    }

    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    @Override
    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    @Override
    public HashMap<Integer, SubTask> getSubTasks() {
        return subtasks;
    }
}