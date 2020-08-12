package ui;

import game.Constants;
import game.GameState;
import java.util.Stack;

public class MainBFS {
    public static void main(String[] args) {
        GameState gameState = new GameState();
        gameState.resetGame();
        BFS bfs = new BFS();
        bfs.findSolutionBFS(gameState);
        Stack<Constants.Direction> solution = bfs.getSolution();

        while (!solution.empty()) {
            System.out.println(solution.pop());
        }

    }

}
