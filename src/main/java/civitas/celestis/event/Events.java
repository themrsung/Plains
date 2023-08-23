package civitas.celestis.event;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains utility methods related to {@link Event}s.
 */
public final class Events {
    //
    // Cause Tracing
    //

    /**
     * Given an event, this prints its serialized form, followed by a
     * list of causes in the temporal order ascending.
     *
     * @param printStream The print stream of which to print to
     * @param event The event of which to find the causes of and print
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
