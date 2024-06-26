package model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Task {

    private int id;
    private String name;
    private TaskStatus status;
    private String desc;
    private LocalDateTime startTime; // LocalDateTime
    private long duration;
    private LocalDateTime endTime;

    public Task(String name, TaskStatus status, String description) {
        this(name, status, description, LocalDateTime.now(), Duration.ZERO);
    }

    public Task(String name, TaskStatus status, String description, LocalDateTime startTime, long duration) {
        this(name, status, description, startTime, Duration.ofMinutes(duration));
    }

    public Task(String name, TaskStatus status, String description, LocalDateTime startTime, Duration duration) {
        this.name = name;
        this.status = status;
        this.desc = description;

        this.startTime = startTime;
        this.duration = duration.toMinutes();
        this.endTime = startTime.plus(duration);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public Task clone() {
        Task task = new Task(name, status, desc, startTime, Duration.ofMinutes(duration));
        task.setId(id);
        return task;
    }

    @Override
    public String toString() {
        return "TASK," + getId() + "," + getDesc() + "," + getStatus() + "," + startTime + "," + duration;
    }

    public static Task fromString(String value) {
        final String[] columns = value.split(",");
        String name = columns[0];
        int id = Integer.parseInt(columns[1]);
        String description = columns[2];
        TaskStatus status = TaskStatus.valueOf(columns[3]);
        TaskType type = TaskType.valueOf(columns[0]);
        LocalDateTime startTime = LocalDateTime.parse(columns[4]);
        Duration duration = Duration.ofMinutes(Integer.parseInt(columns[5]));
        return switch (type) {
            case TASK -> {
                Task t = new Task(name, status, description, startTime, duration);
                t.setId(id);
                yield t;
            }
            case SUBTASK -> {
                int epic = Integer.parseInt(columns[6]);
                SubTask t = new SubTask(name, status, description, epic, startTime, duration);
                t.setId(id);
                yield t;
            }
            case EPIC -> {
                Epic t = new Epic(name, status, description, startTime, duration);
                t.setId(id);
                yield t;
            }
        };
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public Duration getDuration() {
        return Duration.ofMinutes(duration);
    }

    public void setDuration(Duration duration) {
        this.duration = duration.toMinutes();
        this.endTime = startTime.plus(duration);
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        this.endTime = startTime.plus(Duration.ofMinutes(duration));
    }

}
