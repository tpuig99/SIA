package searchMethods;

import game.GameState;

import java.util.concurrent.*;

public abstract class SearchMethod {
    public GameState run(GameState start, int timeOut) {
        GameState solution = null;
        ExecutorService executor = Executors.newCachedThreadPool();
        Callable<GameState> task = () -> run(start);
        Future<GameState> future = executor.submit(task);
        try {
            solution = future.get(timeOut, TimeUnit.SECONDS);
        } catch (Exception ex) {
            endTime = System.currentTimeMillis();
            timedOut = true;
        } finally {
            future.cancel(true);
        }

        return solution;
    }

    public abstract GameState run(GameState start);

    public abstract String getName();

    public int getLatestFrontierNodeCount()  {
        return latestFrontierNodeCount;
    }

    public boolean getTimeOut() {
        return timedOut;
    }

    public int getTotalNodesExpanded() {
        return totalNodesExpanded;
    }

    public long getTimeSpentOnHeuristicMillis() {
        return totalTimeSpendOnHeuristicMillis;
    }

    public long getTotalTimeMillis() {
        return endTime - startTime;
    }

    protected int latestFrontierNodeCount = 0;
    protected int totalNodesExpanded = 0;
    protected long totalTimeSpendOnHeuristicMillis = 0;
    protected long startTime = 0;
    protected long endTime = 0;

    protected String getHeuristicName() {
        return h.getName();
    }

    protected int h(GameState s) {
        if(h == null)
            return 0;

        long startTime = System.currentTimeMillis();
        int result = h.eval(s);
        totalTimeSpendOnHeuristicMillis += System.currentTimeMillis() - startTime;

        return result;
    }

    protected void setHeuristic(Heuristic h) {
        this.h = h;
    }

    protected void resetStats() {
        latestFrontierNodeCount = 1;
        totalNodesExpanded = 1;
        totalTimeSpendOnHeuristicMillis = 0;
        endTime = 0;
        startTime = 0;
    }

    private Heuristic h = null;

    private boolean timedOut = false;
}

