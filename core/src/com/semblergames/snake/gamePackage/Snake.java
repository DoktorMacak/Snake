package com.semblergames.snake.gamePackage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.semblergames.snake.main;
import com.semblergames.snake.utilities.Camera;
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
    /**
     *color of the snake
     */
    private Color snakeColor = new Color(Color.BLUE);
    /**
     *is chamge of direction available
     */
    private boolean changeAvailable = true;
    private Direction nextDirection;

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

    public boolean update(){
        if(nextDirection != null && changeAvailable) {
            switch (direction){
                case up: if(nextDirection != Direction.up && nextDirection != Direction.down)
                    setDirection(nextDirection);
                    break;
                case down: if(nextDirection != Direction.up && nextDirection != Direction.down)
                    setDirection(nextDirection);
                    break;
                case left: if(nextDirection != Direction.left && nextDirection != Direction.right)
                    setDirection(nextDirection);
                    break;
                case right: if(nextDirection != Direction.left && nextDirection != Direction.right)
                    setDirection(nextDirection);
                    break;
            }
            nextDirection = null;
        }
        changeAvailable = true;
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
        for (Segment a:segments){
            if(a.getX() == x && a.getY() == y){
                //game over
                snakeColor = Color.RED;
                return true;
            }
        }
        segments.add(new Segment(x, y));
        if (!grow) segments.remove(0);
        grow = false;
        return false;
    }

    public void grow(){
        grow = true;
    }

    public void setDirection(Direction direction) {
        if (changeAvailable) {
            this.direction = direction;
        }else if(nextDirection == null){
            this.nextDirection = direction;

        }
        changeAvailable = false;
    }

    public Direction getDirection(){
        return direction;
    }

    public Segment getHeadSegment(){
        return segments.get(segments.size()-1);
    }

    public void seColor(Color snakeColor) {
        this.snakeColor = snakeColor;
    }

    public void draw(ShapeRenderer renderer, Camera camera){
        renderer.setColor(snakeColor);
        float width = main.BLOCK_WIDTH;
        float height = main.BLOCK_HEIGHT;
        for(Segment x:segments){
            renderer.rect((x.getX()-camera.getX()-0.5f) * width, (x.getY()-camera.getY()-0.5f) * height, width, height);
        }
    }

    public List<Segment> getSegments() {
        return segments;
    }

    public void move(int dx, int dy) {

        for (Segment a:segments){
            a.move(dx,dy);
        }

    }
}
//        changeAvailable = true;
//                if(nextDirection != null) {
//                setDirection(nextDirection);
//                nextDirection = null;
//                }
