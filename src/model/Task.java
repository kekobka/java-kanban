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

}
