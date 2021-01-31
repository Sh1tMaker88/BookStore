package senla_courses.java.lection_3_task_3;

public class BuildPart_1 implements ILineStep{

    @Override
    public IProductPart buildProductPart() {
        IProductPart frame = new Frame("black");
        System.out.println("Frame has been created");
        return frame;
    }
}
