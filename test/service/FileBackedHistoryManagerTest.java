package service;

import model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static service.FileBackedHistoryManager.FILENAME_CSV;

public class FileBackedHistoryManagerTest extends HistoryManagerTest<FileBackedHistoryManager> {

    @Override
    public FileBackedHistoryManager createManager() {
        try {
            return new FileBackedHistoryManager(File.createTempFile(FILENAME_CSV, ".temp"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void loadFromCSVWithEmptyFile() {
        try {
            FileBackedHistoryManager.loadFromFile(File.createTempFile(FileBackedHistoryManager.FILENAME_CSV, ".temp"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void loadFromCSVWithBreakFile() {
        try {
            File tempFile = File.createTempFile(FileBackedHistoryManager.FILENAME_CSV, ".temp");
            FileWriter fileWriter = new FileWriter(tempFile, true);
            System.out.println(tempFile.getAbsolutePath());
            BufferedWriter bw = new BufferedWriter(fileWriter);
            bw.write("IM BREAK");
            bw.close();
            FileBackedHistoryManager.loadFromFile(tempFile);
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
        historyManager.add(t);
        FileBackedHistoryManager m = FileBackedHistoryManager.loadFromFile(new File(historyManager.getFilePath()));
        Assertions.assertEquals(historyManager, m, "loadFromFile не воссоздает копию");
    }
}