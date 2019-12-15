package threadsynchronization;

import java.util.concurrent.Semaphore;

class Producer implements Runnable {
    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("生产数据");
                Thread.sleep(5);
                ProducerConsumer.buffer.acquire();
                ProducerConsumer.mutex.acquire();
                System.out.println("写入数据");
                Thread.sleep(10);
                ProducerConsumer.mutex.release();
                ProducerConsumer.resource.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer implements Runnable {
    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        while (true) {
            try {
                ProducerConsumer.resource.acquire();
                ProducerConsumer.mutex.acquire();
                System.out.println("读取数据");
                Thread.sleep(10);
                ProducerConsumer.mutex.release();
                ProducerConsumer.buffer.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class ProducerConsumer {
    public static Semaphore mutex = new Semaphore(1);
    public static Semaphore buffer = new Semaphore(1);
    public static Semaphore resource = new Semaphore(0);

    public static void main(String[] args) {
        new Thread(new Producer()).start();
        new Thread(new Consumer()).start();
    }
}
