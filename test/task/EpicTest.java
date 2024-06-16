package task;

import org.junit.jupiter.api.Test;
import status.Stat;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EpicTest {

    @Test
    public void EpicsWithEqualIdShouldBeEqual() {
        Epic epic1 = new Epic(10, "", "", Stat.NEW);
        Epic epic2 = new Epic(10, "", "", Stat.IN_PROGRESS);
        assertEquals(epic1, epic2, "");
    }
}
