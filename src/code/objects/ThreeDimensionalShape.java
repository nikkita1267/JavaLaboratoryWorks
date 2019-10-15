package code.objects;

public abstract class ThreeDimensionalShape extends Shape {
    public ThreeDimensionalShape(double volume) {
        super(volume);
    }

    abstract public double calculateSurfaceArea();
}
