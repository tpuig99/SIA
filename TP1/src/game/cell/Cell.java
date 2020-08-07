package game.cell;

import java.awt.*;

public interface Cell {
    default boolean isEmpty() {
        return false;
    }

    Color getColor();
}
