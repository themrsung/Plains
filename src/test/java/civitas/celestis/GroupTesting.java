package civitas.celestis;

import civitas.celestis.graphics.Colors;
import civitas.celestis.math.Scalars;
import civitas.celestis.task.Task;
import civitas.celestis.task.lifecycle.Scheduler;
import civitas.celestis.task.lifecycle.SyncScheduler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.atomic.AtomicReference;

public class GroupTesting {

    static final AtomicReference<Color> color = new AtomicReference<>(Colors.GOLD);
    static final JFrame frame = new JFrame("Test");
    static final JPanel panel = new JPanel() {
        @Override
        public void paint(Graphics g) {
            final double t = Math.random();

            g.setColor(Colors.lerp(Colors.GOLD, Colors.BLACK, t));
            g.fillRect(0, 0, getWidth() / 2, getHeight());

            g.setColor(Colors.bezier(Colors.GOLD, Colors.BLACK, t));
            g.fillRect(getWidth() / 2, 0, getWidth(), getHeight());

            g.dispose();
        }
    };

    static final Scheduler scheduler = new SyncScheduler();

    static {
        frame.add(panel);
        frame.setSize(1920, 1080);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.dispose();
                scheduler.terminate();

                System.exit(Application.EXIT_CODE_NORMAL);
            }
        });

        scheduler.initialize();
    }

    public static void main(String[] args) {
        scheduler.register(new Task() {
            @Override
            public void execute(long delta) {
                frame.repaint();
            }

            @Override
            public long interval() {
                return 800;
            }
        });

        frame.setVisible(true);

        scheduler.start();

        for (int i = 0; i < 10000; i++) {
            double s1 = i;
            double s2 = s1 + 100;

            System.out.println(s1 + " -> " + s2 + " = " + Scalars.lerp(s1, s2, 0.5));
            System.out.println(s1 + " -> " + s2 + " = " + Scalars.bezier(s1, 1, 1, s2, 0.5));
        }
    }
}
