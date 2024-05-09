package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@DisplayName("Epic")
class EpicTest {
    @Test
    @DisplayName("Экземпляры равны при равных id")
    void shouldEqualsIfIdEquals() {
        Epic task = new Epic("name", TaskStatus.NEW, "desc");
        Epic taskExpected = new Epic("name1", TaskStatus.NEW, "desc");
        task.setId(1);
        taskExpected.setId(1);
        assertEquals(taskExpected, task, "Эпики должны совпадать");
    }


    @Test
    void fromString() {
        Task t = Task.fromString("EPIC,0,Test description,NEW");
        assertInstanceOf(Epic.class, t, "не является экземпляром класса Epic");
    }
}