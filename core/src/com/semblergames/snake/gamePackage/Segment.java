package com.semblergames.snake.gamePackage;


import com.semblergames.snake.utilities.Direction;

public class Segment {
    /**
     * position on the grid
     */
    private int x;
    private int y;
    private Direction orientation;

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
}