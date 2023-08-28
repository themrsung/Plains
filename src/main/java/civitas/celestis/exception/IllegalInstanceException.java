package civitas.celestis.exception;

import jakarta.annotation.Nonnull;

/**
 * Thrown when an un-instantiable static-reference-only class is
 * attempted to be instantiated.
 */
public class IllegalInstanceException extends Exception {
    /**
     * Creates an illegal instance exception with no message.
     */
    public IllegalInstanceException() {
    }

    /**
     * Creates a new illegal instance exception.
     *
     * @param message The message of this exception
     */
    public IllegalInstanceException(@Nonnull String message) {
        super(message);
    }

    /**
     * Creates a new illegal instance exception.
     *
     * @param instance The illegal instance
     */
    public IllegalInstanceException(@Nonnull Object instance) {
        super(instance.getClass().getSimpleName() + " cannot be instantiated.");
    }

    /**
     * Creates a new illegal instance exception.
     *
     * @param instanceClass The illegal instance's class
     */
    public IllegalInstanceException(@Nonnull Class<?> instanceClass) {
        super(instanceClass.getSimpleName() + " cannot be instantiated.");
    }
}
