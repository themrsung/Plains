package civitas.celestis;

import civitas.celestis.event.CancellableEvent;
import civitas.celestis.graphics.*;
import civitas.celestis.task.lifecycle.AtomicScheduler;
import civitas.celestis.task.lifecycle.Scheduler;
import civitas.celestis.util.grid.ArrayGrid;
import civitas.celestis.util.grid.Grid;
import jakarta.annotation.Nonnull;

public class GroupTesting {
    static Grid<Long> imageToLongGrid(@Nonnull Image img) {
        final Grid<Long> grid = new ArrayGrid<>(img.getHeight(), img.getWidth());

        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                grid.set(x, y, img.getRGBA(x, y));
            }
        }

        return grid;
    }

    public static void main(String[] args) {
        final Image image = new PackedImage(100, 100);
        Renderer renderer = image.createRenderer()
                .fill(Color.BLACK.rgba64())
                .renderLine(0, 0, 99, 99, Color.WHITE.rgba64());

        // Dispose renderer
        renderer = null;

        System.out.println(imageToLongGrid(image));
    }
}
