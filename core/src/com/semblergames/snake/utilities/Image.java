package com.semblergames.snake.utilities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.semblergames.snake.main;

public class Image {

    private Texture texture;

    private float x;
    private float y;

    public Image(Texture texture){
        this.texture = texture;
    }

    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void setCentre(float x, float y){
        this.x = x - (float)texture.getWidth()* main.SCALEX /2;
        this.y = y - (float)texture.getHeight()* main.SCALEY /2;
    }

    public void draw(SpriteBatch batch){
        batch.draw(
                texture,
                x,
                y,
                x,
                y,
                (float)texture.getWidth()*main.SCALEX,
                (float)texture.getHeight()*main.SCALEY,
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
