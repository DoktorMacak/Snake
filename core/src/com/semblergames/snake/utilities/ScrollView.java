package com.semblergames.snake.utilities;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.semblergames.snake.main;

import java.util.ArrayList;


public class ScrollView {




    private static float MAX_Y;
    private static float MIN_Y = main.HEIGHT - 200*main.SCALEY;




    public static final int COLUMNS = 3;


    private int size;

    private float y;

    private float dy;

    private float pressY;


    private boolean pressed;

    private ScrollItem [] items;


    public ScrollView(int size,BitmapFont font, float y) {
        this.size = size;

        MAX_Y = (size % 3 == 0 ? size/3:1+(size/3))*(ScrollItem.TOTAL_HEIGHT+ScrollItem.OFFSET_TO_NEXT);


        this.items = new ScrollItem[size];

        for(int i = 0; i < size;i++){
            items[i] = new ScrollItem(i,y,font);
        }

        this.y = y;
        dy = 0;

        pressed = false;
    }

    public void handleDown(int x, int y){
        if(!pressed){
            pressY = y;
            pressed = true;
            dy = y;
        }else{
            float deltaY = y - pressY;
            float lastY = this.y;
            this.y+=deltaY;


            if(this.y > MAX_Y){
                this.y = MAX_Y;
            }
            if(this.y < MIN_Y){
                this.y = MIN_Y;
            }

            for(int i = 0; i < size;i++){
                items[i].move(this.y - lastY);
            }

            pressY = y;
        }



    }

    public int handleUp(int x, int y){
        pressed = false;

        int pointer = -1;

        if(Math.abs(dy - y) < 20* main.SCALEY){
            for(int i =0; i < items.length;i++){
                if(items[i].isInside(x,y)){
                    pointer = i;
                    break;
                }
            }
        }

        return pointer;
    }

    public ScrollItem [] getItems(){
        return items;
    }

    public float getY(){
        return y;
    }

}
