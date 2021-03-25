package Task3;

public class Task3 {
    public static void main(String[] args) {
        Collector collector = new Collector();
        Producer producer = new Producer(collector);
        Consumer consumer = new Consumer(collector);

        Thread thread1 = new Thread(producer);
        Thread thread2 = new Thread(consumer);
        thread1.start();
        thread2.start();
    }
}

