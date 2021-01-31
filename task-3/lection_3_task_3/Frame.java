package senla_courses.java.lection_3_task_3;

public class Frame implements IProductPart {
    String color;
    public Frame(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "color='" + color + '\'';
    }
}
