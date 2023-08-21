package civitas.celestis.util.io;

import jakarta.annotation.Nonnull;

/**
 * A utility class which deserializes string into arrays.
 */
public final class ArrayReader {
    //
    // Primitive Arrays
    //

    /**
     * Reads a serialized string and parses the values into a primitive array of {@code double}s.
     *
     * @param input The input string to parse
     * @return The parsed array
     * @throws NumberFormatException When an exception occurs during deserialization
     */
    @Nonnull
    public static double[] readDoubleArray(@Nonnull String input) throws NumberFormatException {
        input = input.trim();
        final String[] strings = input.substring(1, input.length() - 1).split(", ");
        final double[] values = new double[strings.length];

        for (int i = 0; i < strings.length; i++) {
            values[i] = Double.parseDouble(strings[i]);
        }

        return values;
    }
}