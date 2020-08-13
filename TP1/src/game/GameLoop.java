package game;

import game.cell.Cell;

import java.awt.*;

import static game.Constants.*;

public class GameLoop {

    private static final long ONE_SECOND = 1 * 1000000000;
    private GameState gameState;
    Cell[][] map;
    private long lastUpdate;

    public GameLoop(GameState gameState) {
        this.gameState = gameState;
        map = gameState.getBoard().getCells();
        lastUpdate = System.nanoTime();
    }

    public void loop(Graphics graphics, GameState gameState) {
        this.gameState = gameState;
        repaintScreen(graphics);
        long time = System.nanoTime();
        if (time - lastUpdate > ONE_SECOND / 2) {
            System.out.println("Time elapsed: " + ((double) System.nanoTime() - time) / 1000000 + "ms");
            lastUpdate = time;
        }
        if (gameState.solved()) {
            System.out.println("Ganaste");;
        }
    }

    private void repaintScreen(Graphics g) {
        for (int j = 0; j < gameState.getBoard().getHeight(); j++) {
            for (int i = 0; i < gameState.getBoard().getWidth(); i++) {
                g.setColor(defineColor(i, j));
                g.fillRect(i * SCALE , j * SCALE, SCALE, SCALE);
            }
        }
    }

    private Color defineColor(int x, int y) {
        switch (map[x][y]) {
            case FREE:
                if ((int) gameState.getPlayer().getX() == x && (int) gameState.getPlayer().getY() == y) {
                    return Color.BLUE;
                }
                else if (gameState.checkBagInPosition(x, y)) {
                    return Color.RED;
                }
                return Color.LIGHT_GRAY;
            case WALL:
                return Color.BLACK;
            case GOAL:
                if ((int) gameState.getPlayer().getX() == x && (int) gameState.getPlayer().getY() == y) {
                    return Color.BLUE;
                }
                else if (gameState.checkBagInPosition(x, y)) {
                    return Color.GREEN;
                }
                return Color.YELLOW;
            default:
                return Color.LIGHT_GRAY;
        }
    }
}
