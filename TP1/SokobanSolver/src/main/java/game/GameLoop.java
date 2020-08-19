package game;

import java.awt.*;

import static game.Constants.*;

public class GameLoop {
    private GameState gameState;
    Cell[][] map;
    private boolean solved;

    public GameLoop(GameState gameState) {
        this.gameState = gameState;
        solved = false;
        map = gameState.getBoard().getCells();
    }

    public void loop(Graphics graphics, GameState gameState) {
        this.gameState = gameState;
        repaintScreen(graphics);
        if (gameState.solved() && !solved) {
            System.out.println("Ganaste");
            solved = true;
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
