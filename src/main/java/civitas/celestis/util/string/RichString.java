package civitas.celestis.util.string;

import civitas.celestis.graphics.Color8;
import civitas.celestis.graphics.SimpleColor;
import civitas.celestis.util.Transformable;
import civitas.celestis.util.group.Group;
import jakarta.annotation.Nonnull;

import java.io.Serial;
import java.util.function.Function;

/**
 * A rich string with a color and format.
 * @see Text
 * @see Color8
 * @see civitas.celestis.util.string.Text.Format Format
 */
public class RichString implements Text, Transformable<String> {
    //
    // Constants
    //

    /**
     * The serial version UID of this class.
     */
    @Serial
    private static final long serialVersionUID = 4663366936359070270L;

    /**
     * The default color of a rich string.
     */
    public static final Color8 DEFAULT_COLOR = SimpleColor.BLACK;

    /**
     * The default format of a rich string.
     */
    public static final Format DEFAULT_FORMAT = PLAIN;

    //
    // Constructors
    //

    /**
     * Creates a new rich string with the value of {@code ""}.
     */
    public RichString() {
        this("", DEFAULT_COLOR, DEFAULT_FORMAT);
    }

    /**
     * Creates a new rich string.
     * @param content The content of this string
     */
    public RichString(@Nonnull String content) {
        this(content, DEFAULT_COLOR, DEFAULT_FORMAT);
    }

    /**
     * Creates a new rich string.
     *
     * @param content The content of this string
     * @param color   The color of this string
     */
    public RichString(@Nonnull String content, @Nonnull Color8 color) {
        this(content, color, DEFAULT_FORMAT);
    }

    /**
     * Creates a new rich string.
     *
     * @param content The content of this string
     * @param format  The formatting of this string
     */
    public RichString(@Nonnull String content,  @Nonnull Format format) {
        this(content, DEFAULT_COLOR, format);
    }

    /**
     * Creates a new rich string.
     * @param content The content of this string
     * @param color The color of this string
     * @param format The formatting of this string
     */
    public RichString(@Nonnull String content, @Nonnull Color8 color, @Nonnull Format format) {
        this.content = content;
        this.color = color;
        this.format = format;
    }

    //
    // Variables
    //

    /**
     * The content of this string.
     */
    @Nonnull
    protected final String content;

    /**
     * The color of this string.
     */
    @Nonnull
    protected final Color8 color;

    /**
     * The formatting of this string.
     */
    @Nonnull
    protected final Format format;

    //
    // Getters
    //

    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public String content() {
        return content;
    }

    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Color8 color() {
        return color;
    }

    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Nonnull
    @Override
    public Format format() {
        return format;
    }

    //
    // Transformation
    //

    /**
     * Applies the provided function {@code f} to the content of this string,
     * then returns a new {@link RichString} object constructed from the resulting string.
     * @param f The function to apply to the content of this string
     * @return The resulting string
     */
    @Nonnull
    @Override
    public RichString transform(@Nonnull Function<? super String, String> f) {
        return new RichString(f.apply(content), color, format);
    }

    /**
     * Unsupported. This will return a single-element group containing the returned object.
     */
    @Nonnull
    @Override
    public <F> Transformable<F> map(@Nonnull Function<? super String, F> f) {
        return Group.of(f.apply(content));
    }

    //
    // Serialization
    //

    /**
     * {@inheritDoc}
     * @return {@inheritDoc}
     */
    @Override
    @Nonnull
    public String toString() {
        return "RichString{" +
                "content='" + content + '\'' +
                ", color=" + color +
                ", format=" + format +
                '}';
    }
}
