package civitas.celestis.exception;

import jakarta.annotation.Nonnull;

import java.io.Serial;

/**
 * This exception is thrown when an invalid index is used to query a tuple.
 */
public class TupleIndexOutOfBoundsException extends IndexOutOfBoundsException {
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
     * Creates a new exception with no message.
     */
    public TupleIndexOutOfBoundsException() {
    }

    /**
     * Creates a new exception.
     *
     * @param s The message of this exception
     */
    public TupleIndexOutOfBoundsException(@Nonnull String s) {
        super(s);
    }

    /**
     * Creates a new exception.
     *
     * @param index The illegal index
     */
    public TupleIndexOutOfBoundsException(int index) {
        super(index);
    }

    /**
     * Creates a new exception.
     *
     * @param index The illegal index
     */
    public TupleIndexOutOfBoundsException(long index) {
        super(index);
    }
}
