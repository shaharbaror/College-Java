package ThreadBox;

public class Consumer extends Thread {
    private Box box;

    public Consumer(Box box) {
        this.box = box;
    }

    @Override
    public void run() {
        String message;
        while (!(message = box.giveMessage()).equals("DONE")) {
            System.out.println("Consumed: " + message);
            try {
                Thread.sleep(1000); // Simulate time taken to consume a message
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}