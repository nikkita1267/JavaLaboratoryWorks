package code.objects;

import code.annotations.ConvertableTo;

@ConvertableTo(value = {Sphere.class, Cylinder.class})
public class Circle extends TwoDimensionalShape {
    private double radius;

    public Circle() {
        this(0);
    }

    public Circle(double radius) {
        super(Math.PI * radius * radius);
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "Circle with " +
                "radius=" + radius;
    }
}
