package civitas.celestis.util.function;

/**
 * A function which takes one {@code float} as its parameter, and returns no value.
 */
@FunctionalInterface
public interface FloatConsumer {
    /**
     * Accepts this consumer, executing its contents.
     *
     * @param value The value of which to accept this consumer as
     */
    void accept(float value);
}
