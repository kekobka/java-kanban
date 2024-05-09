package service;

import model.Task;

import java.io.*;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class FileBackedHistoryManager extends InMemoryHistoryManager {

    public static final String FILENAME_CSV = "history.csv";
    private final File file;

    public FileBackedHistoryManager() {
        this(new File(FILENAME_CSV));
    }

    public FileBackedHistoryManager(File file) {
        this.file = file;
    }

    public String getFilePath() {
        return file.getAbsolutePath();
    }

    private void save() {
        final List<Task> history = getHistory();
        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Task entry : history) {
                writer.append(entry.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Ошибка в файле: " + file.getAbsolutePath(), e);
        }
    }

    private void loadFromFile() {
        try (final BufferedReader reader = new BufferedReader(new FileReader(file, UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                final Task task = Task.fromString(line);
                super.add(task);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static FileBackedHistoryManager loadFromFile(File file) {
        final FileBackedHistoryManager manager = new FileBackedHistoryManager(file);
        manager.loadFromFile();
        return manager;
    }

    @Override
    public void add(Task task) {
        super.add(task);
        save();
    }

    @Override
    public void remove(int id) {
        super.remove(id);
        save();
    }
}
