package Task1;

public class Task1 {

    public static final Object toGetBlock = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new FirstThread());
        Thread thread2 = new Thread(new SecondThread());
        System.out.println("- " + thread1.getState());
        thread1.start();
        System.out.println("- " + thread1.getState());
        thread2.start();
        synchronized (toGetBlock) {
            SecondThread.justMethod();
        }
        System.out.println("- " + thread2.getState());
        System.out.println("- " + thread1.getState());
        Thread.sleep(500);
        System.out.println("- " + thread2.getState());
        Thread.sleep(800);
        System.out.println("- " + thread2.getState());
        thread1.stop();
    }
}

class FirstThread implements Runnable {

    @Override
    public synchronized void run() {
        try {
            int f = 0;
            while (f < 100) {
                f++;
                if (f == 50) {
                    wait();
                    f++;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class SecondThread implements Runnable {

    public static Object justMethod() throws InterruptedException {
        Thread.sleep(10);
        return Task1.toGetBlock;
    }

    @Override
    public void run() {
        synchronized (Task1.toGetBlock) {
            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
