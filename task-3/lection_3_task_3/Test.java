package senla_courses.java.lection_3_task_3;

public class Test {
    public static void main(String[] args) {
        IProduct glasses = new Glasses();
        BuildPart_1 frame = new BuildPart_1();
        BuildPart_2 lenses = new BuildPart_2();
        BuildPart_3 temples = new BuildPart_3();
        AssemblyLine glassesLine = new AssemblyLine(frame, lenses, temples);

        glasses = glassesLine.assembleProduct(glasses);
        System.out.println(glasses);
    }
}
