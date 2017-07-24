package com.semblergames.snake.gamePackage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.semblergames.snake.main;
import com.semblergames.snake.utilities.Camera;
import com.semblergames.snake.utilities.Direction;

import java.util.ArrayList;
import java.util.Random;

public class PlayingRegion {

    public static final int EMPTY = -1;
    public static final int WALL = 1;

    public static final int FILLED = 12;


    private Color wallColor;

    private int [][]field;



    public PlayingRegion(int type){
        field = new int[8][9];
        wallColor = new Color(Color.GRAY);
        if(type == FILLED) {
            init();
        }
    }

    public void init(){
        clear();

        Random random = new Random();

        int patID = random.nextInt(PlayState.patterns.size());
        Pattern pattern = PlayState.patterns.get(patID);

        int[] xs = pattern.getXs();
        int[] ys = pattern.getYs();

        boolean flippedX = random.nextBoolean();
        boolean flippedY = random.nextBoolean();

        for (int u = 0; u < pattern.getAmount(); u++) {

            int y = ys[u];
            int x = xs[u];

            if (flippedX) {
                x = Math.abs(x - 8);
            }

            if (flippedY) {
                y = Math.abs(y - 7);
            }

            field[y][x] = WALL;
        }

    }

    private void generateRegion(Random random, int x, int y){
        int amount = random.nextInt(6)+2;

        int []xs = new int[amount];

        int []ys = new int[amount];

        int counter = 0;

        field[y][x] = WALL;

        xs[counter] = x;
        ys[counter] = y;

        counter++;

        boolean failed = false;

        int prevD = -1;

        for(int i = 1; i < amount && !failed; i++){
            int direction = random.nextInt(4);
            while(prevD == direction+2 || prevD == direction -2){
                direction = random.nextInt(4);
            }
            prevD = direction;
            switch(direction){
                case 0:
                    if(y+1 < 32 && field[y+1][x] == EMPTY){
                        y++;
                    }else{
                        failed =  true;
                    }
                    break;
                case 1:
                    if(x+1 < 18 && field[y][x+1] == EMPTY){
                        x++;
                    }else{
                        failed = true;
                    }
                    break;
                case 2:
                    if(y-1 >= 0 && field[y-1][x] == EMPTY){
                        y--;
                    }else{
                        failed = true;
                    }
                    break;
                case 3:
                    if(x-1 >= 0 && field[y][x-1] == EMPTY){
                        x--;
                    }else{
                        failed = true;
                    }
                    break;
            }

            if(!failed){
                field[y][x] = WALL;
                xs[counter] = x;
                ys[counter] = y;
                counter++;
            }
        }

        for(int i = 0; i < counter; i++){
            for(int k = -1; k < 2;k++){
                for(int l = -1; l < 2;l++){
                    int posx = xs[i]+k;
                    int posy = ys[i]+l;

                    if(posx >= 0 && posx < 18 && posy >= 0 && posy < 32 && field[posy][posx] == EMPTY){
                       // field[posy][posx] = TAKEN;
                    }
                }
            }
        }

    }

    private void clear(){
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 9; j++){
                field[i][j] = EMPTY;
            }
        }
    }

    public void draw(int xGrid, int yGrid, ShapeRenderer renderer, Camera camera){
        renderer.setColor(wallColor);
        float width = main.BLOCK_WIDTH;
        float height = main.BLOCK_HEIGHT;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 9; j++){
                if(field[i][j] == WALL){
                    renderer.rect(((float)(j + xGrid*9)-camera.getX())*width, ((float)(i + yGrid*9)-camera.getY()) * height, width, height);
                }
            }
        }
    }

}
