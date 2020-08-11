package ui;

import game.GameState;
import game.cell.Cell;
import static game.Constants.*;


import java.util.ArrayList;

public class DFS {
    public static final int down = 0;
    public static final int up = 1;
    public static final int left = 2;
    public static final int right = 3;
    GameState game;
    Cell[][] initBoard;
    int finished = 0; //0 not, 1 won, 2 lose

    public DFS(GameState game) {
        this.game = game;
    }

    public ArrayList<Integer> dfs(ArrayList<Integer> solution, GameState game,int direction){
        GameState auxiliarGame = new GameState();
        auxiliarGame.resetGame();
        for (int dir:solution) {
            switch (dir){
                case up:{
                    auxiliarGame.moveUp();
                    break;
                }
                case down:{
                    auxiliarGame.moveDown();
                    break;
                }
                case left:{
                    auxiliarGame.moveLeft();
                    break;
                }
                case right:{
                    auxiliarGame.moveRight();
                    break;
                }
            }
        }
        System.out.printf("Player x1:%d y1:%d. x2:%d y2:%d.\n\n",game.player.x,game.player.y,auxiliarGame.player.x,auxiliarGame.player.y);

        //encontré una solución
        if(auxiliarGame.won()){
            System.out.println("WON!");
            finished = 1;
            return solution;
        }
        //se trabaron las bolsas
        if(!auxiliarGame.isMovable()){
            System.out.println("NOT MOVABLE!");
            finished = 2;
            return solution;
        }
        //si hago la acción opuesta de la que vengo, vuelvo al mismo lugar
        if(direction!=up){
            auxiliarGame.moveDown();
            //chequeo que haya habido un cambio == se pudo realizar la acción
            if(!boardCompare(auxiliarGame.board, game.board)){
                System.out.printf("MOVE DOWN AND HAS BEEN AN CHANGE. Player x1:%d y1:%d x2:%d y2:%d.\n\n",game.player.x,game.player.y,auxiliarGame.player.x,auxiliarGame.player.y);
                solution.add(down);
                solution= dfs(solution,auxiliarGame,down);
                if(finished == 1){
                    System.out.println("WE WON");
                    return solution;
                } else if(finished == 2){
                    System.out.println("DIDNT WORK");
                    solution.remove(solution.size()-1);
                    finished = 0;
                    auxiliarGame.moveUp();
                }
            }
        }
        if(direction!=down){
            auxiliarGame.moveUp();
            //chequeo que haya habido un cambio == se pudo realizar la acción
            if(!boardCompare(auxiliarGame.board, game.board)){
                System.out.printf("MOVE UP AND HAS BEEN AN CHANGE. Player x1:%d y1:%d x2:%d y2:%d.\n\n",game.player.x,game.player.y,auxiliarGame.player.x,auxiliarGame.player.y);
                solution.add(up);
                solution= dfs(solution,auxiliarGame,up);
                if(finished == 1){
                    return solution;
                } else if(finished == 2){
                    System.out.println("DIDNT WORK");
                    solution.remove(solution.size()-1);
                    finished = 0;
                    auxiliarGame.moveDown();
                }
            }
        }
        if(direction!=right){
            auxiliarGame.moveLeft();
            //chequeo que haya habido un cambio == se pudo realizar la acción
            if(!boardCompare(auxiliarGame.board, game.board)){
                System.out.printf("MOVE LEFT AND HAS BEEN AN CHANGE. Player x1:%d y1:%d x2:%d y2:%d.\n\n",game.player.x,game.player.y,auxiliarGame.player.x,auxiliarGame.player.y);
                solution.add(left);
                solution= dfs(solution,auxiliarGame,left);
                if(finished == 1){
                    return solution;
                } else if(finished == 2){
                    System.out.println("DIDNT WORK");
                    solution.remove(solution.size()-1);
                    finished = 0;
                    auxiliarGame.moveRight();
                }
            }
        }
        auxiliarGame.board = game.copyBoard();
        if(direction!=left){
            auxiliarGame.moveRight();
            //chequeo que haya habido un cambio == se pudo realizar la acción
            if(!boardCompare(auxiliarGame.board, game.board)){
                System.out.printf("MOVE RIGHT AND HAS BEEN AN CHANGE. Player x1:%d y1:%d x2:%d y2:%d.\n\n",game.player.x,game.player.y,auxiliarGame.player.x,auxiliarGame.player.y);
                solution.add(right);
                solution= dfs(solution,auxiliarGame,right);
                if(finished == 1){
                    return solution;
                } else if(finished == 2){
                    System.out.println("DIDNT WORK");
                    solution.remove(solution.size()-1);
                    finished = 0;
                    auxiliarGame.moveLeft();
                }
            }
        }
        finished = 2;
        return solution;
    }

    public ArrayList<Integer> getSolution() {
        ArrayList<Integer> solution = new ArrayList<>();
        return dfs(solution,game,-1);
    }
    boolean boardCompare(Cell[][] b1, Cell[][] b2){

        for(int width=0; width<WIDTH;width++){
            for(int height=0; height<HEIGHT;height++){
                Cell c1 = b1[width][height];
                Cell c2 = b2[width][height];
                if(!c1.equals(c2)){
                    return false;
                }
            }
        }
        return true;
    }
}
