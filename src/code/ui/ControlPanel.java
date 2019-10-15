package code.ui;

import code.exceptions.BackpackDontHaveTwoDimensionalShapesException;

import javax.swing.*;

public class ControlPanel extends JPanel {
    public ControlPanel(BackPackElementsPanel elementsPanel) {
        JButton addButton = new JButton("Add new shape");
        addButton.addActionListener(event -> {
            JDialog addFrame = new AddShapeDialog(elementsPanel);
            addFrame.toFront();
            addFrame.setVisible(true);
        });

        JButton removeButton = new JButton("Remove shape");
        removeButton.addActionListener(event -> {
            JDialog removeDialog = new RemoveShapeDialog(elementsPanel);
            removeDialog.toFront();
            removeDialog.setVisible(true);
        });

        JButton transformButton = new JButton("Transform");
        transformButton.addActionListener(event -> {
            try {
                JDialog transformDialog = new TransformDialog(elementsPanel);
                transformDialog.toFront();
                transformDialog.setVisible(true);
            } catch (BackpackDontHaveTwoDimensionalShapesException exc) {
                JOptionPane.showMessageDialog(null,
                        "You don't have two dimensional shapes to transform them!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton clearButton = new JButton("Clear backpack");
        clearButton.addActionListener(event -> {
            int answer = JOptionPane.showConfirmDialog(null, "Are you sure?", "Confirm",
                    JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                elementsPanel.clear();
            }
        });

        add(addButton);
        add(removeButton);
        add(transformButton);
        add(clearButton);
    }
}
