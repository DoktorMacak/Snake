package com.semblergames.snake.utilities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.semblergames.snake.fieldPackage.Field;
import com.semblergames.snake.gamePackage.PlayState;
import com.semblergames.snake.gamePackage.Snake;
import com.semblergames.snake.main;

public class MagnetedCoin {

    private float width;
    private float height;

    private Texture texture;

    private float x;
    private float y;

    private float speedX;
    private float speedY;

    public MagnetedCoin(){

        texture = PlayState.standardCoinTextures[0];
        width = texture.getWidth()*main.SCALEX;
        height = texture.getHeight()*main.SCALEY;
    }

    public void set(float x, float y, float headX, float headY, float speed){
        setPosition(x,y);
        setSpeed(headX, headY, speed);
    }

    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void setSpeed(float headX, float headY, float speed){
        speedX = (headX - x) / speed;
        speedY = (headY - y) / speed;
    }

    public void update(float delta){
        x += speedX*delta;
        y += speedY*delta;
    }



    public void draw(SpriteBatch batch){

        batch.draw(
                texture,
                x-width/2,
                y-height/2,
                x,
                y,
                width,
                height,
                1,
                1,
                0,
                0,
                0,
                texture.getWidth(),
                texture.getHeight(),
                false,false
        );
    }


}
