package com.semblergames.snake.gamePackage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.semblergames.snake.main;
import com.semblergames.snake.utilities.Direction;

import java.util.ArrayList;
import java.util.Random;

public class PlayingRegion {

    public static final int EMPTY = -1;
    public static final int WALL = 1;

    private Color wallColor;

    private int relativeX;
    private int relativeY;

    private int [][]field;

    public PlayingRegion(){
        field = new int[32][18];
        wallColor = new Color(Color.GRAY);
        init();
    }

    public void init(){
        clear();


        Random random = new Random();

        for(int i = 0; i < 4;i++){
            for(int j = 0;j<2;j++){
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

                    field[i * 8 + y][j * 9 + x] = WALL;
                }
            }
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
        for(int i = 0; i < 32; i++){
            for(int j = 0; j < 18; j++){
                field[i][j] = EMPTY;
            }
        }
    }

    public void setRelativePos(int relativeX, int relativeY){
        this.relativeX = relativeX;
        this.relativeY = relativeY;
    }

    public void move(Direction direction){

    }

    public void setWall(int row, int column){
        field[row][column] = WALL;
    }

    public void drawRegion(ShapeRenderer renderer){
        renderer.setColor(wallColor);
        float width = main.BLOCK_WIDTH;
        float height = main.BLOCK_HEIGHT;
        for(int i = 0; i < 32; i++){
            for(int j = 0; j < 18; j++){
                if(field[i][j] == WALL){
                    renderer.rect((relativeX+j)*width, (relativeY + i) * height, width, height);
                }
            }
        }
    }

}
