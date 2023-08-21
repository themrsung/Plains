package civitas.celestis.physics;

import civitas.celestis.math.Numbers;
import civitas.celestis.math.Vector3;
import civitas.celestis.util.SafeArray;
import jakarta.annotation.Nonnull;

/**
 * A three-dimensional geometric solid.
 */
public interface Solid {
    //
    // Factory
    //

    /**
     * Given a set of vertices, this returns a new solid which represent those vertices.
     *
     * @param vertices The vertices of which to represent
     * @return A new solid representing the provided vertices
     */
    @Nonnull
    static Solid of(@Nonnull Vector3... vertices) {
        return new BoundingBox(Numbers.min(vertices), Numbers.max(vertices));
    }

    //
    // Containment
    //

    /**
     * Checks if the provided vertex {@code v} is within the bounds of this solid.
     *
     * @param v The vertex of which to check for containment
     * @return {@code true} if the vertex {@code v} is within the bounds of this solid
     */
    boolean contains(@Nonnull Vector3 v);

    /**
     * Checks if this solid has at least one overlapping vertex with the provided solid {@code s}.
     *
     * @param s The solid of which to check for overlaps
     * @return {@code true} if the two solids have at least one vertex in common
     */
    boolean overlaps(@Nonnull Solid s);

    //
    // Geometry
    //

    /**
     * Returns an array containing the corners of this solid.
     *
     * @return The corners of this solid
     */
    @Nonnull
    SafeArray<Vector3> corners();

    /**
     * Returns the geometric centroid of this solid.
     *
     * @return The geometric centroid of this solid
     */
    @Nonnull
    Vector3 center();

    /**
     * Returns the unsigned (absolute) volume of this solid.
     *
     * @return The unsigned volume of this solid
     */
    double volume();

    /**
     * Returns the signed volume of this solid.
     *
     * @return The signed volume of this solid
     */
    double signedVolume();

    //
    // Bounding Box
    //

    /**
     * Returns the bounding box of this solid.
     *
     * @return The bounding box of this solid
     */
    @Nonnull
    BoundingBox boundingBox();

    /**
     * A bounding box used for rough estimation and optimization.
     */
    class BoundingBox implements Solid {
        //
        // Constructors
        //

        /**
         * Creates a new bounding box.
         *
         * @param min The minimum boundary vertex of this box
         * @param max The maximum boundary vertex of this box
         */
        private BoundingBox(@Nonnull Vector3 min, @Nonnull Vector3 max) {
            this.min = min;
            this.max = max;
        }

        //
        // Variables
        //

        /**
         * The minimum vertex of this bounding box.
         */
        @Nonnull
        protected final Vector3 min;

        /**
         * The maximum vertex of this bounding box.
         */
        @Nonnull
        protected final Vector3 max;

        //
        // Methods
        //

        /**
         * Returns the dimensions of this bounding box.
         *
         * @return The dimensions of this bounding box
         */
        @Nonnull
        public Vector3 dimensions() {
            return max.subtract(min);
        }

        /**
         * {@inheritDoc}
         *
         * @param v The vertex of which to check for containment
         * @return {@inheritDoc}
         */
        @Override
        public boolean contains(@Nonnull Vector3 v) {
            return Numbers.isInRange(v, min, max);
        }

        /**
         * {@inheritDoc}
         *
         * @param s The solid of which to check for overlaps
         * @return {@inheritDoc}
         */
        @Override
        public boolean overlaps(@Nonnull Solid s) {
            for (final Vector3 corner : s.corners()) {
                if (contains(corner)) return true;
            }

            return false;
        }

        /**
         * {@inheritDoc}
         *
         * @return {@inheritDoc}
         */
        @Nonnull
        @Override
        public SafeArray<Vector3> corners() {
            return SafeArray.of(
                    min,
                    new Vector3(min.x(), min.y(), max.z()),
                    new Vector3(min.x(), max.y(), min.z()),
                    new Vector3(min.x(), max.y(), max.z()),
                    new Vector3(max.x(), min.y(), min.z()),
                    new Vector3(max.x(), min.y(), max.z()),
                    new Vector3(max.x(), max.y(), min.z()),
                    max
            );
        }

        /**
         * {@inheritDoc}
         *
         * @return {@inheritDoc}
         */
        @Nonnull
        @Override
        public Vector3 center() {
            return Numbers.avg(min, max);
        }

        /**
         * {@inheritDoc}
         *
         * @return {@inheritDoc}
         */
        @Override
        public double volume() {
            final Vector3 dimensions = dimensions();

            final double x = Math.abs(dimensions.x());
            final double y = Math.abs(dimensions.y());
            final double z = Math.abs(dimensions.z());

            return x * y * z;
        }

        /**
         * {@inheritDoc}
         *
         * @return {@inheritDoc}
         */
        @Override
        public double signedVolume() {
            final Vector3 dimensions = dimensions();
            return dimensions.x() * dimensions.y() * dimensions.z();
        }

        /**
         * This is already a bounding box, and thus requires no conversion.
         * This simply returns a reference to itself.
         *
         * @return {@code this}
         */
        @Nonnull
        @Override
        public BoundingBox boundingBox() {
            return this;
        }

        /**
         * Serializes this bounding box into a string for debugging purposes.
         *
         * @return The string representation of this bounding box
         */
        @Nonnull
        @Override
        public String toString() {
            return "BoundingBox{" +
                    "min=" + min +
                    ", max=" + max +
                    '}';
        }
    }
}
