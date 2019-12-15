package processscheduler;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class FBScheduler extends Scheduler {
    FBScheduler(int[] arriveTime, int[] serveTime) {
        super(arriveTime, serveTime);
    }

    @Override
    void schedule() {
        int[] needTime = Arrays.copyOf(serveTime, serveTime.length);
        int currentTime = 0;
        int completed = 0;
        int currentProcess = -1;
        final int MAX_PRIORITY = 2;
        ArrayList<LinkedList<Integer>> queueWithPriority = new ArrayList<>();
        for (int i = 0; i <= MAX_PRIORITY; ++i) {
            queueWithPriority.add(new LinkedList<>());
        }
        int[] priorities = new int[arriveTime.length];
        int[] timeslices = new int[arriveTime.length];
        int currentTimeslice = 0;
        while (completed < arriveTime.length) {
            for (int t = 0; t < arriveTime.length; ++t) {
                if (arriveTime[t] == currentTime) {
                    queueWithPriority.get(0).addLast(t);
                    priorities[t] = 0;
                    timeslices[t] = (int) Math.pow(2, 0);
                }
            }
            if (isAvailableProcess(currentProcess)) {
                if (currentTimeslice == timeslices[currentProcess]) {
                    for (int i = 0; i <= MAX_PRIORITY; ++i) {
                        if (!queueWithPriority.get(i).isEmpty()) {
                            ++priorities[currentProcess];
                            if (priorities[currentProcess] > MAX_PRIORITY) {
                                priorities[currentProcess] = MAX_PRIORITY;
                            }
                            timeslices[currentProcess] = (int) Math.pow(2, priorities[currentProcess]);
                            break;
                        }
                    }
                    queueWithPriority.get(priorities[currentProcess]).addLast(currentProcess);
                    currentTimeslice = 0;
                    currentProcess = -1;
                }
            }
            if (!isAvailableProcess(currentProcess)) {
                for (int i = 0; i <= MAX_PRIORITY; ++i) {
                    if (!queueWithPriority.get(i).isEmpty()) {
                        currentProcess = queueWithPriority.get(i).removeFirst();
                        break;
                    }
                }
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
