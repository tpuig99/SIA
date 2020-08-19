package searchMethods;

import game.Constants;
import game.GameState;

import java.util.*;

public class IDDFS extends SearchMethod{
    private Map<GameState, Integer> history;

    @Override
    public GameState run(GameState start) {
        super.reset();
        super.startTime = System.currentTimeMillis();
        int depth = 0;
        GameState solution;
        do{
            history = new HashMap<>();
            super.expandedNodes = 0;
            solution = dfs(start,depth++);
            //super.expandedNodes += dfs.expandedNodes;
        }while (solution == null);
        super.endTime = System.currentTimeMillis();
        //super.frontierCount = dfs.frontierCount;
        return solution;
    }
    private GameState dfs (GameState start, int maxDepth) {
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
                if (aux != null && (!history.containsKey(aux)||history.get(aux)>curr.getDepth()+1)) {
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
        return "IDDFS";
    }
}
