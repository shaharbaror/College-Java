package Sceduler;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Sceduler {
    private final Map<String, Queue<String>> messageQueues;

    public Sceduler() {
        messageQueues = new HashMap<>();
    }
    public synchronized void signSceduler(String threadId) {
        messageQueues.put(threadId, new LinkedList<>());
        System.out.println("Signed up: " + threadId);
    }
    public synchronized void addMessage(String threadId, String message) {
        messageQueues.computeIfAbsent(threadId, k -> new LinkedList<>()).add(message);
        notifyAll();
    }

    public synchronized String getMessage(String threadId) {
        while (!messageQueues.containsKey(threadId) || messageQueues.get(threadId).isEmpty()) { // [1234,....] ["hello",...]
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return messageQueues.get(threadId).poll();
    }
}
