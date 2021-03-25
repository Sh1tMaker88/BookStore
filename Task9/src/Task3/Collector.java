package Task3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Collector {
    private List<Integer> list = new ArrayList<>();
    private final Object lock = new Object();
    private final Random random = new Random();

    public void addNumber() {
        synchronized (lock) {
            while (list.size() >= 15) {
                try {
                    System.out.println("List is full " + list);
                    lock.wait(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            int i = random.nextInt(100);
            System.out.println("Added '" + i + "'");
            list.add(i);
            lock.notify();
        }
    }

    public void removeNumber(){
        synchronized (lock) {
            while (list.size() == 0) {
                try {
                    System.out.println("List is empty, wait to get more numbers");
                    lock.wait(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            int i = random.nextInt(list.size());
            System.out.println("Removed '" + list.get(i) + "'");
            list.remove(i);
        }
    }
}
