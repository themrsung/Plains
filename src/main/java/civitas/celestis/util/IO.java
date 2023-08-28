package civitas.celestis.util;

import civitas.celestis.exception.IllegalInstanceException;
import jakarta.annotation.Nonnull;

import java.util.function.Function;

/**
 * Contains input-output utilities.
 */
public final class IO {
    //
    // Array Reading
    //

    /**
     * Reads an array of {@code double}s.
     *
     * @param s The string representation of the array
     * @return The parsed array
     * @throws NumberFormatException When the format is invalid
     */
    @Nonnull
    public static double[] readDoubles(@Nonnull String s) throws NumberFormatException {
        final String[] strings = s.replaceAll("[\\[\\] ]", "").split(",");
        final double[] doubles = new double[strings.length];

        for (int i = 0; i < strings.length; i++) {
            doubles[i] = Double.parseDouble(strings[i]);
        }

        return doubles;
    }

    /**
     * Reads an array of {@code double}s into an arbitrary type {@code T}.
     *
     * @param s           The string representation of {@code T}
     * @param constructor The constructor to use
     * @param <T>         The type to parse the {@code double}s into
     * @return The parsed {@code T}
     * @throws NumberFormatException When the format is invalid
     */
    @Nonnull
    public static <T> T readDoubles(@Nonnull String s, @Nonnull Function<double[], T> constructor)
            throws NumberFormatException {
        return constructor.apply(readDoubles(s));
    }

    /**
     * Reads an array of {@code float}s.
     *
     * @param s The string representation of the array
     * @return The parsed array
     * @throws NumberFormatException When the format is invalid
     */
    @Nonnull
    public static float[] readFloats(@Nonnull String s) throws NumberFormatException {
        final String[] strings = s.replaceAll("[\\[\\] ]", "").split(",");
        final float[] floats = new float[strings.length];

        for (int i = 0; i < strings.length; i++) {
            floats[i] = Float.parseFloat(strings[i]);
        }

        return floats;
    }

    /**
     * Reads an array of {@code float}s into an arbitrary type {@code T}.
     *
     * @param s           The string representation of {@code T}
     * @param constructor The constructor to use
     * @param <T>         The type to parse the {@code float}s into
     * @return The parsed {@code T}
     * @throws NumberFormatException When the format is invalid
     */
    @Nonnull
    public static <T> T readFloats(@Nonnull String s, @Nonnull Function<float[], T> constructor)
            throws NumberFormatException {
        return constructor.apply(readFloats(s));
    }

    /**
     * Reads an array of {@code long}s.
     *
     * @param s The string representation of the array
     * @return The parsed array
     * @throws NumberFormatException When the format is invalid
     */
    @Nonnull
    public static long[] readLongs(@Nonnull String s) throws NumberFormatException {
        final String[] strings = s.replaceAll("[\\[\\] ]", "").split(",");
        final long[] floats = new long[strings.length];

        for (int i = 0; i < strings.length; i++) {
            floats[i] = Long.parseLong(strings[i]);
        }

        return floats;
    }

    /**
     * Reads an array of {@code long}s into an arbitrary type {@code T}.
     *
     * @param s           The string representation of {@code T}
     * @param constructor The constructor to use
     * @param <T>         The type to parse the {@code long}s into
     * @return The parsed {@code T}
     * @throws NumberFormatException When the format is invalid
     */
    @Nonnull
    public static <T> T readLongs(@Nonnull String s, @Nonnull Function<long[], T> constructor)
            throws NumberFormatException {
        return constructor.apply(readLongs(s));
    }

    /**
     * Reads an array of {@code int}s.
     *
     * @param s The string representation of the array
     * @return The parsed array
     * @throws NumberFormatException When the format is invalid
     */
    @Nonnull
    public static int[] readInts(@Nonnull String s) throws NumberFormatException {
        final String[] strings = s.replaceAll("[\\[\\] ]", "").split(",");
        final int[] floats = new int[strings.length];

        for (int i = 0; i < strings.length; i++) {
            floats[i] = Integer.parseInt(strings[i]);
        }

        return floats;
    }

    /**
     * Reads an array of {@code int}s into an arbitrary type {@code T}.
     *
     * @param s           The string representation of {@code T}
     * @param constructor The constructor to use
     * @param <T>         The type to parse the {@code int}s into
     * @return The parsed {@code T}
     * @throws NumberFormatException When the format is invalid
     */
    @Nonnull
    public static <T> T readInts(@Nonnull String s, @Nonnull Function<int[], T> constructor)
            throws NumberFormatException {
        return constructor.apply(readInts(s));
    }

    //
    // Miscellaneous
    //

    /**
     * Private constructor to prevent instantiation.
     *
     * @throws IllegalInstanceException Always
     */
    private IO() throws IllegalInstanceException {
        throw new IllegalInstanceException(this);
    }
}
