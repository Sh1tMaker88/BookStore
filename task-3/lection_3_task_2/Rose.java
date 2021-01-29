package senla_courses.java.lection_3_task_2;

public class Rose extends Flower {
    public Rose(double price, Colors colour) {
        super(price, colour);
    }

    @Override
    public String toString() {
        return "Rose" + super.toString();
    }
}
