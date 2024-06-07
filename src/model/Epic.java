package model;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class Epic extends Task {
    private final ArrayList<SubTask> subTasks = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, TaskStatus.NEW, description);
    }

    public Epic(String name, TaskStatus status, String description) {
        super(name, status, description);
    }

    public Epic(String name, TaskStatus status, String description, LocalDateTime startTime, Duration duration) {
        super(name, status, description, startTime, duration);
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

        LocalDateTime first = LocalDateTime.MAX;
        Duration duration = Duration.ZERO;

        for (SubTask sTask : subTasks) {
            if (first.isAfter(sTask.getStartTime())) {
                first = sTask.getStartTime();
            }
            duration = duration.plus(sTask.getDuration());

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

        setStartTime(first);
        setDuration(duration);
    }

    @Override
    public Epic clone() {
        Epic task = new Epic(getName(), getStatus(), getDesc());
        task.setId(getId());
        for (SubTask t : getSubTasks()) {
            task.addSubTask(t);
        }
        task.calculateEpicStatus();
        return task;
    }

    @Override
    public String toString() {
        StringBuilder subtasks = new StringBuilder();
        if (!getSubTasks().isEmpty()) {
            subtasks.append(",").append(getSubTasks().getFirst().getId());
            for (int i = 1; i < getSubTasks().size(); ++i) {
                SubTask t = getSubTasks().get(i);
                subtasks.append(",").append(t.getId());
            }
        }
        return "EPIC," + getId() + "," + getDesc() + "," + getStatus() + "," + getStartTime() + "," + getDuration().toMinutes() + subtasks;
    }
}