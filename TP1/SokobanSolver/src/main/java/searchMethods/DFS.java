package searchMethods;

import game.Constants;
import game.GameState;

import java.util.*;

public class DFS extends SearchMethod {

    private Map<GameState, Integer> history;

    @Override
    public GameState run(GameState start) {
        super.reset();

        super.startTime = System.currentTimeMillis();
        history = new HashMap<>();
        GameState solution = dfs(start, Integer.MAX_VALUE);
        super.endTime = System.currentTimeMillis();
        return solution;
    }

    public GameState run(GameState start,int maxDepth) {
        super.reset();

        super.startTime = System.currentTimeMillis();
        history = new HashMap<>();
        GameState solution = dfs(start, maxDepth);
        super.endTime = System.currentTimeMillis();
        return solution;
    }

    public DFS() { history = new HashMap<>();    }

    public GameState dfs (GameState start, int maxDepth) {
        Stack<GameState> stack = new Stack<>();
        start.setDepth(0);
        stack.push(start);

        while(!stack.isEmpty()) {
            GameState curr = stack.pop();
            super.frontierCount = stack.size();

            if(curr.solved()) {
                return curr;
            }

            boolean expandedNode = false; // same as in bfs

            for (Constants.Direction direction : Constants.Direction.values()) {
                GameState aux = curr.moveInDirection(direction);
                if (aux != null && !history.containsKey(aux)) {
                    if (curr.getDepth() + 1 <= maxDepth) {
                        if (!expandedNode) {
                            expandedNode = true;
                            super.expandedNodes++;
                        }
                        aux.setParent(curr);
                        aux.setDirectionFromParent(direction);
                        aux.setDepth(curr.getDepth() + 1);
                        history.put(aux, curr.getDepth() + 1);
                        stack.push(aux);
                    }
                }
            }
        }
        super.frontierCount = 0;
        return null;
    }

    @Override
    public String getName() {
        return "DFS";
    }
}
