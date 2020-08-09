package ui;

import game.GameState;

import java.util.ArrayList;

public class MainDFS {
    public static void main(String[] args) {
        GameState gameState = new GameState();
        DFS dfs = new DFS(gameState);
        ArrayList<Integer> solution = new ArrayList<>();
        solution = dfs.getSolution();
        for (int direction:
                solution) {
            System.out.print(direction);

        }

    }

}
