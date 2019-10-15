package code.ui;

import javax.swing.*;
import java.awt.*;

public class RemoveShapeDialog extends JDialog {
    public RemoveShapeDialog(BackPackElementsPanel panel) {
        GridLayout layout = new GridLayout();
        layout.setColumns(1);
        layout.setRows(3);

        setLayout(layout);
        setName("Remove shape");

        Dimension screenSize = getToolkit().getScreenSize();
        setBounds(screenSize.width / 3, screenSize.height / 3,
                screenSize.width / 6, screenSize.height / 6);
        JLabel label = new JLabel("Index of shape you want to remove");
        JTextField indexField = new JTextField();
        label.setLabelFor(indexField);
        indexField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JButton removeButton = new JButton("remove");
        removeButton.addActionListener(event -> {
            try {
                panel.removeShape(Integer.parseInt(indexField.getText()));
                JOptionPane.showMessageDialog(null, "Shape was successfully removed!",
                        "Information", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Something went wrong! Check your input.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(label);
        add(indexField);
        add(removeButton);
    }
}
