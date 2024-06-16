package task;

import org.junit.jupiter.api.Test;
import status.Stat;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskTest {

    @Test
    public void tasksWithEqualIdShouldBeEqual() {
        Task task1 = new Task(10, "", "", Stat.NEW);
        Task task2 = new Task(10, "", "", Stat.DONE);

        assertEquals(task1, task2, "");
    }
}
