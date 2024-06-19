package manager;

import managers.Managers;
import managers.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import status.Stat;
import task.Epic;
import task.Subtask;
import task.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static org.junit.jupiter.api.Assertions.*;


public class InMemoryTaskManagerTest {
    private static TaskManager taskManager;

    @BeforeEach
    public void beforeEach() {
        taskManager = Managers.getDefault();
    }

    //проверка на добавляет ли задачу и ищет по id
    @Test
    void addNewTest() {
        final Task task = taskManager.addTask(new Task("Test", "Test addNewTask description"));
        final Task savedTask = taskManager.getTaskById(task.getId());
        assertNotNull(savedTask, "The task was not found");
        assertEquals(task, savedTask, "The tasks do not match");
        final List<Task> tasks = taskManager.getTasks();
        assertNotNull(tasks, "The tasks are not returned");
        assertEquals(1, tasks.size(), "Incorrect number of tasks");
    }

    //проверка на добавляет ли эпик и подзадачу и ищет по id
    @Test
    void addNewEpicAndSubtasks() {
        final Epic familyHolidays = taskManager.addEpic(new Epic(1, "Организовать семейный праздник",
                "Составить список участников", Stat.IN_PROGRESS));

        final Subtask familyHolidaysSubtask1 = taskManager.addSubtask(new Subtask("Организовать семейный праздник",
                "Продумать меню", familyHolidays.getId()));
        final Subtask familyHolidaysSubtask2 = taskManager.addSubtask(new Subtask("Организовать семейный праздник",
                "Подобрать праздничную одежду", familyHolidays.getId()));

        final Epic savedEpic = taskManager.getEpicById(familyHolidays.getId());
        Subtask savedSubtask1 = taskManager.getSubtaskById(familyHolidaysSubtask1.getId());
        final Subtask savedSubtask2 = taskManager.getSubtaskById(familyHolidaysSubtask2.getId());

        assertNotNull(savedEpic, "Epic is not found");
        assertNotNull(savedSubtask2, "The subtask was not found");
        assertEquals(familyHolidays, savedEpic, "The epics don't match");
        assertEquals(familyHolidaysSubtask1, savedSubtask1, "The subtasks do not match");
        assertEquals(familyHolidaysSubtask2, savedSubtask2, "The subtasks do not match");

        final List<Epic> epics = taskManager.getEpics();
        assertNotNull(epics, "Epics don't come back");
        assertEquals(1, epics.size(), "The wrong number of epics");

        final List<Subtask> subtasks =  taskManager.getSubtasks();
        assertNotNull(subtasks, "Subtasks are not returned");
        assertEquals(1, epics.size(), "Incorrect number of subtasks");

    }

    // Обновить задачу, затем вернуть задачу с тем же id
    @Test
    public void updateTaskShouldReturnTaskWithTheSameId() {
        final Task expected = new Task("имя", "описание");
        taskManager.addTask(expected);
        final Task updatedTask = new Task(expected.getId(), "новое имя", "новое описание" , Stat.DONE);
        final Task actual = taskManager.updateTask(updatedTask);
        assertEquals(expected, actual, "Вернулась задача с другим id");
    }

    // Обновите эпик, затем вернуть эпик с тем же id
    @Test
    public void updateEpicShouldReturnEpicWithTheSameId() {
        final Epic expected = new Epic(1, "имя", "описание", Stat.IN_PROGRESS);
        taskManager.addEpic(expected);
        final Epic updatedEpic = new Epic(1, "новое имя", "новое описание", Stat.IN_PROGRESS);
        final Epic actual = taskManager.updateEpic(updatedEpic);
        assertEquals(expected, actual, "Вернулся эпик с другим id");
    }

    //Обновляет подзадачу, затем возвращает подзадачу с тем же id
    @Test
    public void updateSubtaskShouldReturnSubtaskWithTheSameId() {
        final Epic epic = new Epic("имя", "описание");
        taskManager.addEpic(epic);
        final Subtask expected = new Subtask("имя", "описание", epic.getId());
        taskManager.addSubtask(expected);
        final Subtask updatedSubtask = new Subtask
                (expected.getId(), "новое имя", "новое описание", Stat.DONE, epic.getId());
        final Subtask actual = taskManager.updateSubtask(updatedSubtask);
        assertEquals(expected, actual, "Вернулась подзадача с другим id");
    }

    //Удалить эпик и вернуть пустой список
    @Test
    public void deleteEpicsShouldReturnEmptyList() {
        taskManager.addEpic(new Epic(1, "Задача3","Подзадача3", Stat.IN_PROGRESS));
        taskManager.deleteEpics();
        List<Epic> epics = taskManager.getEpics();
        assertTrue(epics.isEmpty(), "Список Эпиков должен быть пуст");
    }


    //удаление Задачи По id
    @Test
    public void deleteTaskByIdShouldReturnNullfKeyIsMissing() {
        taskManager.addTask(new Task(1, "Задача1", "Подзадача1", Stat.NEW));
        taskManager.addTask(new Task(2, "Задача2", "Подзадача2", Stat.DONE));
        assertNull(taskManager.deleteTaskById(3));
    }

    //удаление эпик По Id
    @Test
    public void deleteEpicByIdShouldReturnNullfkeyIsMissing() {
        taskManager.addEpic(new Epic(1, "Задача3", "Подзадача3", Stat.IN_PROGRESS));
        taskManager.getEpicById(1);
        assertNull(taskManager.deleteTaskById(1));
    }
}
