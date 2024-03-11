package model;

public class SubTask extends Task {

    private int epicId;

    public SubTask(String name, TaskStatus status, String description) {
        super(name, status, description);
    }

    public SubTask(String name, TaskStatus status, String description, int epicId) {
        super(name, status, description);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
}
