package code.ui;

import code.exceptions.BackpackOverflowException;
import code.util.ShapeClassHelper;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AddShapeDialog extends JDialog {
    public AddShapeDialog(BackPackElementsPanel elementsPanel) throws HeadlessException {
        GridLayout layout = new GridLayout();
        layout.setColumns(1);
        layout.setRows(2);
        setLayout(layout);
        setName("Add shape");

        List<String> availableShapes = ShapeClassHelper.getAllShapes("src/code/objects");
        List<String> visibleListOfShapes = availableShapes.stream()
                .map(name -> name.substring(name.lastIndexOf('\\') + 1)).collect(Collectors.toList());
        JList<String> shapesList = new JList<>();
        String[] shapesAsArray = new String[visibleListOfShapes.size()];
        shapesList.setListData(visibleListOfShapes.toArray(shapesAsArray));

        Dimension screenSize = getToolkit().getScreenSize();

        shapesList.setLayoutOrientation(JList.VERTICAL);
        JScrollPane shapesScroll = new JScrollPane(shapesList);
        JPanel parametersInputPanel = new JPanel();
        GridLayout parametersLayout = new GridLayout();
        parametersLayout.setRows(4);
        parametersLayout.setColumns(1);
        parametersInputPanel.setLayout(parametersLayout);
        shapesList.addListSelectionListener(event -> {
            String nameOfClass = availableShapes.get(shapesList.getSelectedIndex())
                    .replaceAll("\\\\", ".");
            Map<String, Class<?>> parameters = ShapeClassHelper
                    .getInputParameters(nameOfClass.substring(nameOfClass.indexOf('.') + 1));
//                this.remove(parametersInputPanel);
            parametersInputPanel.removeAll();
            revalidate();
            parameters.keySet().forEach(parameter -> {
                JLabel nameOfParameter = new JLabel(parameter);
                JTextField inputArea = new JTextField();
                nameOfParameter.setLabelFor(inputArea);
                inputArea.setBounds(parametersInputPanel.getX(), parametersInputPanel.getY(),
                        parametersInputPanel.getWidth(), parametersInputPanel.getHeight() / parameters.size());
                inputArea.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
                parametersInputPanel.add(nameOfParameter);
                parametersInputPanel.add(inputArea);
                revalidate();
            });

        });
        add(shapesScroll);
        add(parametersInputPanel);
        setBounds(screenSize.width / 4, screenSize.height / 4,
                screenSize.width / 4, screenSize.height / 4);

        JButton okButton = new JButton("Ok");
        okButton.addActionListener(event -> {
            String nameOfClass = availableShapes.get(shapesList.getSelectedIndex())
                    .replaceAll("\\\\", ".");
            Map<String, Class<?>> parameters = ShapeClassHelper
                    .getInputParameters(nameOfClass.substring(nameOfClass.indexOf('.') + 1));
            List<JTextField> fields = Arrays.stream(parametersInputPanel.getComponents())
                    .filter(component -> component instanceof JTextField) // I need only values from text fields
                    .map(component -> (JTextField) component)
                    .collect(Collectors.toList());
            code.objects.Shape shape;

            try {
                shape = (code.objects.Shape) Class.forName(nameOfClass.substring(nameOfClass.indexOf('.') + 1)).newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            assert shape != null;
            final int[] counter = {0};

            Class<?>[] paramsTypes = new Class<?>[parameters.keySet().size()];
            new ArrayList<>(parameters.values()).toArray(paramsTypes);

            try {
                shape = shape.getClass().getConstructor(paramsTypes).newInstance(
                        parameters.values().stream().map(clazz -> {
                            if (clazz.getName().toLowerCase().contains("int")) {
                                return Integer.parseInt(fields.get(counter[0]++).getText());
                            } else if (clazz.getName().toLowerCase().contains("double")) {
                                return Double.parseDouble(fields.get(counter[0]++).getText());
                            }
                            return null;
                        }).toArray()
                );
            } catch (Exception ignored) {}
            try {
                elementsPanel.addShape(shape);
            } catch (BackpackOverflowException overflow) {
                JOptionPane.showMessageDialog(null,
                        "You can't add this shape to your backpack because it's volume too large!",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
            dispose();
        });
        okButton.setPreferredSize(new Dimension(15, 5));
        add(okButton);
    }
}
