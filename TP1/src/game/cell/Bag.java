package game.cell;

import java.awt.*;

public class Bag extends LivingCell {
    public static final int down = 0;
    public static final int up = 1;
    public static final int left = 2;
    public static final int right = 3;
    @Override
    public Color getColor() {
        return Color.RED;
    }

    public Cell[][] moveDown(Cell[][] board) {
        if(canMove(board,down)){
            Cell aux = board[x][y+1];
            board[x][y+1] = board[x][y];
            board[x][y] = aux;
            this.y = y+1;
        }
        return board;
    }

    public Cell[][] moveUp(Cell[][] board) {
        if(canMove(board,up)){
            Cell aux = board[x][y-1];
            board[x][y-1] = board[x][y];
            board[x][y] = aux;
            this.y = y-1;
        }
        return board;
    }

    public Cell[][] moveLeft(Cell[][] board) {
        if(canMove(board,left)){
            Cell aux = board[x-1][y];
            board[x-1][y] = board[x][y];
            board[x][y] = aux;
            this.x = x-1;
        }
        return board;
    }

    public Cell[][] moveRight(Cell[][] board) {
        if(canMove(board,right)){
            Cell aux = board[x+1][y];
            board[x+1][y] = board[x][y];
            board[x][y] = aux;
            this.x = x+1;
        }
        return board;
    }
    public boolean canMove(Cell[][] board, int direction) {
        switch(direction){
            case down:{
                if(board[x][y+1] instanceof Wall || board[x][y+1] instanceof Bag )
                    return false;
                break;
            }
            case up:{
                if(board[x][y-1] instanceof Wall || board[x][y-1] instanceof Bag )
                    return false;
                break;
            }
            case left:{
                if(board[x-1][y] instanceof Wall || board[x-1][y] instanceof Bag )
                    return false;
                break;
            }
            case right:{
                if(board[x+1][y] instanceof Wall || board[x+1][y] instanceof Bag )
                    return false;
                break;
            }
        }
        return true;
    }
    public boolean isMovable(Cell[][] board){
        if(!(canMove(board,down) && canMove(board,left)) || !(canMove(board,down) && canMove(board,right))){
            return false;
        }
        if(!(canMove(board,up) && canMove(board,left)) || !(canMove(board,up) && canMove(board,right))){
            return false;
        }
        return true;
    }
}
