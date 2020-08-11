package game.cell;

import java.awt.*;

public class Player extends LivingCell {

    @Override
    public Color getColor() {
        return Color.BLUE;
    }

    public Cell[][] moveDown(Cell[][] board) {
        if(board[x][y+1] instanceof Bag){
            board = ((Bag) board[x][y+1]).moveDown(board);
            if(board[x][y+1] instanceof EmptyCell) {
                Cell aux = board[x][y + 1];
                board[x][y + 1] = board[x][y];
                board[x][y] = aux;
                this.y = y+1;
            }
        }
        return board;
    }

    public Cell[][] moveUp(Cell[][] board) {
        if((board[x][y-1] instanceof Bag)){
            board = ((Bag) board[x][y-1]).moveUp(board);
            if(board[x][y-1] instanceof EmptyCell) {
                Cell aux = board[x][y-1];
                board[x][y-1] = board[x][y];
                board[x][y] = aux;
                this.y = y-1;
            }
        }
        return board;
    }

    public Cell[][] moveLeft(Cell[][] board) {
        if((board[x-1][y] instanceof Bag)){
            board = ((Bag) board[x-1][y]).moveLeft(board);
            if(board[x-1][y] instanceof EmptyCell) {
                Cell aux = board[x - 1][y];
                board[x - 1][y] = board[x][y];
                board[x][y] = aux;
                this.x = x-1;
            }
        }
        return board;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Player && ((Player) obj).x == x && ((Player) obj).y==y;
    }

    public Cell[][] moveRight(Cell[][] board) {
        if((board[x+1][y] instanceof Bag)){
            board = ((Bag) board[x+1][y]).moveRight(board);
            if(board[x+1][y] instanceof EmptyCell) {
                Cell aux = board[x + 1][y];
                board[x + 1][y] = board[x][y];
                board[x][y] = aux;
                this.x = x+1;
            }
        }
        return board;
    }
}
