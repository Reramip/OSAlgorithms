package processscheduler;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SchedulerTest {
    int[] arriveTime = new int[]{0, 2, 4, 6, 8};
    int[] serveTime = new int[]{3, 6, 4, 5, 2};
    Scheduler scheduler;
    List<Integer> expected;

    @Test
    void testScheduleFCFS() {
        scheduler = new FCFSScheduler(arriveTime, serveTime);
        expected = Arrays.asList(
                0, 0, 0, 1, 1,
                1, 1, 1, 1, 2,
                2, 2, 2, 3, 3,
                3, 3, 3, 4, 4);
        scheduler.schedule();
        assertIterableEquals(expected, scheduler.getResult());
    }

    @Test
    void testScheduleRR1() {
        scheduler = new RRScheduler(arriveTime, serveTime, 1);
        expected = Arrays.asList(
                0, 0, 1, 0, 1,
                2, 1, 3, 2, 1,
                4, 3, 2, 1, 4,
                3, 2, 1, 3, 3);
        scheduler.schedule();
        assertIterableEquals(expected, scheduler.getResult());
    }

    @Test
    void testScheduleSPN() {
        scheduler = new SPNScheduler(arriveTime, serveTime);
        expected = Arrays.asList(
                0, 0, 0, 1, 1,
                1, 1, 1, 1, 4,
                4, 2, 2, 2, 2,
                3, 3, 3, 3, 3);
        scheduler.schedule();
        assertIterableEquals(expected, scheduler.getResult());
    }

    @Test
    void testScheduleSRT() {
        scheduler = new SRTScheduler(arriveTime, serveTime);
        expected = Arrays.asList(
                0, 0, 0, 1, 2,
                2, 2, 2, 4, 4,
                1, 1, 1, 1, 1,
                3, 3, 3, 3, 3);
        scheduler.schedule();
        assertIterableEquals(expected, scheduler.getResult());
    }

    @Test
    void testScheduleHRRN() {
        scheduler = new HRRNScheduler(arriveTime, serveTime);
        expected = Arrays.asList(
                0, 0, 0, 1, 1,
                1, 1, 1, 1, 2,
                2, 2, 2, 4, 4,
                3, 3, 3, 3, 3);
        scheduler.schedule();
        assertIterableEquals(expected, scheduler.getResult());
    }

    @Test
    void testScheduleFB() {
        scheduler = new FBScheduler(arriveTime, serveTime);
        expected = Arrays.asList(
                0, 0, 1, 0, 2,
                1, 1, 3, 4, 2,
                2, 3, 3, 4, 1,
                1, 1, 2, 3, 3);
        scheduler.schedule();
        assertIterableEquals(expected, scheduler.getResult());
    }
}
