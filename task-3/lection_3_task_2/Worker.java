package senla_courses.java.lection_3_task_2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Worker {

    public void calculatePriceOfBouquet(Bouquet b){
        double sum = 0;
        for (Flower fl : b.bouquet){
            sum += fl.getPrice();
        }
        System.out.println("You collected bouquet from " + b.bouquet.size() + " flowers, and it's cost is " + sum + "$");
    }

    public void collectBouquet(Bouquet b, Flower... args){
        b.bouquet = new ArrayList<>(Arrays.asList(args));
    }
}
