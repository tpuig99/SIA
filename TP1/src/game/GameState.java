package game;

import game.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static game.Constants.*;

public class GameState implements Comparable<GameState>{
    private GameBoard board;
    private Point player;
    private GameState parent;
    private Direction directionFromParent;
    private List<Point> bags;
    private int sorting;
    private int depth;

    public GameState() {
        this.bags = new ArrayList<>();
        this.player = null;
        this.board = null;
        this.parent = null;
        this.directionFromParent = null;
        this.sorting = 0;
        this.depth = 0;
    }

    public GameState(int width, int height, String map) {
        this();
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

    public boolean checkBagInPosition(int x, int y) {
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
                aux.setDirectionFromParent(direction);
            }
        }
        return adjacents;
    }

    public GameState move(int incX, int incY) {
        int newX = ((int) player.getX()) + incX; // si es negativo retrocede
        int newY = ((int) player.getY()) + incY;

        if (!checkObstacleInPosition(newX,newY) || checkBagInPosition(newX,newY) && !checkObstacleInPosition(newX + incX, newY + incY)) {
            GameState gameState = new GameState();

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
            gameState.setBoard(board);
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

    public boolean checkPosition (int x, int y) {
        return checkBagInPosition(x, y) || board.checkObstacleInPosition(x, y);
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

    public Direction getDirectionFromParent() { return directionFromParent; }
    public void setDirectionFromParent(Direction directionFromParent) { this.directionFromParent = directionFromParent; }

    public List<Point> getBags() { return bags; }
    public void setBags(List<Point> bags) { this.bags = bags; }

    public List<Point> getGoals() { return board.getGoals(); }

    public List<Point> getFreeGoals() {
        List<Point> freeGoals = new ArrayList<>();
        boolean isOccupied;
        for (Point goal : getGoals()) {
            isOccupied = false;
            for (Point bag : bags) {
                if (((int) bag.getX() == (int) goal.getX())
                        && ((int) bag.getY() == (int) goal.getY())) {
                    isOccupied = true;
                }
            }
            if (!isOccupied) {
                freeGoals.add(goal);
            }
        }
        return freeGoals;
    }

    public int getSorting() { return sorting; }
    public void setSorting(int sorting) { this.sorting = sorting; }

    public int getDepth() { return depth; }
    public void setDepth(int depth) { this.depth = depth; }

    @Override
    public int compareTo(GameState o) {
        return o.getSorting() - sorting;
    }
}
