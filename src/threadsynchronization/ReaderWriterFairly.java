package threadsynchronization;

import java.util.concurrent.Semaphore;

class FairReader implements Runnable {
    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        while (true) {
            try {
                ReaderWriterFairly.accessible.acquire();
                ReaderWriterFairly.readCountMutex.acquire();
                ReaderWriterFairly.readCount++;
                if (ReaderWriterFairly.readCount == 1) {
                    ReaderWriterFairly.writable.acquire();
                }
                ReaderWriterFairly.readCountMutex.release();
                ReaderWriterFairly.accessible.release();
                System.out.println("读数据");
                Thread.sleep(10);
                ReaderWriterFairly.readCountMutex.acquire();
                ReaderWriterFairly.readCount--;
                if (ReaderWriterFairly.readCount == 0) {
                    ReaderWriterFairly.writable.release();
                }
                ReaderWriterFairly.readCountMutex.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class FairWriter implements Runnable {
    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        while (true) {
            try {
                ReaderWriterFairly.accessible.acquire();
                ReaderWriterFairly.writable.acquire();
                System.out.println("写数据");
                Thread.sleep(10);
                ReaderWriterFairly.writable.release();
                ReaderWriterFairly.accessible.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class ReaderWriterFairly {
    public static int readCount = 0;
    public static Semaphore readCountMutex = new Semaphore(1);
    public static Semaphore writable = new Semaphore(1);
    public static Semaphore accessible = new Semaphore(1);

    public static void main(String[] args) {
        new Thread(new FairReader()).start();
        new Thread(new FairWriter()).start();
    }
}
