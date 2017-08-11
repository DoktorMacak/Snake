package com.semblergames.snake.utilities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.semblergames.snake.main;

public class Button {

    private Texture texture;

    private float x;
    private float y;

    private float width;
    private float height;

    private boolean flipped;

    private float scale;

    private static final float MIN_SCALE = 0.8f;

    private static final float OFFSET_X = 50*main.SCALEX;
    private static final float OFFSET_Y = 40*main.SCALEY;

    private boolean pressed;

    public Button(Texture texture){
        this.texture = texture;

        width = (float)texture.getWidth()*main.SCALEX;
        height = (float)texture.getHeight()*main.SCALEY;

        pressed = false;

        flipped = false;

        scale = 1;
    }

    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void setFlipped(boolean flipped){
        this.flipped = flipped;
    }

    public void update(float delta){
        if(pressed){
            scale -= 0.9*delta;
            if(scale < MIN_SCALE){
                scale = MIN_SCALE;
            }
        }else{
            scale += 0.9*delta;
            if(scale > 1){
                scale = 1;
            }
        }
    }

    public void handleDown(int x, int y){
        if(isInside(x,y)){
            pressed = true;
        }else{
            pressed = false;
        }
    }

    public boolean handleUp(int x, int y){
        pressed = false;
        return isInside(x,y);
    }

    public boolean isInside(int x, int y){

        float maxX = this.x + width*scale/2 + OFFSET_X;
        float minX = this.x - width*scale/2 - OFFSET_X;
        float maxY = this.y + height*scale/2 + OFFSET_Y;
        float minY = this.y - height*scale/2 - OFFSET_Y;

        return x > minX && x < maxX && y > minY && y < maxY;

    }


    public void draw(SpriteBatch batch){

        batch.draw(
                texture,
                x - width*scale/2,
                y - height*scale/2,
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
                false,flipped
        );

    }

}
