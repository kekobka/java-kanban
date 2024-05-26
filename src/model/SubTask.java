package model;

import java.time.Duration;
import java.time.LocalDateTime;

public class SubTask extends Task {

    private int epicId;

    public SubTask(String name, TaskStatus status, String description) {
        super(name, status, description);
    }

    public SubTask(String name, TaskStatus status, String description, LocalDateTime startTime, Duration duration) {
        super(name, status, description, startTime, duration);
    }

    public SubTask(String name, TaskStatus status, String description, int epicId) {
        super(name, status, description);
        this.epicId = epicId;
    }

    public SubTask(String name, TaskStatus status, String description, int epicId, LocalDateTime startTime, Duration duration) {
        super(name, status, description, startTime, duration);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public SubTask clone() {
        SubTask task = new SubTask(getName(), getStatus(), getDesc(), epicId);
        task.setId(getId());
        return task;
    }

    @Override
    public String toString() {
        return "SUBTASK," + getId() + "," + getDesc() + "," + getStatus() + "," + getStartTime() + "," + getDuration() + "," + getEpicId();
    }
}
