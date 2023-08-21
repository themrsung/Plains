package civitas.celestis.graphics;

import jakarta.annotation.Nonnull;

import javax.swing.*;
import java.awt.*;

/**
 * An extended {@link JFrame} with advanced features.
 *
 * @see JFrame
 */
public class XFrame extends JFrame {
    //
    // Constructors
    //

    /**
     * Creates a new extended frame.
     *
     * @throws HeadlessException When the hardware does not support this operation
     */
    public XFrame() throws HeadlessException {
    }

    /**
     * Creates a new extended frame.
     *
     * @param gc The graphics configuration object
     */
    public XFrame(@Nonnull GraphicsConfiguration gc) {
        super(gc);
    }

    /**
     * Creates a new extended frame.
     *
     * @param title The title of this frame
     * @throws HeadlessException When the hardware does not support this operation
     */
    public XFrame(@Nonnull String title) throws HeadlessException {
        super(title);
    }

    /**
     * Creates a new extended frame.
     *
     * @param title The title of this frame
     * @param gc    The graphics configuration object
     */
    public XFrame(@Nonnull String title, @Nonnull GraphicsConfiguration gc) {
        super(title, gc);
    }
}
