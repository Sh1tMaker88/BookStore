package Task3;

public class Producer implements Runnable {

    Collector collector;

    Producer(Collector collector) {
        this.collector = collector;
    }

    @Override
    public void run() {
        for (int i = 0; i < 50; i++) {
            collector.addNumber();
        }
    }
}
