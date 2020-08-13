package ui;

import game.GameState;

import java.util.ArrayList;

public class MainDFS {
    public static void main(String[] args) {
        GameState gameState = new GameState();
        gameState.resetGame();
        DFS dfs = new DFS(gameState);
        ArrayList<Integer> solution = dfs.getSolution();
        for (int direction:
                solution) {
            System.out.print(direction);

        }

    }

}
