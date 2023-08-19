package civitas.celestis.util.io;

import jakarta.annotation.Nonnull;

/**
 * A reader class for deserializing various array-type data structures.
 */
public final class ArrayReader {
    //
    // Primitive
    //

    /**
     * Reads an array of primitive {@code double}s.
     *
     * @param input The input string to read
     * @return An array constructed from the input string
     * @throws NumberFormatException When the format is invalid
     */
    @Nonnull
    public static double[] readDoubleArray(@Nonnull String input) throws NumberFormatException {
        final String[] strings = input.trim().substring(1, input.length() - 1).split(", ");
        final double[] doubles = new double[strings.length];

        for (int i = 0; i < strings.length; i++) {
            doubles[i] = Double.parseDouble(strings[i]);
        }

        return doubles;
    }
}
