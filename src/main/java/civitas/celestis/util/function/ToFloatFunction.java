package civitas.celestis.util.function;

@FunctionalInterface
public interface ToFloatFunction<T> {
    float apply(T t);
}
