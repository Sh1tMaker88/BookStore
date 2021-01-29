package senla_courses.java.lection_3_task_2;

public class Lily extends Flower {
    public Lily(double price, Colors colour) {
        super(price, colour);
    }

    @Override
    public String toString() {
        return "Lily" + super.toString();
    }
}
