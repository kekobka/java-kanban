package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

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

    @Test
    void fromString() {
        Task t = Task.fromString("SUBTASK,0,Test description,NEW,0");
        assertInstanceOf(SubTask.class, t, "не является экземпляром класса SubTask");
    }
}