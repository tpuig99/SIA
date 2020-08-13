package searchMethods;

import game.Constants;
import game.GameState;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class BFS extends SearchMethod {
    private Set<GameState> visitedStates;
    private Queue<GameState> q;

    public GameState run(GameState start) {
        if(start == null) {
            super.totalNodesExpanded = 0;
            super.latestFrontierNodeCount = 0;
            return null;
        }
        super.resetStats();

        super.startTime = System.currentTimeMillis();
        q = new LinkedList<>();
        visitedStates = new HashSet<>();

        visitedStates.add(start);
        q.offer(start);
        while (!q.isEmpty()) {
            GameState curr = q.poll();
            if (curr.solved()) {
                super.endTime = System.currentTimeMillis();
                super.latestFrontierNodeCount = q.size();
                return curr;
            }

            boolean expanded = false;

            for (Constants.Direction direction : Constants.Direction.values()) {
                GameState aux = curr.moveInDirection(direction);
                if (aux != null) {
                    if (!visitedStates.contains(aux)) {
                        if(!expanded) {
                            expanded = true;
                            super.totalNodesExpanded++;
                        }
                        visitedStates.add(aux);
                        aux.setParent(curr);
                        aux.setDirectionFromParent(direction);
                        q.offer(aux);
                    }
                }
            }
        }

        super.endTime = System.currentTimeMillis();
        super.latestFrontierNodeCount = 0;

        return null;
    }

    @Override
    public String getName() {
        return "Breadth-first Search";
    }
}
