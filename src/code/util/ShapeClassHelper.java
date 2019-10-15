package code.util;

import code.annotations.ConvertableTo;
import code.objects.Shape;
import code.objects.TwoDimensionalShape;

import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class ShapeClassHelper {
    public static List<String> getAllShapes(String baseFolder) {
        try {
            return Files.list(Paths.get(baseFolder)).map(path -> {
                if (path.toFile().isDirectory()) {
                    try {
                        return Files.list(path)
                                .map(path2 -> path2.toFile().getParent() + "\\"
                                        + path2.toAbsolutePath().toFile().getName()).collect(Collectors.toList());
                    } catch (Exception ignored) {
                        return Collections.<String>emptyList();
                    }
                } else {
                    return Collections.singletonList(path.toFile().getParent() + "\\" + path.toFile().getName());
                }
            }).flatMap(Collection::stream).map(name -> name.substring(0, name.length() - 5))
                    .filter(name -> !name.contains("Shape")).collect(Collectors.toList());
        } catch (Exception ignored) {
            return Collections.emptyList();
        }
    }

    public static Map<String, Class<?>> getInputParameters(String nameOfShape) {
        try {
            return Arrays.stream(Class.forName(nameOfShape).getDeclaredFields())
                    .filter(field -> !field.isAccessible())
                    .collect(Collectors.toMap(Field::getName, Field::getType));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Class<?>> getConvertableToClasses(TwoDimensionalShape shape) {
        try {
            return Arrays.asList(shape.getClass().getAnnotation(ConvertableTo.class).value());
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
