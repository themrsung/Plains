package civitas.celestis.gpu;

import civitas.celestis.exception.gpu.GraphicsException;
import civitas.celestis.math.complex.Quaternion;
import civitas.celestis.math.vector.Vector2;
import civitas.celestis.math.vector.Vector3;
import civitas.celestis.math.vector.Vector4;
import jakarta.annotation.Nonnull;

/**
 * A utility class to simplify mathematical operations using the {@link GPU} interface.
 * This class is designed to be instantiated on a per-thread basis. Thus, there is no
 * synchronization built in. In other words, <b>this class is not thread-safe.</b>
 * @see GPU
 */
public final class GPUMath {
    //
    // Constructors
    //

    /**
     * Creates a new {@link GPUMath} instance.
     * @throws GraphicsException When the {@link GPU} interface has not yet been initialized
     */
    public GPUMath() throws GraphicsException {
        this.addition = GPU.createKernel(Kernels.ADD_DOUBLES);
        this.subtraction = GPU.createKernel(Kernels.SUBTRACT_DOUBLES);
        this.multiplication = GPU.createKernel(Kernels.MULTIPLY_DOUBLES);
        this.division = GPU.createKernel(Kernels.DIVIDE_DOUBLES);
        this.sqrt = GPU.createKernel(Kernels.SQRT_DOUBLE);
    }

    //
    // Disposal
    //

    /**
     * Disposes this {@link GPUMath} instance, releasing its resources
     * and making it no longer functional.
     */
    public void dispose() {

        /*
         * Disposal should be in the reverse order of acquisition.
         */

        sqrt.dispose();
        division.dispose();
        multiplication.dispose();
        subtraction.dispose();
        addition.dispose();
    }

    //
    // Operations
    //

    /**
     * Adds two values together.
     * @param v1 The first value to add
     * @param v2 The second value to add
     * @return The sum of the two values
     */
    public double add(double v1, double v2) {
        final double[] result = new double[1];

        addition.setInput(0, v1)
                .setInput(1, v2)
                .setOutput(2, result)
                .execute(1)
                .readOutput(2, result);

        return result[0];
    }

    /**
     * Subtracts two values.
     * @param v1 The first value to subtract
     * @param v2 The second value to subtract
     * @return The difference of the two values
     */
    public double subtract(double v1, double v2) {
        final double[] result = new double[1];

        subtraction.setInput(0, v1)
                .setInput(1, v2)
                .setOutput(2, result)
                .execute(1)
                .readOutput(2, result);

        return result[0];
    }

    /**
     * Multiplies two values.
     * @param v1 The first value to multiply
     * @param v2 The second value to multiply
     * @return The product of the two values
     */
    public double multiply(double v1, double v2) {
        final double[] result = new double[1];

        multiplication.setInput(0, v1)
                .setInput(1, v2)
                .setOutput(2, result)
                .execute(1)
                .readOutput(2, result);

        return result[0];
    }

    /**
     * Divides two values.
     * @param v1 The numerator of this operation
     * @param v2 The denominator of this operation
     * @return The quotient of the two values
     * @throws ArithmeticException When the denominator is zero
     */
    public double divide(double v1, double v2) throws ArithmeticException {
        if (v2 == 0) {

            /*
             * The GPU kernel cannot reliably handle this exception.
             */

            throw new ArithmeticException("Cannot divide by zero.");
        }

        final double[] result = new double[1];

        division.setInput(0, v1)
                .setInput(1, v2)
                .setOutput(2, result)
                .execute(1)
                .readOutput(2, result);

        return result[0];
    }

    /**
     * Returns the square root of the provided value.
     * @param v1 The value to get the square root of
     * @return The square root of the provided value
     */
    public double sqrt(double v1) {
        final double[] result = new double[1];

        sqrt.setInput(0, v1)
            .setOutput(1, result)
            .execute(1)
            .readOutput(1, result);

        return result[0];
    }

    /**
     * Normalizes the provided vector {@code v} to have a Euclidean norm (magnitude) of {@code 1}.
     * @param v The vector of which to normalize
     * @return The normalized vector
     * @throws ArithmeticException When the Euclidean norm (the magnitude) is zero
     */
    @Nonnull
    public Vector2 normalize(@Nonnull Vector2 v) throws ArithmeticException {
        final double x = v.x();
        final double y = v.y();

        final double norm = sqrt(x * x + y * y);
        return new Vector2(x / norm, y / norm);
    }

    /**
     * Normalizes the provided vector {@code v} to have a Euclidean norm (magnitude) of {@code 1}.
     * @param v The vector of which to normalize
     * @return The normalized vector
     * @throws ArithmeticException When the Euclidean norm (the magnitude) is zero
     */
    @Nonnull
    public Vector3 normalize(@Nonnull Vector3 v) throws ArithmeticException {
        final double x = v.x();
        final double y = v.y();
        final double z = v.z();

        final double norm = sqrt(x * x + y * y + z * z);
        return new Vector3(x / norm, y / norm, z / norm);
    }

    /**
     * Normalizes the provided vector {@code v} to have a Euclidean norm (magnitude) of {@code 1}.
     * @param v The vector of which to normalize
     * @return The normalized vector
     * @throws ArithmeticException When the Euclidean norm (the magnitude) is zero
     */
    @Nonnull
    public Vector4 normalize(@Nonnull Vector4 v) throws ArithmeticException {
        final double w = v.w();
        final double x = v.x();
        final double y = v.y();
        final double z = v.z();

        final double norm = sqrt(w * w + x * x + y * y + z * z);

        return new Vector4(w / norm, x / norm, y / norm, z / norm);
    }

    /**
     * Normalizes the provided quaternion {@code q} to have a Euclidean norm (magnitude) of {@code 1}.
     * @param q The quaternion of which to normalize
     * @return The normalized quaternion
     * @throws ArithmeticException When the Euclidean norm (the magnitude) is zero
     */
    @Nonnull
    public Quaternion normalize(@Nonnull Quaternion q) throws ArithmeticException {
        final double w = q.w();
        final double x = q.x();
        final double y = q.y();
        final double z = q.z();

        final double norm = sqrt(w * w + x * x + y * y + z * z);

        return new Quaternion(w / norm, x / norm, y / norm, z / norm);
    }

    //
    // Kernels
    //

    /**
     * The kernel which handles addition.
     */
    @Nonnull
    private final KernelReference addition;

    /**
     * The kernel which handles subtraction.
     */
    @Nonnull
    private final KernelReference subtraction;

    /**
     * The kernel which handles multiplication.
     */
    @Nonnull
    private final KernelReference multiplication;

    /**
     * The kernel which handles division.
     */
    @Nonnull
    private final KernelReference division;

    /**
     * The kernel which handles square roots.
     */
    @Nonnull
    private final KernelReference sqrt;
}
