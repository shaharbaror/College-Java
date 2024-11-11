package Sceduler;

public class Receiver extends Thread{
    private final Sceduler sceduler;
    public Receiver(Sceduler sceduler){
        this.sceduler = sceduler;
    }

    @Override
    public void run() {
        String message;
        this.sceduler.signSceduler(Thread.currentThread().getId() + "");
        while (!(message = sceduler.getMessage(Thread.currentThread().getId()+"")).equals("DONE")) {
            System.out.println("Received: " + message);
            try {
                Thread.sleep(1000); // Simulate time taken to consume a message
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("Done receiving messages");
    }
}
