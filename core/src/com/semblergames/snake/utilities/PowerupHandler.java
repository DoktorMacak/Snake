package com.semblergames.snake.utilities;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.semblergames.snake.main;

public class PowerupHandler {

    private ImageShow imageShow;

    private boolean active;
    private float timeLapsed;

    private float duration;

    private boolean rising;
    private float activeAlpha;

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
        activeAlpha = 0;
        rising = false;
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

    public void activate(){
        timeLapsed = 0;
        activeAlpha = 1;
        rising = false;
        active = true;
        imageShow.setCurrent(1);
    }

    public void update(float delta){
        if(active){
            timeLapsed += delta;
            if(timeLapsed > duration){
                active = false;
                imageShow.setCurrent(0);
            }
            if(timeLapsed > 5){
                if(rising){
                    activeAlpha += delta*2.3f;
                    if(activeAlpha > 1){
                        activeAlpha = 1;
                        rising = false;
                    }
                }else{
                    activeAlpha -= delta*2.3f;
                    if(activeAlpha < 0){
                        activeAlpha = 0;
                        rising = true;
                    }
                }
            }
        }
    }

    public void draw(SpriteBatch batch){
        if(active){
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
}
