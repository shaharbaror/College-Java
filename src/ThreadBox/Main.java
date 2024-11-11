package ThreadBox;

public class Main {
    public static void main(String[] args) {
        Box box = new Box();

        Producer producer = new Producer(box);
        Consumer consumer = new Consumer(box);

        producer.start();
        consumer.start();

        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}