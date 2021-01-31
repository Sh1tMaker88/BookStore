package senla_courses.java.lection_3_task_3;

public class AssemblyLine implements IAssemblyLine{

    private ILineStep frameLineStep;
    private ILineStep lensesLineStep;
    private ILineStep templesLineStep;

    public AssemblyLine(ILineStep frameLineStep, ILineStep lensesLineStep, ILineStep templesLineStep) {
        this.frameLineStep = frameLineStep;
        this.lensesLineStep = lensesLineStep;
        this.templesLineStep = templesLineStep;
    }

    @Override
    public IProduct assembleProduct(IProduct product) {
        product.installFirstPart(frameLineStep.buildProductPart());
        System.out.println("Installed frame");
        product.installSecondPart(lensesLineStep.buildProductPart());
        System.out.println("Installed lenses");
        product.installThirdPart(templesLineStep.buildProductPart());
        System.out.println("Installed temples");

        return product;
    }

    public ILineStep getFrameLineStep() {
        return frameLineStep;
    }

    public void setFrameLineStep(ILineStep frameLineStep) {
        this.frameLineStep = frameLineStep;
    }

    public ILineStep getLensesLineStep() {
        return lensesLineStep;
    }

    public void setLensesLineStep(ILineStep lensesLineStep) {
        this.lensesLineStep = lensesLineStep;
    }

    public ILineStep getTemplesLineStep() {
        return templesLineStep;
    }

    public void setTemplesLineStep(ILineStep templesLineStep) {
        this.templesLineStep = templesLineStep;
    }
}
