package code.objects;

public class Cube extends ThreeDimensionalShape {
    private double edge;

    public Cube() {
        this(0);
    }

    public Cube(double a) {
        super(a * a * a);
        this.edge = a;
    }

    public void setEdge(double edge) {
        this.edge = edge;
        setVolume(edge * edge * edge);
    }

    @Override
    public double calculateSurfaceArea() {
        return 6 * edge * edge;
    }

    @Override
    public String toString() {
        return "Cube with " +
                "edge=" + edge;
    }
}
