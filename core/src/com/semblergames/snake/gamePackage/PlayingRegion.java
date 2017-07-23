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
    public static final int TAKEN = 0;

    private int []directions;

    private Color wallColor;

    private int relativeX;
    private int relativeY;

    private int [][]field;

    public PlayingRegion(){
        field = new int[32][18];
        wallColor = new Color(Color.RED);
        directions = new int[4];
        init();
    }

    public void init(){
        clear();

        Random random = new Random();

        int offset = random.nextInt(5);

        int counter = 0;

        for(int i = 0; i < 32;i++){
            for(int j = 0; j< 18;j++){
                counter++;
                if(counter == offset && field[i][j] == EMPTY){
                    generateRegion(random, j, i);
                    offset = random.nextInt(4)+1;
                    counter = 0;
                }
            }
        }

    }

    private void generateRegion(Random random, int x, int y){
        int amount = random.nextInt(6)+2;

        field[x][y] = WALL;

        for(int i = 0; i < amount; i++){
            int direction = random.nextInt(4);
            switch (direction){
                case 0:
                    if(field[x][y+1] == EMPTY){
                        y++;
                        field[x][y] = WALL;
                    }
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
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

    private void resetDirections(){
        for(int i = 0; i < 4; i++){
            directions[i] = 0;
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
