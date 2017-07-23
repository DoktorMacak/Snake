package com.semblergames.snake.gamePackage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.semblergames.snake.main;

public class PlayingRegion {

    public static final short EMPTY = -1;
    public static final short WALL = 1;

    private Color wallColor;

    private float relativeX;
    private float relativeY;

    private short [][]field;

    public PlayingRegion(){
        field = new short[32][18];
        wallColor = new Color(Color.RED);
        clear();
    }

    public void clear(){
        for(short i = 0; i < 32; i++){
            for(short j = 0; j < 18; j++){
                field[i][j] = EMPTY;
            }
        }
    }

    public void setRelativePos(float relativeX, float relativeY){
        this.relativeX = relativeX;
        this.relativeY = relativeY;
    }

    public void move(float dx, float dy){
        relativeX += dx;
        relativeY += dy;
    }

    public void setWall(short row, short column){
        field[row][column] = WALL;
    }

    public void drawRegion(ShapeRenderer renderer){
        renderer.setColor(wallColor);
        float width = main.BLOCK_WIDTH;
        float height = main.BLOCK_HEIGHT;
        for(short i = 0; i < 32; i++){
            for(short j = 0; j < 18; j++){
                if(field[i][j] == WALL){
                    renderer.rect(relativeX+j*width, relativeY + i * height, width, height);
                }
            }
        }
    }

}
