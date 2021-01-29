package senla_courses.java.lection_3_task_2;

public class Violet extends Flower {
    public Violet(double price, Colors colour) {
        super(price, colour);
    }

    @Override
    public String toString() {
        return "Violet" + super.toString();
    }
}
