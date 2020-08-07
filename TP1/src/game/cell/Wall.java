package game.cell;

import java.awt.*;

public class Wall implements Cell {
    @Override
    public Color getColor() {
        return Color.BLACK;
    }
}
