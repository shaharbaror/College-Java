package Sceduler;

public class Main {

    public static void main(String[] args) {
        Sceduler sceduler = new Sceduler();

        Receiver receiver = new Receiver(sceduler);
        Sender sender = new Sender(sceduler, receiver.getId() + "");

        receiver.start();
        sender.start();

        try {
            sender.join();
            receiver.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
