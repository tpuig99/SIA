package searchMethods;

import Heuristics.Heuristic;
import game.GameState;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

public class GlobalGreedy extends SearchMethod{
    PriorityQueue<GameState> queue;
    HashSet<GameState> history;
    public GlobalGreedy(Heuristic h) {
        queue = new PriorityQueue<>();
        history = new HashSet<>();
        super.setHeuristic(h);
    }

    @Override
    public GameState run(GameState start) {
        super.reset();
        start.setSorting(h(start));
        start.setDepth(0);
        super.startTime = System.currentTimeMillis();
        queue.add(start);

        while (!queue.isEmpty()){
            GameState curr = queue.poll();
            super.frontierCount = queue.size();
            if(curr.solved()){
                super.endTime = System.currentTimeMillis();
                return curr;
            }
            boolean expandedNode = false; // same as in bfs
            List<GameState> adjacentStates = curr.getAdjacentStates();

            for (GameState state : adjacentStates) {
                if(!history.contains(state)){
                    if(!expandedNode){
                        expandedNode = true;
                        super.expandedNodes++;
                    }
                    history.add(state);
                    state.setSorting(h(state));
                    state.setParent(curr);
                    state.setDepth(curr.getDepth()+1);
                    queue.add(state);
                }
                if(curr.getSorting() < state.getSorting()){
                    queue.add(curr);
                }
            }
        }
        super.endTime = System.currentTimeMillis();
        return null;
    }

    @Override
    public String getName() {
        return "GREEDY";
    }
}
