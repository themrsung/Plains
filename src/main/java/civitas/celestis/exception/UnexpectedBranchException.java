package civitas.celestis.exception;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * An exception thrown when a theoretically unreachable branch of code
 * has been reached by means of an unknown cause. For example, this is can be
 * thrown by a {@code switch} expression for a value which is known to only
 * return {@code 0} or {@code 1}, but requires a {@code default} branch
 * in order to compile. This exception should theoretically never be encountered.
 */
public class UnexpectedBranchException extends RuntimeException {
    /**
     * Creates a new unexpected exception with no message and no cause.
     */
    public UnexpectedBranchException() {
    }

    /**
     * Creates a new unexpected exception with no cause.
     *
     * @param message The message of this exception
     */
    public UnexpectedBranchException(@Nonnull String message) {
        super(message);
    }

    /**
     * Creates a new unexpected exception.
     *
     * @param message The message of this exception
     * @param cause   The cause of this exception
     */
    public UnexpectedBranchException(@Nonnull String message, @Nullable Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new unexpected exception with no message.
     *
     * @param cause The cause of this exception
     */
    public UnexpectedBranchException(@Nullable Throwable cause) {
        super(cause);
    }
}
