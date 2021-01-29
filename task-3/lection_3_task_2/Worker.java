package senla_courses.java.lection_3_task_2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Worker {
    public static void main(String[] args) {
        Flower rose = new Rose(3.5, Colors.RED);
        Flower cornflower = new Cornflower(2.5, Colors.BLUE);
        Flower lily = new Lily(4.5, Colors.PURPLE);
        Flower violet = new Violet(4.0, Colors.PURPLE);
        Flower chamomile = new Chamomile(2.0, Colors.WHITE);

        List<Flower> list = new ArrayList<>();
        collectBouquet(list, rose, cornflower, lily, violet, chamomile, rose);
        Bouquet bouq = new Bouquet(list);
        System.out.println(list);

        calculatePriceOfBouquet(bouq);
    }

    public static void calculatePriceOfBouquet(Bouquet b){
        double sum = 0;
        for (Flower fl : b.bouquet){
            sum += fl.getPrice();
        }
        System.out.println("You collected bouquet from " + b.bouquet.size() + " flowers, and it's cost is " + sum + "$");
    }

    public static void collectBouquet(List<Flower> list, Flower... args){
        list.addAll(Arrays.asList(args));
    }
}
