package civitas.celestis.gpu;

/**
 * Contains predefined kernels.
 */
public final class Kernels {
    /**
     * A program which takes two {@code double}s and outputs the sum. ({@code a + b})
     * <ul>
     *     <li>Parameter count: 3</li>
     *     <li>Parameter 0: {@code double[]} of length {@code 1} (input A)</li>
     *     <li>Parameter 1: {@code double[]} of length {@code 1} (input B)</li>
     *     <li>Parameter 2: {@code double[]} of length {@code 1} (output)</li>
     *     <li>Work size: {@code 1}</li>
     * </ul>
     */
    public static final KernelSource ADD_DOUBLES;

    /**
     * A program which takes two {@code double}s and outputs the difference. ({@code a - b})
     * <ul>
     *     <li>Parameter count: 3</li>
     *     <li>Parameter 0: {@code double[]} of length {@code 1} (input A)</li>
     *     <li>Parameter 1: {@code double[]} of length {@code 1} (input B)</li>
     *     <li>Parameter 2: {@code double[]} of length {@code 1} (output)</li>
     *     <li>Work size: {@code 1}</li>
     * </ul>
     */
    public static final KernelSource SUBTRACT_DOUBLES;

    /**
     * A program which takes two {@code double}s and outputs the product. ({@code a * b})
     * <ul>
     *     <li>Parameter count: 3</li>
     *     <li>Parameter 0: {@code double[]} of length {@code 1} (input A)</li>
     *     <li>Parameter 1: {@code double[]} of length {@code 1} (input B)</li>
     *     <li>Parameter 2: {@code double[]} of length {@code 1} (output)</li>
     *     <li>Work size: {@code 1}</li>
     * </ul>
     */
    public static final KernelSource MULTIPLY_DOUBLES;

    /**
     * A program which takes two {@code double}s and outputs the quotient. ({@code a / b})
     * <ul>
     *     <li>Parameter count: 3</li>
     *     <li>Parameter 0: {@code double[]} of length {@code 1} (input A)</li>
     *     <li>Parameter 1: {@code double[]} of length {@code 1} (input B)</li>
     *     <li>Parameter 2: {@code double[]} of length {@code 1} (output)</li>
     *     <li>Work size: {@code 1}</li>
     * </ul>
     */
    public static final KernelSource DIVIDE_DOUBLES;

    /**
     * A program which takes one {@code double} and outputs the square root. ({@code sqrt(a)})
     * <ul>
     *     <li>Parameter count: 2</li>
     *     <li>Parameter 0: {@code double[]} of length {@code 1} (input A)</li>
     *     <li>Parameter 1: {@code double[]} of length {@code 1} (output)</li>
     *     <li>Work size: {@code 1}</li>
     * </ul>
     */
    public static final KernelSource SQRT_DOUBLE;

    static {
        ADD_DOUBLES = new KernelSource(
                "__kernel void addDoubles(__global const double *a, __global const double *b, __global double *result) {" +
                        "    int idx = get_global_id(0);" +
                        "    result[idx] = a[idx] + b[idx];" +
                        "}",
                "addDoubles",
                3
        );

        SUBTRACT_DOUBLES = new KernelSource(
                "__kernel void subtractDoubles(__global const double *a, __global const double *b, __global double *result) {" +
                        "    int idx = get_global_id(0);" +
                        "    result[idx] = a[idx] - b[idx];" +
                        "}",
                "subtractDoubles",
                3
        );

        MULTIPLY_DOUBLES = new KernelSource(
                "__kernel void multiplyDoubles(__global const double *a, __global const double *b, __global double *result) {" +
                        "    int idx = get_global_id(0);" +
                        "    result[idx] = a[idx] * b[idx];" +
                        "}",
                "multiplyDoubles",
                3
        );

        DIVIDE_DOUBLES = new KernelSource(
                "__kernel void divideDoubles(__global const double *a, __global const double *b, __global double *result) {" +
                        "    int idx = get_global_id(0);" +
                        "    result[idx] = a[idx] / b[idx];" +
                        "}",
                "divideDoubles",
                3
        );

        SQRT_DOUBLE = new KernelSource(
                "__kernel void sqrtDouble(__global const double *a, __global double *result) {" +
                        "    int idx = get_global_id(0);" +
                        "    result[idx] = sqrt(a[idx]);" +
                        "}",
                "sqrtDouble",
                2
        );
    }

