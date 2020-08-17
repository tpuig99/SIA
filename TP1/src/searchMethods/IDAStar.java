package searchMethods;

import Heuristics.Heuristic;
import game.Constants;
import game.GameState;

import java.util.*;

public class IDAStar extends SearchMethod {
    private Map<GameState, Integer> history;
    private int searchUpTo;

    public IDAStar(Heuristic h) {
        super.setHeuristic(h);
    }

    public GameState run(GameState start) {
        super.reset();
        super.startTime = System.currentTimeMillis();

        GameState result = null;

        int currMaxF = h(start);

        while(result == null) {
            history = new HashMap<>();
            searchUpTo = Integer.MAX_VALUE;
            super.frontierCount = 1;
            result = dfs(start, currMaxF);
            System.out.println("Llegué hasta: " + currMaxF);
            currMaxF = searchUpTo;
        }

        super.endTime = System.currentTimeMillis();
        return result;
    }

    private GameState dfs(GameState start, int max_f) {
        Stack<GameState> stack = new Stack<>();
        start.setSorting(h(start));
        start.setDepth(0);
        stack.push(start);

        while(!stack.isEmpty()) {
            GameState curr = stack.pop();
            super.frontierCount = stack.size();

            if(curr.solved()) {
                return curr;
            }

//            List<GameState> adjacent = new LinkedList<>(curr.getAdjacentStates());
//            Iterator<GameState> it = adjacent.iterator();
//
//            while (it.hasNext()) {
//                GameState gameState = it.next();
//                int newF = h(gameState) + curr.getDepth() + 1;
//                if (gameState.getSorting() == 0 || newF < gameState.getSorting()) {
//                    gameState.setSorting(newF);
//                }
//                else {
//                    it.remove();
//                }
//            }
//
//            Collections.sort(adjacent);

            
            List<GameState> adjacent = new LinkedList<>();
            for (Constants.Direction direction : Constants.Direction.values()) {
                GameState aux = curr.moveInDirection(direction);
                if (aux != null) {
                    int newF = h(aux) + curr.getDepth() + 1;
                    if (aux.getSorting() == 0 || newF < aux.getSorting()) {
                        aux.setSorting(newF);
                        aux.setParent(curr);
                        aux.setDirectionFromParent(direction);
                        adjacent.add(aux);
                    }
                }
            }

            Collections.sort(adjacent);

            boolean expanded = false;

            for(GameState adj : adjacent) {
                if(!history.containsKey(adj) || history.get(adj) < curr.getDepth() + 1) {
                    int newF = adj.getSorting();
                    if (newF <= max_f) {
                        if (!expanded) {
                            expanded = true;
                            super.expandedNodes++;
                        }
                        adj.setDepth(curr.getDepth() + 1);
                        history.put(adj, adj.getDepth());
                        stack.push(adj);
                    } else {
                        if (newF < searchUpTo) { // if no solutions found at that depth, search deeper
                            searchUpTo = newF;
                        }
                    }
                }

            }
        }
        return null;
    }

    @Override
    public String getName() {
        return "IDA* - " + super.getHeuristicName();
    }
}
