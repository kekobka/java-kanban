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
        Task t = Task.fromString("TASK,0,Test description,NEW");
        assertInstanceOf(Task.class, t, "не является экземпляром класса Task");
    }
}