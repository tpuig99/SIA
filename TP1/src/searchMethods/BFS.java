package searchMethods;

import game.Constants;
import game.GameState;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class BFS extends SearchMethod {

    public GameState run(GameState start) {
        super.reset();

        super.startTime = System.currentTimeMillis();
        Queue<GameState> frontier = new LinkedList<>();
        Set<GameState> gameStateHistory = new HashSet<>();

        gameStateHistory.add(start);
        frontier.offer(start);
        while (!frontier.isEmpty()) {      // in bfs, frontier is a queue of gamestates
            GameState curr = frontier.poll();

            if (curr.solved()) {
                super.endTime = System.currentTimeMillis();
                super.frontierCount = frontier.size();
                return curr;
            }

            boolean expandedNode = false;

            for (Constants.Direction direction : Constants.Direction.values()) {
                GameState aux = curr.moveInDirection(direction);
                if (aux != null) {
                    if (!gameStateHistory.contains(aux)) {
                        if(!expandedNode) {
                            expandedNode = true; // node expands <=> at least 1 son wasn't visited before
                            super.expandedNodes++;
                        }
                        gameStateHistory.add(aux);
                        aux.setParent(curr);
                        aux.setDirectionFromParent(direction);
                        frontier.offer(aux);
                    }
                }
            }
        }

        super.endTime = System.currentTimeMillis();
        super.frontierCount = 0;

        return null;
    }

    @Override
    public String getName() {
        return "BFS";
    }
}
