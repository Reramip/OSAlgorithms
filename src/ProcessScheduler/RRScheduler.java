package processscheduler;

import java.util.Arrays;
import java.util.LinkedList;

public class RRScheduler extends Scheduler {
    private int timeslice;

    RRScheduler(int[] arriveTime, int[] serveTime, int timeslice) {
        super(arriveTime, serveTime);
        this.timeslice = timeslice;
    }

    @Override
    void schedule() {
        int[] needTime = Arrays.copyOf(serveTime, serveTime.length);
        LinkedList<Integer> queue = new LinkedList<>();
        int currentTime = 0;
        int completed = 0;
        int currentProcess = -1;
        int currentTimeslice = 0;
        while (completed < arriveTime.length) {
            for (int t = 0; t < arriveTime.length; ++t) {
                if (arriveTime[t] == currentTime) {
                    queue.addLast(t);
                }
            }
            if (currentTimeslice == timeslice) {
                queue.addLast(currentProcess);
                currentTimeslice = 0;
                currentProcess = -1;
            }
            if (!isAvailableProcess(currentProcess)) {
                currentProcess = queue.removeFirst();
            }
            result.addLast(currentProcess);
            --needTime[currentProcess];
            if (needTime[currentProcess] == 0) {
                currentProcess = -1;
                currentTimeslice = 0;
                ++completed;
                ++currentTime;
                continue;
            }
            ++currentTimeslice;
            ++currentTime;

        }
    }
}
