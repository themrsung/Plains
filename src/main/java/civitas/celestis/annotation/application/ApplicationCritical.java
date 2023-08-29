package civitas.celestis.annotation.application;

import jakarta.annotation.Nonnull;

import java.lang.annotation.*;

/**
 * A marker interface used to mark certain parts of the code which are considered
 * application critical. A code is application critical when it can influence the
 * behavior of the main thread of the application in a destructive manner.
 * <p>
 * Destructive actions are defined as code that can potentially put the main thread
 * in an infinite loop, or alter the normal control flow of the main thread.
 * </p>
 * <p>
 * When a component is annotated with this annotation, exercise caution when
 * using or modifying its contents, as it could potentially lead to unintended
 * behavior on the main thread, which may cause major issues which damage
 * the integrity of the entire application.
 * </p>
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target({

        /*
         * Types are not added to this list intentionally.
         * Maintaining version information for types are both ambiguous and tedious,
         * and cannot be guaranteed to be consistent with the code base.
         */

        ElementType.FIELD,
        ElementType.METHOD,
        ElementType.PARAMETER,
        ElementType.CONSTRUCTOR,
        ElementType.RECORD_COMPONENT,
        ElementType.LOCAL_VARIABLE

})
public @interface ApplicationCritical {
    /**
     * Returns the version of which this component was created in.
     * This has a default value only to facilitate the usage of this annotation.
     * In practice, this should be treated as a required field.
     *
     * @return The version this component was created in
     */
    @Nonnull
    String created() default "unknown";

    /**
     * Returns the version of which this component was last updated in.
     *
     * @return The version this component was last updated in
     */
    @Nonnull
    String lastUpdated() default "unknown";
}
