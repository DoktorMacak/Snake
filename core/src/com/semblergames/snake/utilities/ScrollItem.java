package com.semblergames.snake.utilities;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.semblergames.snake.main;

public class ScrollItem {


    private static final float STAR_TO_CENTRE_OFFSET_Y = 135 * main.SCALEY;

    private static final float IMAGE_TO_CENTRE_OFFSET_Y = 45*main.SCALEY;

    public static final float TOTAL_HEIGHT = 330*main.SCALEY;
    private static final float TOTAL_WIDTH = 240*main.SCALEX;

    private static final float TEXT_TO_CENTRE_BOTTOM_OFFSET = 105 * main.SCALEY;


    public static final float OFFSET_BETWEEN = 17*main.SCALEX;

    public static final float OFFSET_TO_NEXT = 60*main.SCALEY;

    private float priceWidth;

    private float centerX;
    private float centerY;

    public ScrollItem(int pos, float y, BitmapFont font){

        int xPos = (pos % 3);
        int yPos = (pos/ScrollView.COLUMNS);

        GlyphLayout layout = new  GlyphLayout(font, Integer.toString(GameData.SKIN_PRICES[pos]));

        priceWidth = layout.width + OFFSET_BETWEEN + OFFSET_TO_NEXT;

        float diff = OFFSET_TO_NEXT + TOTAL_HEIGHT;


        centerX = main.WIDTH / 2;
        if(xPos == 0){
            centerX -= 300*main.SCALEX;
        }else if(xPos == 2){
            centerX += 300*main.SCALEX;
        }

        centerY = (y - TOTAL_HEIGHT/2) - diff*yPos;
    }

    public void move(float deltaY){
        centerY += deltaY;
    }

    public float getImageX(){
        return centerX;
    }

    public float getImageY(){
        return centerY + IMAGE_TO_CENTRE_OFFSET_Y;
    }

    public float getStarX(){
        return centerX + priceWidth/2 - OFFSET_TO_NEXT/2;
    }

    public float getStarY(){
        return centerY - STAR_TO_CENTRE_OFFSET_Y;
    }

    public float getTextX(){
        return centerX - priceWidth/2;
    }

    public float getTextY(){
        return centerY - TEXT_TO_CENTRE_BOTTOM_OFFSET;
    }

    public boolean isInside(int x, int y){
        return x > centerX - TOTAL_WIDTH/2 && x < centerX + TOTAL_WIDTH/2 && y > centerY - TOTAL_HEIGHT/2 && y < centerY + TOTAL_HEIGHT/2;
    }

}
