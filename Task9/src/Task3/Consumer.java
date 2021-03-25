package Task3;

public class Consumer implements Runnable {
    Collector collector;

    Consumer(Collector collector) {
        this.collector = collector;
    }

    @Override
    public void run() {
        for (int i = 0; i < 50; i++) {
            collector.removeNumber();
        }
    }
}
