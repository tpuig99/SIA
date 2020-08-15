package searchMethods;

import game.GameState;
import java.awt.*;

public class H1 implements Heuristic {
    @Override
    public int eval(GameState gameState) {

        int value = 0;
        for (Point bag : gameState.getBags()) {
            if (!gameState.isGoalCell((int) bag.getX(), (int) bag.getY())) {
                int shortest = Integer.MAX_VALUE;
                for (Point goal : gameState.getFreeGoals()) {
                    shortest = Math.min(shortest, getDistance(bag, goal));
                }
                value += shortest;
            }
        }

        return value;
    }

    public int getDistance (Point point1, Point point2) {
        int x = Math.abs((int) point1.getX() - (int) point2.getX());
        int y = Math.abs((int) point1.getY() - (int) point2.getY());
        return x + y;
    }

    @Override
    public String getName() {
        return "H1";
    }
}
