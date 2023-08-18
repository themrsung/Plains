package civitas.celestis.math.atomic;

import java.util.concurrent.atomic.AtomicReference;

/**
 * An atomic 64-bit double precision {@code double}.
 */
public class AtomicDouble extends AtomicReference<Double> {
    //
    // Constructors
    //

    /**
     * Creates a new atomic double.
     *
     * @param initialValue The initial value of this atomic double
     */
    public AtomicDouble(double initialValue) {
        super(initialValue);
    }

    /**
     * Creates a new atomic double. The initial value is {@code 0}.
     */
    public AtomicDouble() {
        super(0d);
    }

    //
    // Arithmetic
    //

    /**
     * Adds a scalar to this double.
     *
     * @param s The scalar to add to this double
     */
    public void add(double s) {
        getAndUpdate(v -> v + s);
    }

    /**
     * Subtracts a scalar from this double.
     *
     * @param s The scalar to subtract from this double
     */
    public void subtract(double s) {
        getAndUpdate(v -> v - s);
    }

    /**
     * Multiplies this double by a scalar.
     *
     * @param s The scalar to multiply this double by
     */
    public void multiply(double s) {
        getAndUpdate(v -> v * s);
    }

    /**
     * Divides this double by a scalar.
     *
     * @param s The scalar to divide this double by
     * @throws ArithmeticException When the denominator {@code s} is zero
     */
    public void divide(double s) throws ArithmeticException {
        if (s == 0) throw new ArithmeticException("Cannot divide by zero.");
        getAndUpdate(v -> v / s);
    }

    /**
     * Divides this double by a scalar, but allows division by zero.
     *
     * @param s The scalar to divide this double by
     */
    public void divideAllowZero(double s) {
        getAndUpdate(v -> v / s);
    }

    /**
     * Raises this double to the {@code e}th power.
     *
     * @param e The exponent of which to raise this double to
     */
    public void pow(double e) {
        getAndUpdate(v -> Math.pow(v, e));
    }

    /**
     * Updates the value of this double to the square root of itself.
     */
    public void sqrt() {
        getAndUpdate(Math::sqrt);
    }

    /**
     * Increments this double. Returns the current value, then adds {@code 1}.
     *
     * @return The current value of this double
     */
    public double increment() {
        return getAndUpdate(v -> v + 1);
    }

    /**
     * Decrements this double. Returns the current value, then subtracts {@code 1}.
     *
     * @return The current value of this double
     */
    public double decrement() {
        return getAndUpdate(v -> v - 1);
    }
}
