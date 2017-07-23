package com.semblergames.snake.gamePackage;

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

    public Snake(int initialLength, Direction initialDirection, int initialX, int initialY){
        this.direction = initialDirection;
        switch (direction){
            case left: for(int i = 0; i < initialLength; i++)
                    segments.add(new Segment(initialX + i, initialY));
            case right: for(int i = 0; i < initialLength; i++)
                segments.add(new Segment(initialX + i, initialY));
            case up: for(int i = 0; i < initialLength; i++)
                segments.add(new Segment(initialX, initialY + i));
            case down: for(int i = 0; i < initialLength; i++)
                segments.add(new Segment(initialX + i, initialY));
        }
    }



    public List<Segment> getSegments() {
        return segments;
    }
}
