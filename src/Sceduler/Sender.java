package Sceduler;

public class Sender extends Thread {
    private final Sceduler sceduler;
    private final String threadId;

    public Sender(Sceduler sceduler, String threadId) {
        this.sceduler = sceduler;
        this.threadId = threadId;
    }

    @Override
    public void run() {
        this.sceduler.signSceduler(Thread.currentThread().getId()+"");
        for (int i =0; i < 5; i++) {
            sceduler.addMessage(this.threadId, "Message " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        sceduler.addMessage(this.threadId, "DONE");
    }
}