    /**
     * A program which takes one {@code double[]} and outputs the normalized array.
     * <ul>
     *     <li>Parameter count: 2</li>
     *     <li>Parameter 0: {@code double[]} of length {@code 2} (input A)</li>
     *     <li>Parameter 1: {@code double[]} of length {@code 2} (output)</li>
     *     <li>Work size: {@code 2}</li>
     * </ul>
     */
    public static final KernelSource NORMALIZE_VECTOR_2;

    /**
     * A program which takes one {@code double[]} and outputs the normalized array.
     * <ul>
     *     <li>Parameter count: 2</li>
     *     <li>Parameter 0: {@code double[]} of length {@code 3} (input A)</li>
     *     <li>Parameter 1: {@code double[]} of length {@code 3} (output)</li>
     *     <li>Work size: {@code 3}</li>
     * </ul>
     */
    public static final KernelSource NORMALIZE_VECTOR_3;

    /**
     * A program which takes one {@code double[]} and outputs the normalized array.
     * <ul>
     *     <li>Parameter count: 2</li>
     *     <li>Parameter 0: {@code double[]} of length {@code 4} (input A)</li>
     *     <li>Parameter 1: {@code double[]} of length {@code 4} (output)</li>
     *     <li>Work size: {@code 4}</li>
     * </ul>
     */
    public static final KernelSource NORMALIZE_VECTOR_4;

    static {
        NORMALIZE_VECTOR_2 = new KernelSource(
                "__kernel void normalizeVector2(__global const double *a, __global double *result) {" +
                        "    double n = sqrt(a[0] * a[0] + a[1] * a[1]);" +
                        "    result[0] = a[0] / n;" +
                        "    result[1] = a[1] / n;" +
                        "}",
                "normalizeVector2",
                2
        );

        NORMALIZE_VECTOR_3 = new KernelSource(
                "__kernel void normalizeVector3(__global const double *a, __global double *result) {" +
                        "    double n = sqrt(a[0] * a[0] + a[1] * a[1] + a[2] * a[2]);" +
                        "    result[0] = a[0] / n;" +
                        "    result[1] = a[1] / n;" +
                        "    result[2] = a[2] / n;" +
                        "}",
                "normalizeVector3",
                2
        );

        NORMALIZE_VECTOR_4 = new KernelSource(
                "__kernel void normalizeVector4(__global const double *a, __global double *result) {" +
                        "    double n = sqrt(a[0] * a[0] + a[1] * a[1] + a[2] * a[2]);" +
                        "    result[0] = a[0] / n;" +
                        "    result[1] = a[1] / n;" +
                        "    result[2] = a[2] / n;" +
                        "    result[3] = a[3] / n;" +
                        "}",
                "normalizeVector4",
                2
        );
    }

    /**
     * Kernel used for debugging.
     */
    public static final KernelSource DEBUG_1;

    static {
        DEBUG_1 = new KernelSource(
                "__kernel void debug1(__global const double *a, __global double *result, __global double *debug) {" +
                        "    double n = sqrt(a[0] * a[0] + a[1] * a[1] + a[2] * a[2]);" +
                        "    result[0] = a[0] / n;" +
                        "    result[1] = a[1] / n;" +
                        "    result[2] = a[2] / n;" +
                        "    debug[0] = (double)(sizeof(a) / sizeof(a[0]));" +
                        "}",
                "debug1",
                3
        );
    }
}
