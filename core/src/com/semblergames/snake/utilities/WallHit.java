package com.semblergames.snake.utilities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.semblergames.snake.main;

public class WallHit {

    private static final float DURATION = 0.08f;
    private static final int LENGTH = 5;

    private float x;
    private float y;

    private float width;
    private float height;

    private Direction orientation;

    private FieldAnimation animation;

    private Texture[] textures;

    private boolean visible;

    public WallHit(Texture [] textures){
        this.textures = textures;

        visible = false;

        width = (float) textures[0].getWidth()  * main.SCALEX;
        height = (float) textures[0].getHeight() * main.SCALEY;

        animation = new FieldAnimation(LENGTH, DURATION);
    }

    public void activate(){
        visible = true;
        animation.play();
    }

    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void setOrientation(Direction direction){
        orientation = direction;
    }

    public void draw(SpriteBatch batch){
        if(visible) {

            float rotation = 0;

            switch(orientation){
                case left:{
                    rotation = 180;
                    break;
                }
                case right:{
                    rotation = 0;
                    break;
                }
                case up:{
                    rotation = 90;
                    break;
                }
                case down: {
                    rotation = 270;
                    break;
                }
            }

            Texture texture = textures[animation.getCurrentFrame()];

            batch.draw(
                    texture,
                    x - width / 2,
                    y - height / 2,
                    width/2,
                    height/2,
                    width,
                    height,
                    1,
                    1,
                    rotation,
                    0,
                    0,
                    texture.getWidth(),
                    texture.getHeight(),
                    false, false
            );
        }
    }

    public void update(float delta){

        animation.update(delta);
        if(animation.isFinished()){
            animation.stop();
            visible = false;
        }
    }

}
