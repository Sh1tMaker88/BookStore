package senla_courses.java.lection_3_task_3;

public class BuildPart_2 implements ILineStep{

    @Override
    public IProductPart buildProductPart() {
        IProductPart lenses = new Lenses("glass", 1.5);
        System.out.println("Lenses has been created");
        return lenses;
    }
}
