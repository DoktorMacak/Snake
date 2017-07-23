package com.semblergames.snake.gamePackage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.semblergames.snake.main;
import com.semblergames.snake.utilities.Direction;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    /**
     * list of all the snake segments
     */
    private List<Segment> segments = new ArrayList<Segment>();
    /**
     *current snake's moving direction
     */
    private Direction direction;
    /**
     *is snake growing
     */
    private boolean grow = false;

    public Snake(int initialLength, Direction initialDirection, int initialX, int initialY){
        this.direction = initialDirection;
        switch (direction){
            case left: for(int i = 0; i < initialLength; i++)
                segments.add(new Segment(initialX + i, initialY));
                break;
            case right: for(int i = 0; i < initialLength; i++)
                segments.add(new Segment(initialX - i, initialY));
                break;
            case up: for(int i = 0; i < initialLength; i++)
                segments.add(new Segment(initialX, initialY + i));
                break;
            case down: for(int i = 0; i < initialLength; i++)
                segments.add(new Segment(initialX , initialY - i));
                break;
        }
    }

    public void update(){
        int x = segments.get(segments.size()-1).getX();
        int y = segments.get(segments.size()-1).getY();
        switch (direction){
            case up: y++;
                break;
            case down: y--;
                break;
            case left: x--;
                break;
            case right: x++;
                break;
        }
        segments.add(new Segment(x,y));
        if (!grow) segments.remove(0);
        grow = false;
    }

    public void grow(){
        grow = true;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void draw(ShapeRenderer renderer){
        renderer.setColor(Color.BLUE);
        float width = main.BLOCK_WIDTH;
        float height = main.BLOCK_HEIGHT;
        for(Segment x:segments){
            renderer.rect(x.getX() * width, x.getY() * height, width, height);
        }
    }

    public List<Segment> getSegments() {
        return segments;
    }
}
