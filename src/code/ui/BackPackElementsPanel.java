package code.ui;

import code.objects.Shape;
import code.containers.Backpack;

import javax.swing.*;
import java.awt.*;
import java.io.StringWriter;

public class BackPackElementsPanel extends JPanel {
    private Backpack backpack;
    private JTextArea itemsArea = new JTextArea();

    public BackPackElementsPanel(double volume) {
        backpack = new Backpack(volume);
        itemsArea.setEnabled(false);
        itemsArea.setMinimumSize(new Dimension(getWidth(), getHeight()));
        itemsArea.setDisabledTextColor(Color.BLACK);
        itemsArea.setSelectedTextColor(Color.BLACK);
        itemsArea.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));

        add(itemsArea);
    }

    public void addShape(Shape shape) {
        backpack.add(shape);
        showItems();
    }

    public void removeShape(int index) {
        backpack.remove(index);
        showItems();
    }

    public void removeShape(Shape shape) {
        backpack.remove(shape);
        showItems();
    }

    public void clear() {
        backpack.clear();
        showItems();
    }

    public Backpack getBackpack() {
        return backpack;
    }

    private void showItems() {
        StringWriter itemsWriter = new StringWriter();
        backpack.showItems(itemsWriter);
        itemsArea.setText(itemsWriter.toString());
    }
}
