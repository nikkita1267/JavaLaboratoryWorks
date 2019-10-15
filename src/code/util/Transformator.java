package code.util;

import code.annotations.ConvertableTo;
import code.exceptions.NotConvertableException;
import code.objects.Shape;
import code.objects.ThreeDimensionalShape;
import code.objects.TwoDimensionalShape;

import java.util.function.Function;
import java.util.function.Supplier;

public class Transformator {
    public static <T extends TwoDimensionalShape, U extends ThreeDimensionalShape> ThreeDimensionalShape
    convert(T t,
            Function<? super T, ? extends ThreeDimensionalShape> transformer,
            Supplier<? extends ThreeDimensionalShape> instansiator) {
        Class<?> clazz = t.getClass();
        ConvertableTo convertableTo = clazz.getAnnotation(ConvertableTo.class);
        ThreeDimensionalShape targetType = instansiator.get();
        if (convertableTo != null) {
            for (Class<?> convertableClass : convertableTo.value()) {
                if (targetType.getClass().getCanonicalName().equals(convertableClass.getCanonicalName())) {
                    return transformer.apply(t);
                }
            }
        }

        throw new NotConvertableException("You cannot convert object of type " + t.getClass()
                + " to type " + targetType.getClass() + ". Maybe you forget to add it to @ConvertableTo");
    }
}
