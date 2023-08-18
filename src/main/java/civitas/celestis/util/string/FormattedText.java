package civitas.celestis.util.string;

import jakarta.annotation.Nonnull;

import java.util.Arrays;
import java.util.EnumSet;

/**
 * A formatable {@link Text} object.
 * @see Text
 */
public interface FormattedText extends Text {
    //
    // Getters
    //

    /**
     * Returns the formatting of this text.
     *
     * @return The formatting of this text
     */
    @Nonnull
    Format format();

    //
    // Formatting
    //

    /**
     * Creates a new {@link Format} object from the provided array of options.
     *
     * @param options The options to construct the format with
     * @return The constructed {@link Format} object
     */
    @Nonnull
    static Format newFormat(@Nonnull Format.Option... options) {
        return new Format(options);
    }

    /**
     * The formatting of a {@link Text} object.
     */
    class Format {
        //
        // Options
        //

        /**
         * The available options for formatting a {@link Text} object.
         */
        public enum Option {
            BOLD("b"),
            ITALIC("i"),
            UNDERLINED("u"),
            STRIKE("s");

            /**
             * Creates a new option.
             *
             * @param tag The tag of this option
             */
            Option(@Nonnull String tag) {
                this.tag = tag;
            }

            /**
             * The tag of this option.
             */
            @Nonnull
            private final String tag;

            /**
             * Returns the tag of this option.
             *
             * @return The tag ot this option
             */
            @Nonnull
            public String tag() {
                return tag;
            }
        }

        //
        // Variables
        //

        /**
         * The set of options this format contains.
         */
        @Nonnull
        private final EnumSet<Option> options;

        //
        // Constructors
        //

        /**
         * Creates a new {@link Format} object.
         *
         * @param options The options of this format
         */
        protected Format(@Nonnull Option... options) {
            this.options = EnumSet.copyOf(Arrays.stream(options).toList());
        }

        //
        // Getters
        //

        /**
         * Returns a list of options this format has.
         *
         * @return A list of options this format has
         */
        @Nonnull
        public EnumSet<Option> options() {
            return options;
        }
    }
}
