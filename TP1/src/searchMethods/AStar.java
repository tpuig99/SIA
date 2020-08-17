package searchMethods;

import Heuristics.Heuristic;
import game.Constants;
import game.GameState;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class AStar extends SearchMethod {
    private PriorityQueue<GameState> priorityQueue;
    private Map<GameState, Integer> history;

    public AStar(Heuristic h) {
        super.setHeuristic(h);
    }

    public GameState run(GameState start) {
        super.reset();

        priorityQueue = new PriorityQueue<>((gs1, gs2) -> gs1.getSorting() - gs2.getSorting());
        history = new HashMap<>();
        super.startTime = System.currentTimeMillis();
        start.setSorting(h(start));
        history.put(start, 0);
        priorityQueue.offer(start);

        while(!priorityQueue.isEmpty()) {
            GameState curr = priorityQueue.poll();

            if (curr.solved()) {
                super.endTime = System.currentTimeMillis();
                super.frontierCount = priorityQueue.size();
                return curr;
            }

            boolean expandedNode = false;

            int nextG = history.get(curr) + 1;

            for (Constants.Direction direction : Constants.Direction.values()) {
                GameState aux = curr.moveInDirection(direction);

                if (aux != null) {
                    if(!history.containsKey(aux) || history.get(aux) > nextG) {
                        if (history.get(aux) != null) {
                            priorityQueue.remove(aux);
                        }

                        int heuristicValue = h(aux);
                        if (heuristicValue < Integer.MAX_VALUE) {               // if heuristic says ok
                            if (!expandedNode) {
                                expandedNode = true;
                                super.expandedNodes++;
                            }

                            aux.setParent(curr);
                            aux.setSorting(nextG + heuristicValue);
                            aux.setDirectionFromParent(direction);
                            history.put(aux, nextG);
                            priorityQueue.offer(aux);
                        }
                    }
                }
            }
        }
        endTime = System.currentTimeMillis();
        super.frontierCount = 0;
        return null;
    }

    @Override
    public String getName() {
        return "A* - " + super.getHeuristicName();
    }
}