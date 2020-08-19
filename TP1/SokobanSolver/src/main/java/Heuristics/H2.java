package Heuristics;

import game.GameState;

import java.awt.*;

public class H2 implements Heuristic {

    @Override
    public int eval(GameState gameState) {
        int value = 0;
        for (Point bag : gameState.getBags()) {

                if (!gameState.isGoalCell((int) bag.getX(), (int)  bag.getY()) && bagAtDeadlock(gameState, bag)) {
                    return Integer.MAX_VALUE;
                }

                int shortest = Integer.MAX_VALUE;
                for (Point goal : gameState.getGoals()) {
                    shortest = Math.min(shortest, getDistance(bag, goal));
                }
                value += shortest;
        }

        return value;
    }

    public boolean bagAtDeadlock (GameState gameState, Point bag) {
        int x = (int) bag.getX();
        int y = (int) bag.getY();

        return (gameState.checkPosition(x - 1, y) || gameState.checkPosition(x + 1, y))
                && (gameState.checkPosition(x, y - 1) || gameState.checkPosition(x, y + 1));
    }

    public int getDistance (Point point1, Point point2) {
        int x = Math.abs((int) point1.getX() - (int) point2.getX());
        int y = Math.abs((int) point1.getY() - (int) point2.getY());
        return x + y;
    }

    @Override
    public String getName() {
        return "H2";
    }
}
