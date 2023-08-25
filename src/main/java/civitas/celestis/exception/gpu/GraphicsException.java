package civitas.celestis.exception.gpu;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * A graphics exception is thrown when a GPU accelerated operation
 * was unable to complete successfully.
 */
public class GraphicsException extends RuntimeException {
    public GraphicsException() {
    }

    public GraphicsException(@Nonnull String message) {
        super(message);
    }

    /**
     * Creates a new GPU exception.
     *
     * @param message The message of this exception
     * @param cause   The cause of this exception
     */
    public GraphicsException(@Nonnull String message, @Nullable Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new GPU exception.
     *
     * @param cause The cause of this exception
     */
    public GraphicsException(@Nullable Throwable cause) {
        super(cause);
    }
}
