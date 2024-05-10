package model;

public class Task {

    private int id;
    private String name;
    private TaskStatus status;
    private String desc;


    public Task(String name, TaskStatus status, String description) {
        this.name = name;
        this.status = status;
        this.desc = description;
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
        Task task = new Task(name, status, desc);
        task.setId(id);
        return task;
    }

    @Override
    public String toString() {
        return "TASK," + getId() + "," + getDesc() + "," + getStatus();
    }

    public static Task fromString(String value) {
        final String[] columns = value.split(",");
        String name = columns[0];
        int id = Integer.parseInt(columns[1]);
        String description = columns[2];
        TaskStatus status = TaskStatus.valueOf(columns[3]);
        TaskType type = TaskType.valueOf(columns[0]);
        return switch (type) {
            case TASK -> {
                Task t = new Task(name, status, description);
                t.setId(id);
                yield t;
            }
            case SUBTASK -> {
                int epic = Integer.parseInt(columns[4]);
                SubTask t = new SubTask(name, status, description, epic);
                t.setId(id);
                yield t;
            }
            case EPIC -> {
                Epic t = new Epic(name, status, description);
                t.setId(id);
                yield t;
            }
        };
    }
}
