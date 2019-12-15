package processscheduler;

import java.util.Arrays;
import java.util.LinkedList;

public class FCFSScheduler extends Scheduler {
    FCFSScheduler(int[] arriveTime, int[] serveTime) {
        super(arriveTime, serveTime);
    }

    @Override
    void schedule() {
        int[] needTime = Arrays.copyOf(serveTime, serveTime.length);
        LinkedList<Integer> queue = new LinkedList<>();
        int currentTime = 0;
        int completed = 0;
        int currentProcess = -1;
        while (completed < arriveTime.length) {
            for (int t = 0; t < arriveTime.length; ++t) {
                if (arriveTime[t] == currentTime) {
                    queue.addLast(t);
                }
            }
            if (!isAvailableProcess(currentProcess)) {
                currentProcess = queue.removeFirst();
            }
            result.addLast(currentProcess);
            --needTime[currentProcess];
            if (needTime[currentProcess] == 0) {
                currentProcess = -1;
                ++completed;
            }
            ++currentTime;
        }
    }
}
