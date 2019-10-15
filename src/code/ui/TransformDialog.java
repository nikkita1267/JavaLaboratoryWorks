package code.ui;

import code.exceptions.BackpackDontHaveTwoDimensionalShapesException;
import code.objects.ThreeDimensionalShape;
import code.objects.TwoDimensionalShape;
import code.util.DefaultTransforms;
import code.util.ShapeClassHelper;
import code.util.Transformator;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class TransformDialog extends JDialog {
    @SuppressWarnings("rawtypes")
    public TransformDialog(BackPackElementsPanel panel) {
        GridLayout layout = new GridLayout();
        layout.setRows(5);
        layout.setColumns(1);
        setLayout(layout);
        Dimension screenSize = getToolkit().getScreenSize();
        setBounds(screenSize.width / 3, screenSize.height / 3,
                screenSize.width / 3, screenSize.height / 3);

        JLabel fromLabel = new JLabel("From shape");
        JList<TwoDimensionalShape> elements = new JList<>();
        List<TwoDimensionalShape> twoDimensionalShapes = panel.getBackpack()
                .stream()
                .filter(shape -> shape instanceof TwoDimensionalShape)
                .map(shape -> (TwoDimensionalShape) shape)
                .collect(Collectors.toList());

        if (twoDimensionalShapes.isEmpty()) {
            throw new BackpackDontHaveTwoDimensionalShapesException();
        }

        System.out.println(twoDimensionalShapes.size());
        TwoDimensionalShape[] shapes = new TwoDimensionalShape[twoDimensionalShapes.size()];
        twoDimensionalShapes.toArray(shapes);
        elements.setListData(shapes);
        elements.setPreferredSize(new Dimension(100, 100));
        elements.setLayoutOrientation(JList.VERTICAL);
        JScrollPane elementsScroll = new JScrollPane(elements);

        fromLabel.setLabelFor(elementsScroll);
        add(fromLabel);
        add(elementsScroll);
        JLabel toLabel = new JLabel("To shape");
        JList<String> destinationShapes = new JList<>();
        elements.addListSelectionListener(event -> {
            List<Class<?>> convertableToClasses = ShapeClassHelper.getConvertableToClasses(elements.getSelectedValue());
            String[] threeDimensionalShapes = new String[convertableToClasses.size()];
            convertableToClasses.stream().map(Class::getSimpleName).collect(Collectors.toList()).toArray(threeDimensionalShapes);
            destinationShapes.setListData(threeDimensionalShapes);
        });

        destinationShapes.setLayoutOrientation(JList.VERTICAL);
        JScrollPane destinationShapesScroll = new JScrollPane(destinationShapes);
        toLabel.setLabelFor(destinationShapes);
        add(toLabel);
        add(destinationShapesScroll);

        JButton transform = new JButton("transform");
        transform.addActionListener(event -> {
            try {
                TwoDimensionalShape source = elements.getSelectedValue();
                List<Class<?>> convertableToClasses = ShapeClassHelper.getConvertableToClasses(elements.getSelectedValue());
                Class<?> targetType = convertableToClasses.get(destinationShapes.getSelectedIndex());
                ThreeDimensionalShape destination;
                System.err.println(source.getClass().getName());
                System.err.println(targetType.getName());
                if (source.getClass().getName().toLowerCase().contains("circle")) {
                    if (targetType.getName().toLowerCase().contains("sphere")) {
                        destination = Transformator.convert(source,
                                DefaultTransforms.CIRCLE_TO_SPHERE.getRule(),
                                DefaultTransforms.CIRCLE_TO_SPHERE.getInstantiator());
                    } else if (targetType.getName().toLowerCase().contains("cylinder")) {
                        destination = Transformator.convert(source,
                                DefaultTransforms.CIRCLE_TO_CYLINDER.getRule(),
                                DefaultTransforms.CIRCLE_TO_CYLINDER.getInstantiator());
                    } else {
                        throw new UnsupportedOperationException("Sorry, but this is not implemented yet");
                    }
                } else if (source.getClass().getName().toLowerCase().equals("square")) {
                    if (targetType.getName().toLowerCase().equals("cube")) {
                        destination = Transformator.convert(source,
                                DefaultTransforms.SQUARE_TO_CUBE.getRule(),
                                DefaultTransforms.SQUARE_TO_CUBE.getInstantiator());
                    } else {
                        throw new UnsupportedOperationException("Sorry, but this is not implemented yet");
                    }
                } else {
                    throw new UnsupportedOperationException("Sorry, but this is not implemented yet");
                }

                panel.removeShape(source);
                panel.addShape(destination);
                dispose();
            } catch (Exception e) {
                System.err.println(e.getMessage());
                JOptionPane.showMessageDialog(null, "Something went wrong! Change your input.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(transform);
    }
}
