package model;

import java.util.ArrayList;

public class Epic extends Task {
    private final ArrayList<SubTask> subTasks = new ArrayList<>();

    public Epic(String name, TaskStatus status, String description) {
        super(name, status, description);
    }


    public void addSubTask(SubTask subTask) {
        subTask.setEpicId(getId());
        subTasks.add(subTask);
        calculateEpicStatus();
    }

    public ArrayList<SubTask> getSubTasks() {
        return subTasks;
    }

    public void removeTask(SubTask subTask) {
        subTasks.remove(subTask);
        calculateEpicStatus();
    }

    public void calculateEpicStatus() {
        boolean isdone = true;
        boolean isnew = true;
        for (SubTask sTask : subTasks) {
            if (sTask.getStatus() != TaskStatus.DONE) {
                isdone = false;
            }
            if (sTask.getStatus() != TaskStatus.NEW) {
                isnew = false;
            }
        }
        if (isnew) {
            setStatus(TaskStatus.NEW);
            return;
        }
        if (isdone) {
            setStatus(TaskStatus.DONE);
            return;
        }
        setStatus(TaskStatus.IN_PROGRESS);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subTasks=" + subTasks +
                ", status=" + this.getStatus() +
                '}';
    }
}