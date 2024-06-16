package task;

import org.junit.jupiter.api.Test;
import status.Stat;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubtaskTest {

    @Test
    public void SubtasksWithEqualIdShouldBeEqual() {
        Subtask subtask1 = new Subtask(10, "Починить электропроводку", "Купить розетку", Stat.NEW, 5);
        Subtask subtask2 = new Subtask(10, "", "", Stat.DONE, 5);
        assertEquals(subtask1, subtask2, "Ошибка");
    }
}
