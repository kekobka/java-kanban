package service;

import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static service.FileBackedTaskManager.FILENAME_CSV;

class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTaskManager> {

    @Override
    public FileBackedTaskManager createManager() {
        try {
            return new FileBackedTaskManager(File.createTempFile(FILENAME_CSV, ".temp"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void loadFromCSVWithEmptyFile() {
        try {
            FileBackedTaskManager.loadFromFile(File.createTempFile(FILENAME_CSV, ".temp"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void loadFromCSVWithBreakFile() {
        try {
            File tempFile = File.createTempFile(FILENAME_CSV, ".temp");
            FileWriter fileWriter = new FileWriter(tempFile, true);
            System.out.println(tempFile.getAbsolutePath());
            BufferedWriter bw = new BufferedWriter(fileWriter);
            bw.write("IM BREAK");
            bw.close();
            FileBackedTaskManager.loadFromFile(tempFile);
            throw new AssertionFailedError("loadFromFile не выбрасывает ошибку при сломанном файле");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ArrayIndexOutOfBoundsException e) {
            Assertions.assertEquals(e.getMessage(), "Index 1 out of bounds for length 1");
        }
    }

    @Test
    void loadFromCSVWithTasks() {
        Task t = newTask();
        t.setDesc("ЮТФ8"); // utf8 support check
        taskManager.addNewTask(t);
        FileBackedTaskManager m = FileBackedTaskManager.loadFromFile(new File(taskManager.getFilePath()));
        Assertions.assertEquals(taskManager, m, "loadFromFile не воссоздает копию");
    }

    @Test
    void saveCSVWithDeletedTasks() {
        taskManager.deleteAll();
        int id = taskManager.addNewTask(newTask());
        Task task = taskManager.getTask(id);
        try {
            Assertions.assertEquals(task.toString(), Files.readString(Paths.get(taskManager.getFilePath())).trim());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}