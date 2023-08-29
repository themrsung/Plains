package civitas.celestis;

import civitas.celestis.graphics.Colors;
import jakarta.annotation.Nonnull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Application to test features in a controlled environment.
 */
public class BenchmarkApplication extends Application {
    //
    // Singleton
    //

    /**
     * Returns the application instance.
     * @return The application instance
     */
    @Nonnull
    public static BenchmarkApplication getApplication() {
        return application;
    }

    private static final BenchmarkApplication application = new BenchmarkApplication();

    //
    // Main
    //

    public static void main(String[] args) {
        application.start();
    }

    //
    // Constructors
    //

    private BenchmarkApplication() {
        super("PlainsBenchmarkApplication", "0.6");
    }

    //
    // GUI
    //

    /**
     * The frame of this application.
     */
    private final JFrame frame = new JFrame();

    /**
     * Initializes the frame.
     */
    private void initializeFrame() {
        frame.setSize(1920, 1080);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                stop();
            }
        });
    }

    //
    // Methods
    //

    @Override
    public void start() {
        super.start();
        initializeFrame();

        //
        // TEST CODE HERE
        //

        final AtomicReference<Color> color = new AtomicReference<>(Colors.GOLDEN_ROD);
        final JComponent component = (JComponent) frame.add(new JComponent() {
            @Override
            public void paint(Graphics g) {
                g.setColor(color.getAndUpdate(c -> Colors.lerp(c, Colors.random(), 0.05)));
                g.fillRect(0, 0, getWidth(), getHeight());
                g.dispose();
            }
        });

        component.setSize(frame.getSize());

        scheduler.register(delta -> component.repaint());

        frame.setVisible(true);
    }
}
