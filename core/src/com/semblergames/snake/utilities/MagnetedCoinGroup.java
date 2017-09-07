package com.semblergames.snake.utilities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.semblergames.snake.fieldPackage.Field;
import com.semblergames.snake.gamePackage.Snake;
import com.semblergames.snake.main;

public class MagnetedCoinGroup {

    private static final float MIN_DISTANCE = 64* main.BLOCK_WIDTH*main.BLOCK_HEIGHT + 5;

    private MagnetedCoin [] magnetedCoins;

    private int size;


    public MagnetedCoinGroup(){

        magnetedCoins = new MagnetedCoin[20];
        for(int i = 0; i < 20;i++){
            magnetedCoins[i] = new MagnetedCoin();
        }

        size = 0;

    }

    public void clear(){
        size = 0;

    }

    public void add(float x, float y, float headX, float headY, float speed){
        magnetedCoins[size].set(x,y,headX, headY,speed);
        size++;
    }

    public void update(float delta){
        for(int i =0; i < size;i++){
            magnetedCoins[i].update(delta);
        }
    }

    public void draw(SpriteBatch batch){
        for(int i =0; i < size;i++){
            magnetedCoins[i].draw(batch);
        }
    }

    public boolean processField(Field field, float headX, float headY, float speed){
        float dx = field.getX() - headX;
        float dy = field.getY() - headY;
        if(dx*dx + dy*dy < MIN_DISTANCE){
            add(field.getX(), field.getY(), headX, headY, speed);
            return true;
        }
        return false;
    }

}
