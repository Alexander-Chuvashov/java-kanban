package manager;

import managers.Managers;
import managers.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import status.Stat;
import task.Epic;
import task.Subtask;
import task.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class InMemoryHistoryManagerTest {

    private static TaskManager taskManager;

    @BeforeEach
    public void beforeEach() {
        taskManager = Managers.getDefault();
    }


    //Должен вернуть Старый эпик после обновления
    @Test
    public void getHistoryShouldReturnOldEpicAfterUpdate() {
        Epic familyHolidays = new Epic("Организовать семейный праздник","Составить список участников");
        taskManager.addEpic(familyHolidays);
        taskManager.getEpicById(familyHolidays.getId());
        taskManager.updateEpic(new Epic(familyHolidays.getId(), "Новое имя", "Новое описание", Stat.IN_PROGRESS));
        List<Task> epics = taskManager.getHistory();
        Epic oldEpic = (Epic) epics.getFirst();
        assertEquals(familyHolidays.getName(), oldEpic.getName(), "В истории нет старой версии");
        assertEquals(familyHolidays.getDescription(), oldEpic.getDescription(), "В истории нет старой версии");
    }

    //вернуть эту подзадачу после обновления
    @Test
    public void getHistoryShouldReturnOldSubtaskAfterUpdate() {
        Epic familyHolidays = new Epic("Организовать семейный праздник", "Составить список участников");
        taskManager.addEpic(familyHolidays);
        Subtask familyHolidaysSubtask3 = new Subtask("Организовать семейный праздник", "Подобрать праздничную одежду", familyHolidays.getId());
        taskManager.addSubtask(familyHolidaysSubtask3);
        taskManager.getSubtaskById(familyHolidaysSubtask3.getId());
        taskManager.updateSubtask(new Subtask(familyHolidaysSubtask3.getId(), "Новое имя", "новое описание", Stat.IN_PROGRESS, familyHolidays.getId()));
        List<Task> subtasks = taskManager.getHistory();
        Subtask oldSubtask = (Subtask)subtasks.getFirst();
        assertEquals(familyHolidaysSubtask3.getName(), oldSubtask.getName(), "В истории нет старой версии");
        assertEquals(familyHolidaysSubtask3.getDescription(), oldSubtask.getDescription(), "В истории нет старой версии");
    }
}
