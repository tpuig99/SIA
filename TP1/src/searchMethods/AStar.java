package searchMethods;

import Heuristics.Heuristic;
import game.Constants;
import game.GameState;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class AStar extends SearchMethod {
    private PriorityQueue<GameState> priorityQueue = new PriorityQueue<>((gs1, gs2) -> gs1.getSorting() - gs2.getSorting());
    private Map<GameState, Integer> costMap = new HashMap<>();

    public AStar(Heuristic h) {
        super.setHeuristic(h);
    }

    public GameState run(GameState start) {
        super.reset();

        super.startTime = System.currentTimeMillis();
        start.setSorting(h(start));
        costMap.put(start, 0);
        priorityQueue.offer(start);

        while(!priorityQueue.isEmpty()) {
            GameState curr = priorityQueue.poll();

            if (curr.solved()) {
                super.endTime = System.currentTimeMillis();
                super.frontierCount = priorityQueue.size();
                return curr;
            }

            boolean expandedNode = false;

            int nextG = costMap.get(curr) + 1;

            for (Constants.Direction direction : Constants.Direction.values()) {
                GameState aux = start.moveInDirection(direction);

                if (aux != null) {
                    Integer directionScore = costMap.getOrDefault(aux, null);
                    if (directionScore == null || nextG < directionScore) {  // if gamestate was not visited
                                                                                // or visited previously with greater cost
                        int heuristicValue = h(aux);
                        if (heuristicValue < Integer.MAX_VALUE) {               // if heuristic says ok
                            if (!expandedNode) {
                                expandedNode = true;
                                super.expandedNodes++;
                            }

                            if (directionScore != null) {   // if new value is better, update
                                priorityQueue.remove(aux);
                            }
                            aux.setParent(curr);
                            aux.setSorting(nextG + heuristicValue);
                            aux.setDirectionFromParent(direction);
                            costMap.put(aux, nextG);
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