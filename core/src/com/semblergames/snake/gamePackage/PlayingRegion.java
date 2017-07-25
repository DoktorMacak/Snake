package com.semblergames.snake.gamePackage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.semblergames.snake.main;
import com.semblergames.snake.utilities.Camera;
import com.semblergames.snake.utilities.Direction;
import com.sun.org.apache.regexp.internal.RE;

import java.util.ArrayList;
import java.util.Random;

public class PlayingRegion {

    public static final int EMPTY = -1;
    public static final int WALL = 1;

    public static final int FILLED = 12;

    public static final int width = 9;
    public static final int height = 8;

    private Color wallColor;

    private int [][]field;



    public PlayingRegion(int type){
        field = new int[height][width];
        wallColor = new Color(Color.GRAY);
        clear();
        if(type == FILLED) {
            init();
        }
    }

    public void init(){
        Random random = new Random();

        int patID = random.nextInt(Pattern.getPatterns().size());
        Pattern pattern = Pattern.getPatterns().get(patID);

        int[] xs = pattern.getXs();
        int[] ys = pattern.getYs();

        boolean flippedX = random.nextBoolean();
        boolean flippedY = random.nextBoolean();

        for (int u = 0; u < pattern.getAmount(); u++) {

            int y = ys[u];
            int x = xs[u];

            if (flippedX) {
                x = Math.abs(x - width + 1);
            }

            if (flippedY) {
                y = Math.abs(y - height +1);
            }

            field[y][x] = WALL;
        }

    }


    public void clear(){
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                field[i][j] = EMPTY;
            }
        }
    }

    public boolean isWall(int x, int y){
        return field[y][x] == WALL;
    }

    public void draw(int xGrid, int yGrid, ShapeRenderer renderer, Camera camera){
        renderer.setColor(wallColor);
        float w = main.BLOCK_WIDTH;
        float h = main.BLOCK_HEIGHT;
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                if(field[i][j] == WALL){
                    renderer.rect(((j + xGrid*width)-camera.getX())*w, ((i + yGrid*height)-camera.getY()) * h, w, h);
                }
            }
        }
    }

    public void drawPaint(int xGrid, int yGrid,ShapeRenderer renderer, int x, int y, Camera camera){
        Color color = renderer.getColor();
        renderer.setColor(Color.RED);
        renderer.rect(((x + xGrid*width)-camera.getX())*main.BLOCK_WIDTH, ((y + yGrid*height)-camera.getY()) * main.BLOCK_HEIGHT, main.BLOCK_WIDTH, main.BLOCK_HEIGHT);
        renderer.setColor(color);
    }

}
