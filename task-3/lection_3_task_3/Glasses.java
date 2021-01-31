package senla_courses.java.lection_3_task_3;

public class Glasses implements IProduct{

    private IProductPart frame;
    private IProductPart lenses;
    private IProductPart temples;

    @Override
    public void installFirstPart(IProductPart part1) {
        if (part1 instanceof Frame){
            setFrame(part1);
        }
    }

    @Override
    public void installSecondPart(IProductPart part2) {
        if (part2 instanceof Lenses){
            setLenses(part2);
        }
    }

    @Override
    public void installThirdPart(IProductPart part3) {
        if (part3 instanceof Temples){
            setTemples(part3);
        }
    }

    public IProductPart getFrame() {
        return frame;
    }

    public void setFrame(IProductPart frame) {
        this.frame = frame;
    }

    public IProductPart getLenses() {
        return lenses;
    }

    public void setLenses(IProductPart lenses) {
        this.lenses = lenses;
    }

    public IProductPart getTemples() {
        return temples;
    }

    public void setTemples(IProductPart temples) {
        this.temples = temples;
    }

    @Override
    public String toString() {
        return "Glasses{" +
                "frame: " + frame +
                ", lenses: " + lenses +
                ", temples: " + temples +
                '}';
    }
}
