package civitas.celestis.demo;

import civitas.celestis.Application;
import civitas.celestis.annotation.application.ApplicationCritical;
import jakarta.annotation.Nonnull;

/**
 * A demo application to demonstrate the usage of the application class.
 * @see Application
 */
public class DemoApplication extends Application {
    //
    // Instance
    //

    /**
     * The instance of this application.
     */
    @ApplicationCritical
    private static final DemoApplication application = new DemoApplication();

    /**
     * Returns the application instance.
     * @return The application instance
     */
    @Nonnull
    public static DemoApplication getApplication() {
        return application;
    }

    //
    // Initialization
    //

    /**
     * Creates a new demo application.
     */
    private DemoApplication() {
        super("PlainsDemoApplication", "0.4");
    }
}
