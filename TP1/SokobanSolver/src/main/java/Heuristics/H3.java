package Heuristics;

import game.GameState;

import java.awt.*;

public class H3 implements Heuristic {
    @Override
    public int eval(GameState gameState) {
        int value = 0;
        int shortestGoal = Integer.MAX_VALUE;
        int shortestPlayer = Integer.MAX_VALUE;
        for (Point bag : gameState.getBags()) {
            if (!gameState.isGoalCell((int) bag.getX(), (int) bag.getY())) {
                for (Point goal : gameState.getGoals()) {
                    shortestGoal = Math.min(shortestGoal, getDistance(bag, goal));
                }
                shortestPlayer = Math.min(shortestPlayer, getDistance(bag, gameState.getPlayer()));
                value += shortestGoal;
            }
        }

        return value + shortestPlayer;
    }

    public int getDistance (Point point1, Point point2) {
        int x = Math.abs((int) point1.getX() - (int) point2.getX());
        int y = Math.abs((int) point1.getY() - (int) point2.getY());
        return x + y;
    }

    @Override
    public String getName() {
        return "H3";
    }
}
