package game;

import game.cell.*;

import java.util.Random;

import static game.Constants.*;

public class GameState implements GameOptions {
    public Cell[][] board;
    public Player player;


    public String[] map1 =     {"      ###      ",
                                "      #.#      ",
                                "  #####.#####  ",
                                " ##         ## ",
                                "##  # # # #  ##",
                                "#  ##     ##  #",
                                "# ##  # #  ## #",
                                "#     $@$     #",
                                "####  ###  ####",
                                "   #### ####   ",};

    public GameState() {
        resetGame();
    }

    public void moveRandomlyPlayer() {
        Random rand = new Random();
        int x = rand.nextInt(4);

        switch (x % 4) {
            case 0: moveDown();
            break;
            case 1: moveUp();
            break;
            case 2: moveLeft();
            break;
            case 3: moveRight();
            break;
        }
    }

    public void moveDown() {
        if (player.y < BOARD_SIZE - 1 && isCellEmpty(player.x, player.y + 1)) {
            swap(player.x, player.y, player.x, player.y + 1);
            player.y++;
        }
    }

    public void moveUp() {
        if (player.y > 0 && isCellEmpty(player.x, player.y - 1)) {
            swap(player.x, player.y, player.x, player.y - 1);
            player.y--;
        }
    }

    public void moveLeft() {
        if (player.x > 0 && isCellEmpty(player.x - 1, player.y)) {
            swap(player.x, player.y, player.x - 1, player.y);
            player.x--;
        }
    }

    public void moveRight() {
        if (player.x < BOARD_SIZE - 1 && isCellEmpty(player.x + 1, player.y)) {
            swap(player.x, player.y, player.x + 1, player.y);
            player.x++;
        }
    }

    public boolean isCellEmpty(int i, int j) {
        if (i < 0 || i >= BOARD_SIZE || j < 0 || j >= BOARD_SIZE) {
            return false;
        }
        return board[i][j].isEmpty();
    }

    public synchronized void swap(int i, int j, int ii, int jj) {
        Cell temp = board[i][j];
        board[i][j] = board[ii][jj];
        board[ii][jj] = temp;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    public boolean isOver() {
        return false;
    }

    public void resetGame() {
        board = new Cell[WIDTH][HEIGHT];
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                board[i][j] = new EmptyCell();
            }
        }

        for (int j = 0; j < map1.length; j++) {
            for (int i = 0; i < map1[j].length(); i++) {
                switch (map1[j].charAt(i)) {
                    case '.': board[i][j] = new GoalCell();
                    break;
                    case '@':
                        player = new Player();
                        player.x = i;
                        player.y = j;
                        board[i][j] = player;
                        break;
                    case '$':
                        board[i][j] = new Bag();
                        break;
                    case '#':
                        board[i][j] = new Wall();
                        break;
                    default:
                        break;
                }
            }
        }
    }

}
