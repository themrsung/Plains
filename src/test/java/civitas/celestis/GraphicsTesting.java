package civitas.celestis;

import civitas.celestis.graphics.Colors;
import civitas.celestis.task.lifecycle.Scheduler;
import civitas.celestis.task.lifecycle.SyncScheduler;
import jakarta.annotation.Nonnull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Used to test graphics components.
 */
public class GraphicsTesting {

    //
    // BOILERPLATE CODE; DO NOT MODIFY
    //

    static final Scheduler scheduler = new SyncScheduler();

    static final JFrame frame = new JFrame("GraphicsTesting") {
        @Override
        public void paint(@Nonnull Graphics g) {
            render(g);
        }
    };

    static final JPanel panel = new JPanel(true);

    static {
        // Set the dimensions of the window here
        frame.setSize(1920, 1080);
        frame.add(panel);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(@Nonnull WindowEvent e) {
                frame.dispose();
                scheduler.terminate();

                System.exit(Application.EXIT_CODE_NORMAL);
            }
        });

        scheduler.initialize();
    }

    //
    // END OF BOILERPLATE CODE
    //

    /**
     * This renders the screen.
     *
     * @param g The {@link Graphics} object to render to
     */
    static void render(@Nonnull Graphics g) {

        //
        // START OF TEST CODE
        //

        final Color random = Colors.random();

        g.setColor(random);
        g.fillRect(0, 0, frame.getWidth(), frame.getHeight());

        //
        // END OF TEST CODE
        //

        g.dispose();
    }

    /**
     * Starts the program. The program will exit when the window is closed.
     */
    public static void main(@Nonnull String[] args) {
        frame.setVisible(true);

        scheduler.start();
        scheduler.register(delta -> frame.repaint());
    }
}
