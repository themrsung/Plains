/**
 * <h2>Math</h2>
 * <p>
 * This package is a comprehensive mathematical library containing
 * {@link civitas.celestis.math.vector.Vector vectors}, {@link civitas.celestis.math.matrix.Matrix matrices},
 * and other mathematical classes required to build a math-intensive application.
 * </p>
 * <p>
 * All classes within this package are designed to be mathematically accurate
 * without sacrificing performance. Practically is also considered in methods such as
 * {@link civitas.celestis.math.vector.Vector#normalizeOrZero()} where an exceptional
 * case which will cause division by zero is handled by returning a fallback value of zero.
 * </p>
 *
 * @see civitas.celestis.math.Numbers
 * @see civitas.celestis.math.vector.Vector
 * @see civitas.celestis.math.matrix.NumericGrid
 */
package civitas.celestis.math;