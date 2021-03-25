package Task4;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task4 {
    public static void main(String[] args) throws InterruptedException {
//        LocalDateTime lc = LocalDateTime.now();
//        System.out.println(lc.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
//        Thread.sleep(2000);
//        LocalDateTime lc2 = LocalDateTime.now();
//        System.out.println(lc2.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        Thread thread = new Thread(new DaemonShowsTime(1));
        thread.setDaemon(true);
        thread.start();
        thread.sleep(100000);
    }
}

class DaemonShowsTime implements Runnable{

    int secondsToRefresh;

    DaemonShowsTime(int timeToSleep){
        this.secondsToRefresh = timeToSleep;
    }
    @Override
    public void run() {
        while (true) {
            LocalDateTime lc = LocalDateTime.now();
            System.out.println(lc.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            try {
                Thread.sleep(secondsToRefresh * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}