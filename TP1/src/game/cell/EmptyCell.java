package game.cell;

import java.awt.*;

public class EmptyCell implements Cell {

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public Color getColor() {
        return Color.WHITE;
    }
}
