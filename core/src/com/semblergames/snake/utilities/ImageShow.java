package com.semblergames.snake.utilities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.semblergames.snake.main;

public class ImageShow {

    private Texture [] textures;

    private float x;
    private float y;

    private int current;

    public ImageShow(Texture [] textures) {
        this.textures = textures;
        current = 0;
    }

    public void setCurrent(int current){
        this.current = current;
    }

    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void setCentre(float x, float y){
        this.x = x - (float)textures[current].getWidth()* main.SCALEX /2;
        this.y = y - (float)textures[current].getHeight()* main.SCALEY /2;
    }

    public void draw(SpriteBatch batch){
        batch.draw(
                textures[current],
                x,
                y,
                x,
                y,
                (float)textures[current].getWidth()*main.SCALEX,
                (float)textures[current].getHeight()*main.SCALEY,
                1,
                1,
                0,
                0,
                0,
                textures[current].getWidth(),
                textures[current].getHeight(),
                false,false
        );
    }
}
