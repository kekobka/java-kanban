package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Task;

public class InMemoryHistoryManager implements HistoryManager {

    ArrayList<Task> history = new ArrayList<>();

    @Override
    public void add(Task task) {
        history.add(task.clone());
    }

    @Override
    public List<Task> getHistory() {
        return history.subList(Math.max(history.size()-10,0), history.size());
    }

    @Override
    public void remove(int id) {
        history.remove(id);
    }
}