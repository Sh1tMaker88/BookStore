package senla_courses.java.lection_3_task_3;

public class Temples implements IProductPart{
    String material;

    public Temples(String material) {
        this.material = material;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    @Override
    public String toString() {
        return "material='" + material + '\'';
    }
}
