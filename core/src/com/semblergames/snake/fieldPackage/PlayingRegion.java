package com.semblergames.snake.fieldPackage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.semblergames.snake.gamePackage.PlayState;
import com.semblergames.snake.main;
import com.semblergames.snake.utilities.Camera;

import java.util.Random;

public class PlayingRegion {

    public static final int EMPTY = 11;
    public static final int FILLED = 12;

    public static final int width = 9;
    public static final int height = 8;

    private Field [][]field;



    public PlayingRegion(int type){
        field = new Field[height][width];
        init(type);
    }

    public void init(int type){
        Random random = new Random();

        int patID = random.nextInt(Pattern.getPatterns().size());
        Pattern pattern = Pattern.getPatterns().get(patID);

        int[] xs = pattern.getXs();
        int[] ys = pattern.getYs();

        boolean flippedX = random.nextBoolean();
        boolean flippedY = random.nextBoolean();

        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                field[i][j] = new Field(Field.EMPTY);
            }
        }
        if(type == FILLED) {

            for (int u = 0; u < pattern.getAmount(); u++) {

                int y = ys[u];
                int x = xs[u];

                if (flippedX) {
                    x = Math.abs(x - width + 1);
                }

                if (flippedY) {
                    y = Math.abs(y - height + 1);
                }

                field[y][x].setType(Field.WALL);
            }
        }

    }

    public Field getField(int x, int y){
        return field[y][x];
    }

    public void draw(int xGrid, int yGrid, SpriteBatch batch, Camera camera, float delta){
        float xPos = xGrid*width - camera.getX();
        float yPos = yGrid*height - camera.getY();
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                field[i][j].update(delta);
                field[i][j].draw(batch, (xPos+j)*main.BLOCK_WIDTH, (yPos+i)*main.BLOCK_HEIGHT);
            }
        }
    }
}
