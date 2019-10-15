package code.ui;

import javax.swing.*;
import java.awt.*;

public class BackpackFrame extends JFrame {
    public BackpackFrame(double volume) throws HeadlessException {
        this("Backpack UI", volume);
    }

    public BackpackFrame(String title, double volume) throws HeadlessException {
        super(title);
        Dimension screenSize = getToolkit().getScreenSize();
        BackPackElementsPanel elementsPanel = new BackPackElementsPanel(volume);
        ControlPanel controlPanel = new ControlPanel(elementsPanel);
        setBounds(screenSize.width / 3, screenSize.height / 3,
                screenSize.width / 3, screenSize.height / 3);
        GridLayout layout = new GridLayout();
        setLayout(layout);
        layout.setColumns(2);
        add(elementsPanel);
        add(controlPanel);
    }
}
