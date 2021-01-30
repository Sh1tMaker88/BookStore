package senla_courses.java.lection_3_task_2;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        Flower rose = new Rose(3.5, Colors.RED);
        Flower cornflower = new Cornflower(2.5, Colors.BLUE);
        Flower lily = new Lily(4.5, Colors.PURPLE);
        Flower violet = new Violet(4.0, Colors.PURPLE);
        Flower chamomile = new Chamomile(2.0, Colors.WHITE);
        Worker worker = new Worker();
        Bouquet bouq = new Bouquet();

        worker.collectBouquet(bouq, rose, cornflower, lily, violet, chamomile, rose);
        System.out.println(bouq.bouquet);
        worker.calculatePriceOfBouquet(bouq);
    }
}
