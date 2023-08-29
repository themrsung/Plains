package civitas.celestis.event;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains utility methods related to {@link Event}s.
 */
public final class Events {
    //
    // Serialization
    //

    /**
     * Serializes an event into its standardized form.
     *
     * @param event The event of which to serialize
     * @return The serialized form of the event
     */
    @Nonnull
    public static String toString(@Nullable Handleable event) {
        if (event == null) return "null";

        /*
         * DO NOT CALL Handleable.toString() ANYWHERE IN THIS METHOD.
         * DOING SO WILL RESULT IN AN INFINITE LOOP.
         */

        final StringBuilder result = new StringBuilder(event.getClass().getSimpleName());
        result.append("{")
                .append("uuid=").append(event.getUniqueId()).append(", ")
                .append("cause=");

        final Handleable cause = event.getCause();
        result.append(cause != null ? cause.getUniqueId() : "null");

        for (final Field field : event.getClass().getDeclaredFields()) {
            final String name = field.getName();
            if (name.equals("uuid") || name.equals("cause")) continue;

            result.append(", ").append(name).append("=");

            try {
                result.append(field.get(event));
            } catch (final IllegalAccessException ignored) {
                final String getterName;

                // Contextual getter search for booleans
                if (field.getType() == boolean.class || field.getType() == Boolean.class) {
                    getterName = "is" +
                            Character.toUpperCase(name.charAt(0)) +
                            name.substring(1);
                } else {
                    getterName = "get" +
                            Character.toUpperCase(name.charAt(0)) +
                            name.substring(1);
                }

                final Method getter;

                try {
                    getter = event.getClass().getMethod(getterName);
                } catch (final NoSuchMethodException e) {
                    result.append("getter not found (").append(e.getMessage()).append(")");
                    continue;
                }

                try {
                    final Object value = getter.invoke(event);
                    result.append(value instanceof String string ?
                            "'" + string + "'" :
                            value);

                } catch (final IllegalAccessException e) {
                    result.append("inaccessible (non-public)");
                } catch (final InvocationTargetException e) {
                    result.append("error (").append(e.getMessage()).append(")");
                }
            }
        }

        return result.append("}").toString();
    }

    //
    // Cause Tracing
    //

    /**
     * Given an event, this prints its serialized form, followed by a
     * list of causes in the temporal order ascending.
     *
     * @param printStream The print stream of which to print to
     * @param event       The event of which to find the causes of and print
     */
    public static void printEventCauseTrace(@Nonnull PrintStream printStream, @Nullable Handleable event) {
        if (event == null) {
            printStream.println("Event: null");
            return;
        }

        // Print the event's serialized form
        printStream.println("Event: " + event);

        // Declare list of causes
        final List<Handleable> causes = new ArrayList<>();

        // Find all causes
        Handleable current = event;
        while (current != null) {
            causes.add(current);
            current = current.getCause();
        }

        // Print list header
        printStream.println("Causes:");

        // Print all causes in temporal order
        for (int i = (causes.size() - 1); i >= 0; i--) {
            final Handleable cause = causes.get(i);
            printStream.println("- " + cause);
        }
    }
}
