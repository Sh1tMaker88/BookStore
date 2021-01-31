package senla_courses.java.lection_3_task_3;

public class Lenses implements IProductPart{
    String material;
    double index;

    public Lenses(String material, double index) {
        this.material = material;
        this.index = index;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public double getIndex() {
        return index;
    }

    public void setIndex(double index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "material='" + material + '\'' +
                ", index=" + index;
    }
}
