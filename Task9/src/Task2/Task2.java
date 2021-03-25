package Task2;

public class Task2 {
    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new NumberOne());
        Thread thread2 = new Thread(new NumberTwo());
        thread1.start();
        Thread.sleep(20);
        thread2.start();
    }
}

class NumberOne implements Runnable {

    @Override
    public void run() {
        Thread.currentThread().setName("First");
        for (int i = 0; i < 50; i++) {
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
            Thread.yield();
        }
    }
}

class NumberTwo implements Runnable {

    @Override
    public void run() {
        Thread.currentThread().setName("Second");
        for (int i = 0; i < 50; i++) {
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
        }
    }
}