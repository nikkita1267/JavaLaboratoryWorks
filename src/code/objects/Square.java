package code.objects;

import code.annotations.ConvertableTo;

@ConvertableTo(Cube.class)
public class Square extends TwoDimensionalShape {
    private double edge;

    public Square() {
        this(0);
    }

    public Square(double edge) {
        super(edge * edge);
        this.edge = edge;
    }

    public double getEdge() {
        return edge;
    }

    public void setEdge(double edge) {
        this.edge = edge;
    }

    @Override
    public String toString() {
        return "Square with " +
                "edge=" + edge;
    }
}
