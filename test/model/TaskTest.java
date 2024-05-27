package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@DisplayName("Task")
class TaskTest {

    @Test
    @DisplayName("Экземпляры равны при равных id")
    void shouldEqualsIfIdEquals() {
        Task task = new Task("name", TaskStatus.NEW, "desc");
        Task taskExpected = new Task("name1", TaskStatus.NEW, "desc");
        task.setId(1);
        taskExpected.setId(1);
        assertEquals(taskExpected, task, "Таски должны совпадать");
    }

    @Test
    void fromString() {
        Task from = new Task("Test", TaskStatus.NEW, "Test description");
        Task to = Task.fromString(from.toString());
        assertInstanceOf(Task.class, to, "не является экземпляром класса Task");
        assertEquals(from, to, "не является копией исходника");
    }
}