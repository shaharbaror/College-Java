package ThreadPool;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class ThreadPool {
    private Queue<String> threadPool;
    private String[] currentThreads;
    private int maxSize;
    private int currentSize;

    public ThreadPool(int maxSize) {
        this.maxSize = maxSize;
        this.currentSize = 0;
        this.currentThreads = new String[maxSize];
        this.threadPool = new LinkedList<>();
    }

    private boolean isInThreads(String threadId) {
        for (String thread : this.currentThreads) {
            if (Objects.equals(thread, threadId)) {
                return true;
            }
        }
        return false;
    }

    private synchronized void addThread(String threadId) {
        if (this.currentSize < this.maxSize) {
            for (int i = 0; i < this.maxSize; i++) {
                System.out.println("HERE");
                if (this.currentThreads[i] == null) {
                    this.currentThreads[i] = threadId;
                    this.currentSize++;
                    break;
                }
            }
        }
    }

    public synchronized void Acquire(String threadId) {
        this.threadPool.add(threadId);
        while (this.currentSize > this.maxSize && !Objects.equals(this.threadPool.peek(), threadId)) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        addThread(this.threadPool.poll());
    }

    public synchronized void EndOperation(String threadId) {
        for (int i = 0; i < this.currentSize; i++) {
            if (Objects.equals(this.currentThreads[i], threadId)) {
                this.currentThreads[i] = null;
                this.currentSize--;
                break;
            }
        }
        notifyAll();
    }
}
