package threadsynchronization;

import java.util.concurrent.Semaphore;

class WFReader implements Runnable {
    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        while (true) {
            try {
                WriterFirst.readWait.acquire();
                WriterFirst.readable.acquire();
                WriterFirst.readCountMutex.acquire();
                WriterFirst.readCount++;
                if (WriterFirst.readCount == 1) {
                    WriterFirst.writable.acquire();
                }
                WriterFirst.readCountMutex.release();
                WriterFirst.readable.release();
                WriterFirst.readWait.release();
                System.out.println("读数据");
                Thread.sleep(10);
                WriterFirst.readCountMutex.acquire();
                WriterFirst.readCount--;
                if (WriterFirst.readCount == 0) {
                    WriterFirst.writable.release();
                }
                WriterFirst.readCountMutex.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class WFWriter implements Runnable {
    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        while (true) {
            try {
                WriterFirst.writeCountMutex.acquire();
                WriterFirst.writeCount++;
                if (WriterFirst.writeCount == 1) {
                    WriterFirst.readable.acquire();
                }
                WriterFirst.writeCountMutex.release();
                WriterFirst.writable.acquire();
                System.out.println("写数据");
                Thread.sleep(10);
                WriterFirst.writable.release();
                WriterFirst.writeCountMutex.acquire();
                WriterFirst.writeCount--;
                if (WriterFirst.writeCount == 0) {
                    WriterFirst.readable.release();
                }
                WriterFirst.writeCountMutex.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class WriterFirst {
    public static int readCount = 0;
    public static Semaphore readCountMutex = new Semaphore(1);
    public static Semaphore readable = new Semaphore(1);
    public static Semaphore readWait = new Semaphore(1);

    public static int writeCount = 0;
    public static Semaphore writeCountMutex = new Semaphore(1);
    public static Semaphore writable = new Semaphore(1);

    public static void main(String[] args) {
        new Thread(new WFReader()).start();
        new Thread(new WFWriter()).start();
    }
}
