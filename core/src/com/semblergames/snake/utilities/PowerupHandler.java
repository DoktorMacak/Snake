package com.semblergames.snake.utilities;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.semblergames.snake.main;

public class PowerupHandler {

    private ImageShow imageShow;

    private boolean active;
    private float timeLapsed;

    private float duration;

    private boolean finished;

    private float activeTime;
    private boolean activeVisible;

    private Texture activeTexture;

    private float activeX;
    private float activeY;

    public PowerupHandler(Texture [] textures, Direction direction){
        activeTexture = textures[2];
        Texture [] t = new Texture[2];
        t[0] = textures[0];
        t[1] = textures[1];
        imageShow = new ImageShow(t);
        active = false;
        timeLapsed = 0;
        duration = 8;
        activeTime = 0;
        activeVisible = true;
        finished = false;
        switch (direction){
            case left:{
                imageShow.setCentre(main.WIDTH - 210*main.SCALEX, main.HEIGHT - 90*main.SCALEY);
                activeX = main.WIDTH /2 - 240*main.SCALEX;
                activeY = main.HEIGHT/2 - 45*main.SCALEY;
                break;
            }
            case right:{
                imageShow.setCentre(main.WIDTH - 90*main.SCALEX, main.HEIGHT - 90*main.SCALEY);
                activeX = main.WIDTH /2 + 150*main.SCALEX;
                activeY = main.HEIGHT/2 - 45*main.SCALEY;
                break;
            }
        }

    }

    public void reset(){
        active = false;
    }

    public void activate(){
        timeLapsed = 0;
        activeTime = 0;
        active = true;
        activeVisible = true;
        finished = false;
        imageShow.setCurrent(1);
    }

    public void update(float delta){
        if(active){
            timeLapsed += delta;
            if(timeLapsed > duration){
                active = false;
                finished = true;
                imageShow.setCurrent(0);
            }
            if(timeLapsed > 5){
                activeTime+= delta;
                if(activeTime > 0.3f){
                    activeTime = 0;
                    activeVisible = !activeVisible;
                }
            }
        }
    }

    public boolean isFinished(){
        if(finished){
            finished = false;
            return true;
        }
        return false;
    }

    public void draw(SpriteBatch batch){
        if(active && activeVisible){

            batch.draw(
                    activeTexture,
                    activeX,
                    activeY,
                    activeX,
                    activeY,
                    (float)activeTexture.getWidth()*main.SCALEX,
                    (float)activeTexture.getHeight()*main.SCALEY,
                    1,
                    1,
                    0,
                    0,
                    0,
                    activeTexture.getWidth(),
                    activeTexture.getHeight(),
                    false,false
            );

        }
        imageShow.draw(batch);
    }

    public boolean isActive() {
        return active;
    }

    public float getTimeLapsed(){
        return timeLapsed;
    }
}
