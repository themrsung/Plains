package civitas.celestis.exception.event;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.io.Serial;

/**
 * An exception which is thrown when the handling of an event fails,
 * either by the method being inaccessible, or by the handler method itself
 * throwing an exception during the processing of the event.
 */
public class HandlerException extends Exception {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 0L;

    //
    // Constructors
    //

    /**
     * Creates a new handler exception with no message and no cause.
     */
    public HandlerException() {
    }

    /**
     * Creates a new handler exception with no cause.
     *
     * @param message The message containing information about this exception
     */
    public HandlerException(@Nonnull String message) {
        super(message);
    }

    /**
     * Creates a new handler exception.
     *
     * @param message The message containing information about this exception
     * @param cause   The cause of this exception
     */
    public HandlerException(@Nonnull String message, @Nullable Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new handler exception with no message.
     *
     * @param cause The cause of this exception
     */
    public HandlerException(@Nullable Throwable cause) {
        super(cause);
    }
}
