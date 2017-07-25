package com.semblergames.snake.utilities;

import com.semblergames.snake.gamePackage.Snake;
import com.semblergames.snake.main;

public class Camera {

    private float x;
    private float y;

    private float speedX;
    private float speedY;

    public Camera(){

    }

    public void update(float timePassed){

        x+= speedX*timePassed;
        y+= speedY*timePassed;

    }

    public void move(float dx, float dy){
        x += dx;
        y += dy;
    }


    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getSpeedX() {
        return speedX;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    public void align(Snake snake){
        x = (float)snake.getHeadSegment().getX() - ((float)(main.SCREEN_WIDTH-1))/2;
        y = (float)snake.getHeadSegment().getY() - ((float)(main.SCREEN_HEIGHT-1))/2;
    }

}
