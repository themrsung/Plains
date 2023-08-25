package civitas.celestis.exception.gpu;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * A graphics exception is thrown when a GPU accelerated operation
 * was unable to complete successfully.
 */
public class GraphicsException extends RuntimeException {
    /**
     * Creates a new graphics exception with no cause and no message.
     */
    public GraphicsException() {
    }

    /**
     * Creates a new graphics exception with no cause.
     *
     * @param message The message of this exception
     */
    public GraphicsException(@Nonnull String message) {
        super(message);
    }

    /**
     * Creates a new graphics exception.
     *
     * @param message The message of this exception
     * @param cause   The cause of this exception
     */
    public GraphicsException(@Nonnull String message, @Nullable Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new graphics exception with no message.
     *
     * @param cause The cause of this exception
     */
    public GraphicsException(@Nullable Throwable cause) {
        super(cause);
    }
}
