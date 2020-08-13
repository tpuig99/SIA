package game;

import game.cell.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static game.Constants.*;

public class GameState implements Comparable<GameState>{
    private GameBoard board;
    private Point player;
    private GameState parent;
    private Direction directionToParent;
    private List<Point> bags;
    private int sorting;

    public int getWidth() {
        return width;
    }

    private int width;
    private int height;
    private String map;

    public GameState() {
        this.bags = new ArrayList<>();
        this.player = null;
        this.board = null;
        this.parent = null;
        this.sorting = 0;

    }

    public GameState(int width, int height) {
        this();
        this.height = height;
        this.width = width;
    }

    public GameState(int width, int height, String map) {
        this();
        this.height = height;
        this.width = width;
        this.map = map;

        String[] line = map.split("\n");
        for (int i = 0; i < line.length; i++) {
            char[] aux = line[i].toCharArray();
            for (int j = 0; j < aux.length; j++) {
                switch (aux[j]) {
                    case '@':
                        player = (new Point(j, i));
                        break;
                    case '$':
                        addBag(j, i);
                        break;
                    default:
                        break;
                }
            }
        }

        this.board = new GameBoard(width, height, map);

    }

    public boolean isGoalCell(int x, int y) {
        for (Point goal : getBoard().getGoals()) {
            if ((int) goal.getX() == x && (int) goal.getY() == y) {
                return true;
            }
        }
        return false;
    }

    public void addBag (int x, int y) {
        bags.add(new Point(x, y));
    }

    private boolean checkBagInPosition(int x, int y) {
        for (Point bag : bags) {
            if (bag.getX() == x && bag.getY() == y) {
                return true;
            }
        }
        return false;
    }

    private boolean checkObstacleInPosition(int x, int y) {
        return board.checkObstacleInPosition(x, y) || checkBagInPosition(x, y);
    }


    public GameState moveInDirection (Direction direction) {
        switch (direction) {
            case up:
                return move(0,-1);
            case down:
                return move(0,1);
            case left:
                return move(-1,0);
            case right:
                return move(1,0);
        }
        return null;
    }

    public List<GameState> getAdjacentStates() {
        List<GameState> adjacents = new ArrayList<>();

        for (Direction direction : Direction.values()) {
            GameState aux = moveInDirection(direction);
            if (aux != null) {
                adjacents.add(aux);
            }
        }
        return adjacents;
    }

    public GameState move(int incX, int incY) {
        int newX = ((int) player.getX()) + incX; // si es negativo retrocede
        int newY = ((int) player.getY()) + incY;

        if (!checkObstacleInPosition(newX,newY) || checkBagInPosition(newX,newY) && !checkObstacleInPosition(newX + incX, newY + incY)) {
            GameState gameState = new GameState();
            gameState.setWidth(width);
            gameState.setHeight(height);
            gameState.setMap(map);

            for (Point bag : bags) {
                int x = (int) bag.getX();
                int y = (int) bag.getY();
                if (x == newX && y == newY) {
                    x += incX;
                    y += incY;
                }
                gameState.addBag(x, y);
            }
            gameState.setPlayer(newX, newY);
            gameState.setBoard(new GameBoard(width, height, map));
            return gameState;
        }
        return null;
    }

    public boolean solved() {
        for(Point bag: bags) {
            if (!isGoalCell((int) bag.getX(), (int) bag.getY())) {
                return false;
            };
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 31 + (int) player.getX();
        result = 31 * result + (int) player.getY();

        for (int j = 0; j < board.getHeight(); j++) {
            for (int i = 0; i < board.getWidth(); i++) {
                if (checkBagInPosition(i, j)) {
                    result = 31 * result + i;
                    result = 31 * result + j;
                }
            }
        }
        return result;
    }

    public Cell[][] getFullBoard () {
        Cell[][] gameBoard = getBoard().getCells();
        int x = (int) player.getX();
        int y = (int) player.getY();
        gameBoard[x][y] = Cell.PLAYER;

        for (Point bag : bags) {
            int bagX = (int) bag.getX();
            int bagY = (int) bag.getY();
            gameBoard[bagX][bagY] = Cell.BAG;
        }
        return gameBoard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof GameState) {
            GameState gs = (GameState) o;
            if (gs.board != this.board) return false;
            if (!player.equals(gs.player)) return false;
            for (Point bag: bags) {
                if (!gs.bags.contains(bag)) return false;
            }
            return true;
        }
        return false;
    }

    public GameBoard getBoard() { return board;    }
    public void setBoard(GameBoard board) { this.board = board; }

    public Point getPlayer() { return player; }
    public void setPlayer(int x, int y) { this.player = new Point(x, y); }

    public GameState getParent() { return parent; }
    public void setParent(GameState parent) { this.parent = parent; }

    public Direction getDirectionToParent() { return directionToParent; }
    public void setDirectionToParent(Direction directionToParent) { this.directionToParent = directionToParent; }

    public List<Point> getBags() { return bags; }
    public void setBags(List<Point> bags) { this.bags = bags; }

    public List<Point> getGoals() { return board.getGoals(); }

    public int getSorting() { return sorting; }
    public void setSorting(int sorting) { this.sorting = sorting; }


    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    @Override
    public int compareTo(GameState o) {
        return o.getSorting() - sorting;
    }
}
