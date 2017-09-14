package com.semblergames.snake.gamePackage;


import com.semblergames.snake.utilities.Direction;

public class Segment {
    /**
     * position on the grid
     */
    private int x;
    private int y;
    private Direction orientation;

    private boolean hFlip;
    private boolean vFlip;
    private float rotation;

    public Segment (int x, int y, Direction orientation){
        this.x = x;
        this.y = y;
        this.orientation = orientation;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move(int dx, int dy) {

        x+=dx;
        y+=dy;

    }

    public Direction getOrientation() {
        return orientation;
    }

    public Segment corner(boolean hFlip, boolean vFlip){
        this.hFlip = hFlip;
        this.vFlip = vFlip;
        return this;
    }

    public Segment body(float rotation){
        this.rotation = rotation;
        return this;
    }

    public boolean getHFlip(){
        return hFlip;
    }

    public boolean getVFlip(){
        return vFlip;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
}