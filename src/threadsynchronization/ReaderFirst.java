package threadsynchronization;

import java.util.concurrent.Semaphore;

class RFReader implements Runnable {
    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        while (true) {
            try {
                ReaderFirst.mutex.acquire();
                ReaderFirst.readCount++;
                if (ReaderFirst.readCount == 1) {
                    ReaderFirst.writable.acquire();
                }
                ReaderFirst.mutex.release();
                System.out.println("读数据");
                ReaderFirst.mutex.acquire();
                ReaderFirst.readCount--;
                if (ReaderFirst.readCount == 0) {
                    ReaderFirst.writable.release();
                }
                ReaderFirst.mutex.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class RFWriter implements Runnable {
    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        while (true) {
            try {
                ReaderFirst.writable.acquire();
                System.out.println("写数据");
                ReaderFirst.writable.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class ReaderFirst {
    public static int readCount = 0;
    public static Semaphore mutex = new Semaphore(1);
    public static Semaphore writable = new Semaphore(1);

    public static void main(String[] args) {
        new Thread(new RFReader()).start();
        new Thread(new RFWriter()).start();
    }
}
