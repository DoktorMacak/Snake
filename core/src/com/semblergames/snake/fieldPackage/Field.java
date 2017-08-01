package com.semblergames.snake.fieldPackage;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.semblergames.snake.gamePackage.PlayState;
import com.semblergames.snake.main;
import com.semblergames.snake.utilities.Animation;

public class Field {

    public static final int EMPTY = 0;
    public static final int WALL = 1;
    public static final int MAGNET_COIN = 2;
    public static final int SPEED_COIN = 3;
    public static final int STANDARD_COIN = 4;
    public static final int POINT_STAR = 5;


    private static final float DURATION = 0.08f;
    private static final int LENGTH = 6;

    private int type;

    private Animation animation;


    public Field(int type){
        this.type = type;

        animation = new Animation(LENGTH, DURATION);
        animation.pause();
    }

    public Animation getAnimation() {
        return animation;
    }

    public int getType(){
        return type;
    }

    public void setType(int type){
        this.type = type;
    }


    public void draw(SpriteBatch batch, float x, float y){

        Texture texture = null;

        switch(type){
            case WALL:{
                texture = PlayState.wallTextures[animation.getCurrentFrame()];
                break;
            }
            case MAGNET_COIN:{
                texture = PlayState.magnetCoinTextures[animation.getCurrentFrame()];
                break;
            }
            case SPEED_COIN:{
                texture = PlayState.speedCoinTexture[animation.getCurrentFrame()];
                break;
            }
            case STANDARD_COIN:{
                texture = PlayState.standardCoinTextures[animation.getCurrentFrame()];
                break;
            }
            case POINT_STAR:{
                texture = PlayState.pointTexture[animation.getCurrentFrame()];
                break;
            }
        }

        if(texture != null) {

            batch.draw(
                    texture,
                    x-texture.getWidth()*main.SCALEX/2,
                    y-texture.getHeight()*main.SCALEY/2,
                    x,
                    y,
                    texture.getWidth()*main.SCALEX,
                    texture.getHeight()*main.SCALEY,
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

    public void update(float delta) {

        animation.update(delta);
        if(animation.isFinished()){
            animation.stop();
            this.type = EMPTY;
        }

    }
}
