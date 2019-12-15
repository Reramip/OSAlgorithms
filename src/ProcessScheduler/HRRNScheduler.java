package ProcessScheduler;

import java.util.Arrays;
import java.util.LinkedList;

public class HRRNScheduler extends Scheduler {
    HRRNScheduler(int[] arriveTime, int[] serveTime) {
        super(arriveTime, serveTime);
    }

    @Override
    void schedule() {
        int[] needTime = Arrays.copyOf(serveTime, serveTime.length);
        int currentTime = 0;
        int completed = 0;
        int currentProcess = -1;
        int arriveCount = 0;
        while (completed < arriveTime.length) {
            for (int t : arriveTime) {
                if (t == currentTime) {
                    ++arriveCount;
                }
            }
            if (!isAvailableProcess(currentProcess)) {
                for (int max = -1, i = 0; i < arriveCount; ++i) {
                    if (needTime[i] > 0) {
                        int rr = (currentTime - arriveTime[i] + needTime[i]) / needTime[i];
                        if (rr > max) {
                            max = rr;
                            currentProcess = i;
                        }
                    }
                }
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
