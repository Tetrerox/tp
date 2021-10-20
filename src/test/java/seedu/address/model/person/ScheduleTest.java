package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.person.PersonTestUtil.createPeriod;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.DayOfWeek;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ScheduleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Schedule(null));
    }

    @Test
    public void constructor_invalidSchedule_throwsIllegalArgumentException() {
        String invalidSchedule = "";
        assertThrows(IllegalArgumentException.class, () -> new Schedule(invalidSchedule));
    }

    @Test
    public void isValidSchedule() {
        // null address
        assertThrows(NullPointerException.class, () -> Schedule.isValidSchedule(null));

        // invalid addresses
        assertFalse(Schedule.isValidSchedule("mon-1-")); // invalid string
        assertFalse(Schedule.isValidSchedule("abcde")); // invalid string

        // valid addresses
        assertTrue(Schedule.isValidSchedule("monday-morning tuesday-afternoon"));
        assertTrue(Schedule.isValidSchedule("")); //empty schedule
    }


    @Test
    public void getTotalWorkingHourTest() {
        Schedule schedule = new Schedule();
        assertEquals(0, schedule.getTotalWorkingHour());

        schedule.addShift(DayOfWeek.MONDAY, Slot.AFTERNOON);
        assertEquals(4, schedule.getTotalWorkingHour());
    }


    @Test
    public void getWorkingHourTest() {
        Schedule testSchedule = new Schedule();
        DayOfWeek toAdd = DayOfWeek.MONDAY;
        Period testPeriod = createPeriod(1, 7);
        for (int i = 1; i <= 7; i++) {
            testSchedule.addShift(toAdd, Slot.MORNING);
            int result = testSchedule.getTotalWorkingHour(testPeriod, List.of());
            assertEquals(i * Schedule.HOURS_PER_SLOT, result);
            toAdd = toAdd.plus(1);
        }
        int result = testSchedule.getTotalWorkingHour(testPeriod, List.of(createPeriod(1, 1)));
        assertEquals(6 * Schedule.HOURS_PER_SLOT, result);
    }
}
