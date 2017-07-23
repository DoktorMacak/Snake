package com.semblergames.snake.gamePackage;



public class Segment {
    /**
     * position on the grid
     */
    private int x;
    private int y;

    public Segment (int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}