package ui;

import game.GameState;
import game.cell.Cell;

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
        GameState auxiliarGame = game;
        //encontré una solución
        if(auxiliarGame.won()){
            finished = 1;
            return solution;
        }
        //se trabaron las bolsas
        if(!auxiliarGame.isMovable()){
            finished = 2;
            return solution;
        }
        //si hago la acción opuesta de la que vengo, vuelvo al mismo lugar
        if(direction!=up){
            auxiliarGame.moveDown();
            //chequeo que haya habido un cambio == se pudo realizar la acción
            if(!auxiliarGame.board.equals(game.board)){
                solution.add(down);
                solution= dfs(solution,auxiliarGame,down);
                if(finished == 1){
                    return solution;
                } else if(finished == 2){
                    solution.remove(solution.size()-1);
                    finished = 0;
                }
            }
        }
        auxiliarGame = game;
        if(direction!=down){
            auxiliarGame.moveUp();
            //chequeo que haya habido un cambio == se pudo realizar la acción
            if(!auxiliarGame.board.equals(game.board)){
                solution.add(up);
                solution= dfs(solution,auxiliarGame,up);
                if(finished == 1){
                    return solution;
                } else if(finished == 2){
                    solution.remove(solution.size()-1);
                    finished = 0;
                }
            }
        }
        auxiliarGame = game;
        if(direction!=right){
            auxiliarGame.moveLeft();
            //chequeo que haya habido un cambio == se pudo realizar la acción
            if(!auxiliarGame.board.equals(game.board)){
                solution.add(left);
                solution= dfs(solution,auxiliarGame,left);
                if(finished == 1){
                    return solution;
                } else if(finished == 2){
                    solution.remove(solution.size()-1);
                    finished = 0;
                }
            }
        }
        auxiliarGame = game;
        if(direction!=left){
            auxiliarGame.moveRight();
            //chequeo que haya habido un cambio == se pudo realizar la acción
            if(!auxiliarGame.board.equals(game.board)){
                solution.add(right);
                solution= dfs(solution,auxiliarGame,right);
                if(finished == 1){
                    return solution;
                } else if(finished == 2){
                    solution.remove(solution.size()-1);
                    finished = 0;
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
}
