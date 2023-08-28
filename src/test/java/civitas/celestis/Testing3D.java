package civitas.celestis;

import civitas.celestis.graphics.Colors;
import civitas.celestis.graphics.Face;
import civitas.celestis.graphics.Vertex;
import civitas.celestis.math.vector.Vectors;

public class Testing3D {
    public static void main(String[] args) {
        final Face face = new Face(
                Vertex.POSITIVE_X,
                Vertex.NEGATIVE_Z,
                Vertex.ZERO,
                Colors.ANTIQUE_WHITE
        );

        System.out.println(face.transform(v -> v.absolute(new Vertex(2320,202, 202), Vectors.randomQuaternion())));
    }
}
