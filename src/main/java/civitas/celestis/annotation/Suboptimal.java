package civitas.celestis.annotation;

import jakarta.annotation.Nonnull;

import java.lang.annotation.*;

/**
 * A marker interface used to mark elements which are not optimized and/or
 * not optimizable due to their algorithm's inherit nature. When this annotation
 * is encountered, an alternate approach to the solution should be considered
 * before using the annotated element.
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target({
        ElementType.TYPE,
        ElementType.FIELD,
        ElementType.METHOD,
        ElementType.PARAMETER,
        ElementType.CONSTRUCTOR,
        ElementType.LOCAL_VARIABLE,
        ElementType.RECORD_COMPONENT
})
public @interface Suboptimal {
    /**
     * Returns the reason this component is suboptimal.
     *
     * @return The reason this component is suboptimal
     */
    @Nonnull
    String reason() default "unknown";
}
