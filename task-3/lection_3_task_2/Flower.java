package senla_courses.java.lection_3_task_2;

public abstract class Flower {

    private double price;
    private Colors colour;

    public Flower(double price, Colors colour) {
        this.price = price;
        this.colour = colour;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Colors getColour() {
        return colour;
    }

    public void setColour(Colors colour) {
        this.colour = colour;
    }

    @Override
    public String toString() {
        return "{" +
                "price=" + price +
                ", color=" + colour +
                '}';
    }
}
