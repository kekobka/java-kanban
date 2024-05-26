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

    protected SubTask newSubTask(TaskStatus status, Epic epic) {
        return new SubTask("Test", status, "Test description", epic.getId());
    }

    @Test
    void fromString() {
        Task from = new Epic("Test", TaskStatus.NEW, "Test description");
        Task to = Task.fromString(from.toString());
        assertInstanceOf(Epic.class, to, "не является экземпляром класса Epic");
        assertEquals(from, to, "не является копией исходника");
    }

    @Test
    void checkEpicStatusNEW() {
        Epic epic = new Epic("name", TaskStatus.NEW, "desc");
        epic.addSubTask(newSubTask(TaskStatus.NEW, epic));
        epic.addSubTask(newSubTask(TaskStatus.NEW, epic));
        epic.addSubTask(newSubTask(TaskStatus.NEW, epic));
        assertEquals(TaskStatus.NEW, epic.getStatus(), "Статус эпика должен быть NEW");
    }

    @Test
    void checkEpicStatusDONE() {
        Epic epic = new Epic("name", TaskStatus.NEW, "desc");
        epic.addSubTask(newSubTask(TaskStatus.DONE, epic));
        epic.addSubTask(newSubTask(TaskStatus.DONE, epic));
        epic.addSubTask(newSubTask(TaskStatus.DONE, epic));
        assertEquals(TaskStatus.DONE, epic.getStatus(), "Статус эпика должен быть DONE");
    }

    @Test
    void checkEpicStatusNEW_DONE() {
        Epic epic = new Epic("name", TaskStatus.NEW, "desc");
        epic.addSubTask(newSubTask(TaskStatus.NEW, epic));
        epic.addSubTask(newSubTask(TaskStatus.DONE, epic));
        epic.addSubTask(newSubTask(TaskStatus.NEW, epic));
        epic.addSubTask(newSubTask(TaskStatus.DONE, epic));
        assertEquals(TaskStatus.IN_PROGRESS, epic.getStatus(), "Статус эпика должен быть IN_PROGRESS");
    }

    @Test
    void checkEpicStatusIN_PROGRESS() {
        Epic epic = new Epic("name", TaskStatus.NEW, "desc");
        epic.addSubTask(newSubTask(TaskStatus.IN_PROGRESS, epic));
        epic.addSubTask(newSubTask(TaskStatus.IN_PROGRESS, epic));
        epic.addSubTask(newSubTask(TaskStatus.IN_PROGRESS, epic));
        epic.addSubTask(newSubTask(TaskStatus.IN_PROGRESS, epic));
        assertEquals(TaskStatus.IN_PROGRESS, epic.getStatus(), "Статус эпика должен быть IN_PROGRESS");
    }
}