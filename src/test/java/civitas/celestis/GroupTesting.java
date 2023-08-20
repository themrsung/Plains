package civitas.celestis;

import civitas.celestis.graphics.Color8;
import civitas.celestis.graphics.LinearColor;
import civitas.celestis.graphics.SimpleColor;
import civitas.celestis.util.tuple.Tuple;

import javax.swing.*;
import java.awt.*;

public class GroupTesting {
    public static void main(String[] args) {
        final Color8 color = new SimpleColor(120, 200, 100, 255);
        System.out.println(color);

        final Color awt = color.inverse().awt32();
        final JFrame frame = new JFrame("Test");
        final JPanel panel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                g.setColor(awt);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        frame.add(panel);
        frame.setSize(1000, 1000);
        frame.setVisible(true);
    }
}
