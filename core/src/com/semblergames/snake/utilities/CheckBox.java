package com.semblergames.snake.utilities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.semblergames.snake.main;

public class CheckBox {

    private static final float OFFSET_X = 50* main.SCALEX;
    private static final float OFFSET_Y = 40*main.SCALEY;

    private static final float MIN_SCALE = 0.8f;

    private float x;
    private float y;

    private float width;
    private float height;

    private float scale;

    private boolean pressed;

    private boolean checked;

    private Texture checkedTexture;
    private Texture unCheckedTexture;

    public CheckBox(Texture checkedTexture, Texture unCheckedTexture) {
        this.checkedTexture = checkedTexture;
        this.unCheckedTexture = unCheckedTexture;

        width = (float)checkedTexture.getWidth()*main.SCALEX;
        height = (float)checkedTexture.getHeight()*main.SCALEY;

        pressed = false;

        scale = 1;

        checked = false;
    }

    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
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
        if(isInside(x,y)){
            checked = !checked;
            return true;
        }
        return false;
    }

    public boolean isInside(int x, int y){

        float maxX = this.x + width*scale/2 + OFFSET_X;
        float minX = this.x - width*scale/2 - OFFSET_X;
        float maxY = this.y + height*scale/2 + OFFSET_Y;
        float minY = this.y - height*scale/2 - OFFSET_Y;

        return x > minX && x < maxX && y > minY && y < maxY;

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


    public void draw(SpriteBatch batch){

        Texture texture;

        if(checked){
            texture = checkedTexture;
        }else{
            texture = unCheckedTexture;
        }

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
                false,false
        );
    }
}
