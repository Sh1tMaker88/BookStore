package senla_courses.java.lection_3_task_3;

public class BuildPart_3 implements ILineStep {

    @Override
    public IProductPart buildProductPart() {
        IProductPart temples = new Temples("metal");
        System.out.println("Temples has been created");
        return temples;
    }
}
