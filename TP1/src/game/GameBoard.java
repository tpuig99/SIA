package game;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    private int width;
    private int height;
    private final Cell[][] board;
    private List<Point> goals;

    public GameBoard(int width, int height, String map) {
        this.height = height;
        this.width = width;
        board = new Cell[width][height];
        goals = new ArrayList<>();

        String[] line = map.split("\n");
        for (int i = 0; i < line.length; i++) {
            char[] aux = line[i].toCharArray();
            for (int j = 0; j < aux.length; j++) {
                switch (aux[j]) {
                    case '.':
                        board[j][i] = Cell.GOAL;
                        goals.add(new Point(j, i));
                        break;
                    case '#':
                        board[j][i] = Cell.WALL;
                        break;
                    default:
                        board[j][i] = Cell.FREE;
                        break;
                }
            }
        }
    }

    public boolean checkObstacleInPosition(int x, int y) {
        return x < 0 || y < 0 || x >= width || y >= height || (board[x][y] == Cell.WALL);
    }

    public Cell[][] getCells() { return board.clone(); }

    public Cell getCellContent(int x, int y) {
        return board[x][y];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Point> getGoals() {
        return goals;
    }
}
