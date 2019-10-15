package code.objects;

import java.util.Objects;

abstract public class Shape implements Comparable<Shape> {
    private double volume;

    Shape(double volume)  {
        this.volume = volume;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    @Override
    public int compareTo(Shape o) {
        if (Math.abs(volume - o.volume) < 1e-6) {
            return 0;
        } else if (volume > o.volume) {
            return -1;
        } else  {
            return 1;
        }
    }

    @Override
    public String toString() {
        return "Shape{" +
                "volume=" + volume +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shape shape = (Shape) o;
        return Double.compare(shape.volume, volume) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(volume);
    }
}
