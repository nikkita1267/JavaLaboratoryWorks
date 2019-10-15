package code.util;

import code.objects.*;

import java.util.function.Function;
import java.util.function.Supplier;

public enum DefaultTransforms {
    CIRCLE_TO_SPHERE((Circle circle) -> new Sphere(circle.getRadius()), Sphere::new),
    CIRCLE_TO_CYLINDER((Circle circle) -> new Cylinder(circle.getRadius(), Math.random()), Cylinder::new),
    SQUARE_TO_CUBE((Square square) -> new Cube(square.getEdge()), Cube::new);

    <T extends TwoDimensionalShape, U extends ThreeDimensionalShape>
    DefaultTransforms(Function<? super T, ? extends U> rule,
                      Supplier<? extends ThreeDimensionalShape> instantiator) {
        this.rule = rule;
        this.instantiator = instantiator;
    }

    private Function rule;
    private Supplier<? extends ThreeDimensionalShape> instantiator;

    public Function getRule() {
        return rule;
    }

    public Supplier<? extends ThreeDimensionalShape> getInstantiator() {
        return instantiator;
    }
}
