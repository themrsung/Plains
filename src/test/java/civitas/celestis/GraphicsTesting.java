package civitas.celestis;

import civitas.celestis.graphics.LinearColor;
import civitas.celestis.graphics.XFrame;
import civitas.celestis.graphics.XPanel;
import civitas.celestis.task.Scheduler;
import civitas.celestis.task.SyncScheduler;

import java.awt.*;
import java.util.concurrent.atomic.AtomicReference;

public class GraphicsTesting {
    static AtomicReference<LinearColor> color = new AtomicReference<>(LinearColor.CYAN);
    static XFrame frame = new XFrame("Test");
    static XPanel panel = new XPanel() {
        @Override
        public void paint(Graphics g) {
            setSize(1920, 1080);

            color.getAndUpdate(c -> c.lerp(LinearColor.WHITE, 0.05));
            g.setColor(color.get());
            g.fillRect(0, 0, getWidth(), getHeight());
            g.dispose();
        }
    };
    static Scheduler scheduler = new SyncScheduler();

    public static void main(String[] args) {
        scheduler.register(delta -> frame.repaint());
        frame.setSize(1920, 1080);

        frame.add(panel);

        frame.setVisible(true);
        scheduler.start();
    }
}
