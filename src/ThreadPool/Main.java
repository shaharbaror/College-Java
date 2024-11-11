package ThreadPool;

public class Main {
    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool(5);
        ThreadOperation[] threadOperations = new ThreadOperation[12];
        for (int i = 0; i < 12; i++) {
            threadOperations[i] = new ThreadOperation(threadPool, "Thread " + i);
            threadOperations[i].start();
        }

        for (int i = 0; i < 12; i++) {
            try {
                threadOperations[i].join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
