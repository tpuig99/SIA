package game;

import game.cell.Player;

public interface GameOptions {

    void swap(int fromX, int fromY, int toX, int toY);

    boolean isCellEmpty(int x, int y);

    Player getPlayer();
}
