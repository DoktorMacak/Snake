package com.semblergames.snake.utilities;

import com.semblergames.snake.gamePackage.Snake;

public class Camera {

    private float x;
    private float y;

    private float speedX;
    private float speedY;

    public Camera(float x, float y){
        this.x = x;
        this.y = y;
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
        x = (float)snake.getHeadSegment().getX() - 8.5f;
        y = (float) snake.getHeadSegment().getY() - 15.5f;
    }

}
