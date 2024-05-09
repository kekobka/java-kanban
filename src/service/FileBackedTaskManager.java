package service;

import model.*;

import java.io.*;
import java.util.Map;


import static java.nio.charset.StandardCharsets.UTF_8;

public class FileBackedTaskManager extends InMemoryTaskManager {

    public static final String FILENAME_CSV = "tasks.csv";

    private final File file;

    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager manager = new FileBackedTaskManager(file);
        manager.loadFromFile();
        return manager;
    }

    public String getFilePath() {
        return file.getAbsolutePath();
    }

    public FileBackedTaskManager() {
        this(Managers.getDefaultHistory());
    }

    public FileBackedTaskManager(File file) {
        this(Managers.getDefaultHistory(), file);
    }

    public FileBackedTaskManager(HistoryManager historyManager) {
        this(historyManager, new File(FILENAME_CSV));
    }

    public FileBackedTaskManager(HistoryManager historyManager, File file) {
        super(historyManager);
        this.file = file;
    }

    @Override
    public int addNewSubTask(SubTask subtask) {
        int ret = super.addNewSubTask(subtask);
        save();
        return ret;
    }

    @Override
    public int addNewTask(Task task) {
        int ret = super.addNewTask(task);
        save();
        return ret;
    }

    @Override
    public int addNewEpic(Epic epic) {
        int ret = super.addNewEpic(epic);
        save();
        return ret;
    }

    private void loadFromFile() {
        try (final BufferedReader reader = new BufferedReader(new FileReader(file, UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {

                final Task task = Task.fromString(line);
                final int id = task.getId();
                if (task instanceof SubTask subtask) {
                    subtasks.put(id, subtask);
                } else if (task instanceof Epic epic) {
                    epics.put(id, epic);
                    final String[] columns = line.split(",");
                    for (int i = 4; i < columns.length; i++) {
                        epic.addSubTask(subtasks.get(Integer.parseInt(columns[i])));
                    }
                } else {
                    tasks.put(id, task);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void save() {
        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Map.Entry<Integer, Task> entry : tasks.entrySet()) {
                writer.append(entry.getValue().toString());
                writer.newLine();
            }
            for (Map.Entry<Integer, SubTask> entry : subtasks.entrySet()) {
                writer.append(entry.getValue().toString());
                writer.newLine();
            }
            for (Map.Entry<Integer, Epic> entry : epics.entrySet()) {
                writer.append(entry.getValue().toString());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка в файле: " + file.getAbsolutePath(), e);
        }
    }
}
