package ui;

import game.GameState;
import game.cell.Cell;
import static game.Constants.*;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

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

    public ArrayList<Integer> dfs(ArrayList<Integer> solution, GameState game, int direction, HashSet<String> history){
        GameState auxiliarGame = new GameState();
        completeBoard(solution,auxiliarGame);
        String code = boardHash(auxiliarGame);
        System.out.printf("Player x:%d y:%d. %s\n\n",game.player.x,game.player.y,auxiliarGame.player.x,auxiliarGame.player.y,code);

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
            auxiliarGame.moveDown();
        System.out.printf("%s AFTER DOWN %s\n",code,boardHash(auxiliarGame));

        //chequeo que haya habido un cambio == se pudo realizar la acción
            if(!boardCompare(auxiliarGame.board, game.board)){
               if(!history.contains(boardHash(auxiliarGame))) {
                   System.out.printf("MOVE DOWN AND HAS BEEN A CHANGE. Player x1:%d y1:%d x2:%d y2:%d.\n\n", game.player.x, game.player.y, auxiliarGame.player.x, auxiliarGame.player.y);
                   solution.add(down);
                   history.add(boardHash(auxiliarGame));
                   solution = dfs(solution, auxiliarGame, down, history);
                   if (finished == 1) {
                       return solution;
                   } else if (finished == 2) {
                       System.out.printf("DIDNT WORK DOWN x2:%d y2:%d --> %s\n\n", auxiliarGame.player.x, auxiliarGame.player.y,code);
                       solution.remove(solution.size() - 1);
                       finished = 0;
                       completeBoard(solution,auxiliarGame);
                   }
               }else{
                   completeBoard(solution,auxiliarGame);
               }
            }
            auxiliarGame.moveLeft();
            //chequeo que haya habido un cambio == se pudo realizar la acción
            if(!boardCompare(auxiliarGame.board, game.board)){
                System.out.printf("%s --> AFTER left %s\n",code,boardHash(auxiliarGame));
               if(!history.contains(boardHash(auxiliarGame))) {
                   history.add(boardHash(auxiliarGame));
                   System.out.printf("MOVE LEFT AND HAS BEEN A CHANGE. Player x1:%d y1:%d x2:%d y2:%d.\n\n", game.player.x, game.player.y, auxiliarGame.player.x, auxiliarGame.player.y);
                   solution.add(left);
                   solution = dfs(solution, auxiliarGame, left, history);
                   if (finished == 1) {
                       return solution;
                   } else if (finished == 2) {
                       System.out.printf("DIDNT WORK LEFT x2:%d y2:%d. --> %s\n\n", auxiliarGame.player.x, auxiliarGame.player.y,code);
                       solution.remove(solution.size() - 1);
                       finished = 0;
                       completeBoard(solution,auxiliarGame);
                   }
               } else {
                   completeBoard(solution,auxiliarGame);
               }
            }
            auxiliarGame.moveUp();
            System.out.printf("%s --> AFTER up %s\n",code,boardHash(auxiliarGame));
        //chequeo que haya habido un cambio == se pudo realizar la acción
            if(!boardCompare(auxiliarGame.board, game.board)){
                if(!history.contains(boardHash(auxiliarGame))) {
                    history.add(boardHash(auxiliarGame));
                    System.out.printf("MOVE UP AND HAS BEEN A CHANGE. Player x1:%d y1:%d x2:%d y2:%d.\n\n", game.player.x, game.player.y, auxiliarGame.player.x, auxiliarGame.player.y);
                    solution.add(up);
                    solution = dfs(solution, auxiliarGame, up, history);
                    if (finished == 1) {
                        return solution;
                    } else if (finished == 2) {
                        System.out.printf("DIDNT WORK UP x2:%d y2:%d. --> %s\n\n", auxiliarGame.player.x, auxiliarGame.player.y,code);
                        solution.remove(solution.size() - 1);
                        finished = 0;
                        completeBoard(solution,auxiliarGame);
                    }
                }else{
                    completeBoard(solution,auxiliarGame);
                }
            }
            auxiliarGame.moveRight();
        System.out.printf("%s --> AFTER right %s\n",code,boardHash(auxiliarGame));

        //chequeo que haya habido un cambio == se pudo realizar la acción
            if(!boardCompare(auxiliarGame.board, game.board)){
                if(!history.contains(boardHash(auxiliarGame))) {
                    System.out.printf("MOVE RIGHT AND HAS BEEN A CHANGE. Player x1:%d y1:%d x2:%d y2:%d.\n\n", game.player.x, game.player.y, auxiliarGame.player.x, auxiliarGame.player.y);
                    history.add(boardHash(auxiliarGame));
                    solution.add(right);
                    solution = dfs(solution, auxiliarGame, right, history);
                    if (finished == 1) {
                        return solution;
                    } else if (finished == 2) {
                        System.out.printf("DIDNT WORK RIGHT x2:%d y2:%d. --> %s\n\n", auxiliarGame.player.x, auxiliarGame.player.y,code);
                        solution.remove(solution.size() - 1);
                        finished = 0;
                        completeBoard(solution,auxiliarGame);
                    }
                } else {
                    completeBoard(solution,auxiliarGame);
                }
            }
        finished = 2;
        return solution;
    }
    String boardHash(GameState game){
        return Integer.toString(game.player.x) + Integer.toString(game.player.y) + '-' +
               Integer.toString(game.bags.get(0).x) + Integer.toString(game.bags.get(0).y) + '-' +
               Integer.toString(game.bags.get(1).x) + Integer.toString(game.bags.get(1).y)  ;
    }
    void completeBoard(ArrayList<Integer> solution, GameState auxiliarGame){
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
    }
    public ArrayList<Integer> getSolution() {
        ArrayList<Integer> solution = new ArrayList<>();
        HashSet<String> history = new HashSet<>();
        history.add(boardHash(game));
        return dfs(solution,game,-1,history);
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
