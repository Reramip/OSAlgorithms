package threadsynchronization;

import java.util.concurrent.Semaphore;

class Barber implements Runnable {
    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        while (true) {
            try {
                SleepingBarber.ready.acquire();
                System.out.println("理发师开始理发");
                SleepingBarber.finish.release();
                System.out.println("理发师理完了");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Customer implements Runnable {
    @Override
    public void run() {
        try {
            SleepingBarber.mutex.acquire();
            if (SleepingBarber.waitingCount < 6) {
                SleepingBarber.waitingCount++;
                SleepingBarber.mutex.release();
                SleepingBarber.waitChair.acquire();
                System.out.println("顾客等待");
                SleepingBarber.barberChair.acquire();
                SleepingBarber.waitChair.release();
                SleepingBarber.ready.release();
                System.out.println("顾客准备好理发");
                SleepingBarber.finish.acquire();
                SleepingBarber.barberChair.release();
                System.out.println("顾客离开");
                SleepingBarber.mutex.acquire();
                SleepingBarber.waitingCount--;
            }
            SleepingBarber.mutex.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class SleepingBarber {
    public static int waitingCount = 0;
    public static Semaphore mutex = new Semaphore(1);
    public static Semaphore barberChair = new Semaphore(1);
    public static Semaphore waitChair = new Semaphore(5);
    public static Semaphore ready = new Semaphore(0);
    public static Semaphore finish = new Semaphore(0);

    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) {
        new Thread(new Barber()).start();
        while (true) {
            new Thread(new Customer()).start();
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
