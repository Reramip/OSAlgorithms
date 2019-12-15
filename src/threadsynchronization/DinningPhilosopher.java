package threadsynchronization;

import java.util.concurrent.Semaphore;

class Philosopher implements Runnable {
    int number;

    Philosopher(int number) {
        this.number = number;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        while (true) {
            try {
                System.out.printf("哲学家%d思考\n", number);
                Thread.sleep(10);
                int first = Math.min(number % 5, (number + 1) % 5);
                int second = Math.max(number % 5, (number + 1) % 5);
                // 先左后右拿
                DinningPhilosopher.forks[first].acquire();
                DinningPhilosopher.forks[second].acquire();
                System.out.printf("哲学家%d吃饭\n", number);
                Thread.sleep(10);
                // 先右后左放
                DinningPhilosopher.forks[second].release();
                DinningPhilosopher.forks[first].release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}

public class DinningPhilosopher {
    public static Semaphore[] forks = new Semaphore[]{
            new Semaphore(1),
            new Semaphore(1),
            new Semaphore(1),
            new Semaphore(1),
            new Semaphore(1)
    };

    public static void main(String[] args) {
        for (int i = 0; i < 5; ++i) {
            new Thread(new Philosopher(i)).start();
        }
    }
}
