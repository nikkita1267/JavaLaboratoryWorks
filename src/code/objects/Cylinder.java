package code.objects;

public class Cylinder extends ThreeDimensionalShape {
    private double radius;
    private double height;

    public Cylinder() {
        this(0, 0);
    }

    public Cylinder(double radius, double height) {
        super(Math.PI * radius * radius * height);
        this.radius = radius;
        this.height = height;
    }

    @Override
    public double calculateSurfaceArea() {
        return 2 * Math.PI * radius * (height + radius);
    }

    @Override
    public String toString() {
        return "Cylinder with " +
                "radius=" + radius +
                " and height=" + height;
    }
}
