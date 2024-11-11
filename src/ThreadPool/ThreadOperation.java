package ThreadPool;

public class ThreadOperation extends Thread{
    ThreadPool threadPool;
    String message;
    public ThreadOperation(ThreadPool threadPool, String message) {
        this.threadPool = threadPool;
        this.message = message;
    }



    @Override
    public void run() {
        this.threadPool.Acquire(Thread.currentThread().getId() + "");
        System.out.println("Thread " + Thread.currentThread().getId() + " is running");
        System.out.println("Message: " + this.message);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        this.threadPool.EndOperation(Thread.currentThread().getId() + "");
    }
}
