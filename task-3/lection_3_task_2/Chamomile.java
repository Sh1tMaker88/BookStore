package senla_courses.java.lection_3_task_2;

public class Chamomile extends Flower {
    public Chamomile(double price, Colors colour) {
        super(price, colour);
    }

    @Override
    public String toString() {
        return "Chamomile" + super.toString();
    }
}
