package game.cell;

import java.awt.*;

public class GoalCell implements Cell {
    @Override
    public Color getColor() {
        return Color.YELLOW;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof GoalCell;
    }
}
