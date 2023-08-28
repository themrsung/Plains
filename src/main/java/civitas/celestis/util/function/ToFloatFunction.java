package civitas.celestis.util.function;

@FunctionalInterface
public interface ToFloatFunction<T> {
    float applyAsFloat(T t);
}
