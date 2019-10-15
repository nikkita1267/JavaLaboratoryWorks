package code.containers;

import code.objects.Shape;
import code.exceptions.BackpackOverflowException;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class Backpack {
    private List<Shape> items = new ArrayList<>();
    private double packVolume;
    private double summaryFiguresVolume;

    public Backpack(double packVolume) {
        this.packVolume = packVolume;
        summaryFiguresVolume = 0;
    }

    public void add(Shape figure) {
        summaryFiguresVolume += figure.getVolume();
        if (summaryFiguresVolume > packVolume) {
            throw new BackpackOverflowException();
        } else {
            items.add(figure);
            Collections.sort(items);
        }
    }

    public void remove(int index) {
        summaryFiguresVolume -= items.get(index).getVolume();
        items.remove(index);
    }

    public int size() {
        return items.size();
    }

    public void showItems(Writer writer) {
        for (Shape item : items) {
            System.out.println(item.getVolume());
        }

        items.forEach(shape -> {
            try {
                writer.write(shape.toString());
                writer.write('\n');
            } catch (IOException exc) {
                throw new RuntimeException(exc);
            }
        });
    }

    public void showItems() {
        showItems(new OutputStreamWriter(System.out));
    }

    public void clear() {
        items.clear();
        summaryFiguresVolume = 0;
    }

    public void remove(Shape shape) {
        for (Shape item : items) {
            if (item.toString().equals(shape.toString())) {
                summaryFiguresVolume -= item.getVolume();
                break;
            }
        }
        items.remove(shape);
    }

    public Stream<Shape> stream() {
        return items.stream();
    }
}
