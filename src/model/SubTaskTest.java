package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("SubTask")
class SubTaskTest {
    @Test
    @DisplayName("Экземпляры равны при равных id")
    void shouldEqualsIfIdEquals() {
        SubTask task = new SubTask("name", TaskStatus.NEW, "desc");
        SubTask taskExpected = new SubTask("name1", TaskStatus.NEW, "desc");
        task.setId(1);
        taskExpected.setId(1);
        assertEquals(taskExpected, task, "Сабтаски должны совпадать");
    }
}