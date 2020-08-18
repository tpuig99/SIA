package searchMethods;

import game.GameState;

import java.util.*;

public class IDDFS extends SearchMethod{

    @Override
    public GameState run(GameState start) {
        super.reset();

        super.startTime = System.currentTimeMillis();
        int depth = 0;
        DFS dfs = new DFS();
        GameState solution;
        do{
            solution = dfs.run(start,depth++);
            super.expandedNodes += dfs.expandedNodes;
        }while (solution == null);
        super.endTime = System.currentTimeMillis();
        super.frontierCount = dfs.frontierCount;
        return solution;
    }


    @Override
    public String getName() {
        return "IDDFS";
    }
}