package civitas.celestis;


import civitas.celestis.graphics.Colors;
import civitas.celestis.graphics.LinearColor;

import java.awt.*;

public class GroupTesting {
    public static void main(String[] args) {
        final Color c1 = Color.RED;
        final Color c2 = Color.BLUE;

        final Color c3 = Colors.lerp(c1, c2, Double.MIN_VALUE);

        System.out.println(Colors.toString(c3));

    }
}
