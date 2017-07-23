package com.semblergames.snake.gamePackage;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    /**
     * list of all the snake segments
     */
    private List<segment> segments = new ArrayList<segment>();

    public List<segment> getSegments() {
        return segments;
    }
}
