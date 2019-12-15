package processscheduler;

import java.util.LinkedList;
import java.util.List;

abstract class Scheduler {
    protected final int[] arriveTime;
    protected final int[] serveTime;
    protected LinkedList<Integer> result = new LinkedList<>();

    Scheduler(int[] arriveTime, int[] serveTime) {
        this.arriveTime = arriveTime;
        this.serveTime = serveTime;
    }

    abstract void schedule();

    List<Integer> getResult() {
        return result;
    }

    protected boolean isAvailableProcess(int currentProcess) {
        return currentProcess != -1;
    }
}
