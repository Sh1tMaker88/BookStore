package senla_courses.java.lection_3_task_2;

public class Cornflower extends Flower{
    public Cornflower(double price, Colors colour) {
        super(price, colour);
    }

    @Override
    public String toString() {
        return "Cornflower" + super.toString();
    }
}
