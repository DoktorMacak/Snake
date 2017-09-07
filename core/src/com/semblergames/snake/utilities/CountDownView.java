package com.semblergames.snake.utilities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.semblergames.snake.main;

public class CountDownView {

    private Texture [] textures;

    private int pointer;

    private float timeLapsed;

    private float scale;

    private boolean finished;

    private float x;
    private float y;

    private float width;
    private float height;

    public CountDownView(Texture [] textures){
        this.textures = textures;
        timeLapsed = 0;
        pointer = textures.length-1;
        scale = 10f;
        finished = false;
        width = textures[0].getWidth()* main.SCALEX;
        height = textures[0].getHeight()*main.SCALEY;
    }

    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
    }

    public boolean isFinished(){
        return finished;
    }

    public void restart(){
        pointer = textures.length-1;
        timeLapsed = 0;
        scale = 10f;
        finished = false;
    }

    public void update(float delta){
        timeLapsed += delta;
        scale -= 9.7*delta;
        if(timeLapsed > 1){
            pointer--;
            timeLapsed = 0;
            scale = 10f;
            if(pointer < 0){
                finished = true;
                pointer = 0;
            }
        }
    }

    public void draw(SpriteBatch batch){
        Texture texture = textures[pointer];
        batch.draw(
                texture,
                x-width*scale/2,
                y-height*scale/2,
                x,
                y,
                width*scale,
                height*scale,
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
