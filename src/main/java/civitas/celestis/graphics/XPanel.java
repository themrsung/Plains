package civitas.celestis.graphics;

import jakarta.annotation.Nonnull;

import javax.swing.*;
import java.awt.*;

/**
 * An extended {@link JPanel} with advanced features.
 *
 * @see JPanel
 */
public class XPanel extends JPanel {
    //
    // Constructors
    //

    /**
     * Creates a new extended panel.
     *
     * @param layout           The layout manager object
     * @param isDoubleBuffered Whether this panel should be double-buffered
     */
    public XPanel(@Nonnull LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
    }

    /**
     * Creates a new extended panel.
     *
     * @param layout The layout manager object
     */
    public XPanel(@Nonnull LayoutManager layout) {
        super(layout, true);
    }

    /**
     * Creates a new extended panel.
     *
     * @param isDoubleBuffered Whether this panel should be double-buffered
     */
    public XPanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    /**
     * Creates a new extended panel.
     */
    public XPanel() {
        super(true);
    }
}
