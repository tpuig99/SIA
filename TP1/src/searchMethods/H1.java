package searchMethods;

import game.GameState;
import java.awt.*;

public class H1 implements Heuristic {
    @Override
    public int eval(GameState gameState) {

        int distance = Integer.MAX_VALUE;
        for (Point bag : gameState.getBags()) {
            if (!gameState.isGoalCell((int) bag.getX(), (int) bag.getY())) {
                for (Point goal : gameState.getFreeGoals()) {
                    distance = Math.min(distance, getDistance(bag, goal));
                }
            }
        }

        if (distance == Integer.MAX_VALUE) {    // if distance not modified, there were no FreeGoals -> Winning State
            return 0;
        }
        return distance;
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
