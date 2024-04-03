package service;

import model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private final ArrayList<Task> history = new ArrayList<>();

    @Override
    public void add(Task task) {
        history.add(task.clone());
    }

    @Override
    public List<Task> getHistory() {
        return history.subList(Math.max(history.size() - 10, 0), history.size());
    }

    // remove by internal list id
    @Override
    public void remove(int id) {
        if (id < 0 || id >= history.size()) {
            return;
        }
        history.remove(id);
    }
}
