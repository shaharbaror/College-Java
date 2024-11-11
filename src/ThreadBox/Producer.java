package ThreadBox;

public class Producer extends Thread {
    private Box box;

    public Producer(Box box) {
        this.box = box;
    }

    @Override
    public void run() {
        String[] messages = {
                "Message 1",
                "Message 2",
                "Message 3",
                "Message 4"
        };

        for (String message : messages) {
            box.receiveMessage(message);
            System.out.println("Produced: " + message);
            try {
                Thread.sleep(1000); // Simulate time taken to produce a message
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        box.receiveMessage("DONE");
    }
}