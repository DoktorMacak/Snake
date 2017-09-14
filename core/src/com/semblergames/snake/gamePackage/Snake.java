package com.semblergames.snake.gamePackage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.DistanceFieldFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.semblergames.snake.main;
import com.semblergames.snake.utilities.Camera;
import com.semblergames.snake.utilities.Direction;
import com.semblergames.snake.utilities.Skin;

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
     *snake size
     */
    private int snakeSize;
    /**
     *color of the snake
     */
    private Color snakeColor = new Color(Color.BLUE);
    /**
     *is chamge of direction available
     */
    private boolean changeAvailable = true;
    private Direction nextDirection;

    private Skin skin;
    private Segment head;
    private List<Segment> body = new ArrayList<Segment>();
    private List<Segment> corners = new ArrayList<Segment>();
    private boolean over;

    private Segment reverse;

    Texture skinTexture;

    public Snake(int initialLength, Direction initialDirection, int initialX, int initialY, Skin skin){
        this.direction = initialDirection;
        switch (direction){
            case left: for(int i = 0; i < initialLength; i++)
                segments.add(new Segment(initialX + i, initialY, direction));
                break;
            case right: for(int i = 0; i < initialLength; i++)
                segments.add(new Segment(initialX - i, initialY, direction));
                break;
            case up: for(int i = 0; i < initialLength; i++)
                segments.add(new Segment(initialX, initialY + i, direction));
                break;
            case down: for(int i = 0; i < initialLength; i++)
                segments.add(new Segment(initialX , initialY - i, direction));
                break;
        }
        this.skin = skin;
        update();
        snakeSize = initialLength;
        over = false;
    }

    public boolean update(){
        over = false;
        if(nextDirection != null && changeAvailable) {
            switch (direction){
                case up: if(nextDirection != Direction.up && nextDirection != Direction.down) setDirection(nextDirection);
                case down: if(nextDirection != Direction.up && nextDirection != Direction.down) setDirection(nextDirection);
                case left: if(nextDirection != Direction.left && nextDirection != Direction.right) setDirection(nextDirection);
                case right: if(nextDirection != Direction.left && nextDirection != Direction.right) setDirection(nextDirection);
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
                over = true;
            }
        }
        segments.add(new Segment(x, y, direction));
        if (over) segments.remove(segments.size()-1);
        if (segments.size() > snakeSize) {
            segments.remove(0);
            reverse = segments.get(0);
        }

        Direction lastOrientation;
        lastOrientation = segments.get(segments.size() - 1).getOrientation();
        head = segments.get(segments.size() - 1);
        switch (head.getOrientation()){
            case left: {
                head.setRotation(90f);
                break;
            }
            case down: {
                head.setRotation(180f);
                break;
            }
            case right: {
                head.setRotation(270f);
                break;
            }
            case up: {
                head.setRotation(0f);
                break;
            }
        }
        body.clear();
        corners.clear();
        for(int i = segments.size()-2;i>=0;i--){
            if(segments.get(i).getOrientation() == lastOrientation || i == 0){
                if(lastOrientation == Direction.left ||
                        lastOrientation == Direction.right)
                    body.add(segments.get(i).body(90f));
                else body.add(segments.get(i).body(0f));
                lastOrientation = segments.get(i).getOrientation();
            }else {
                switch (lastOrientation){
                    case up: {
                        if (segments.get(i).getOrientation() == Direction.left)
                            corners.add(segments.get(i).corner(true,true));
                        else corners.add(segments.get(i).corner(false,true));
                        break;
                    }
                    case down: {
                        if (segments.get(i).getOrientation() == Direction.left)
                            corners.add(segments.get(i).corner(true,false));
                        else corners.add(segments.get(i).corner(false,false));
                        break;
                    }
                    case left: {
                        if (segments.get(i).getOrientation() == Direction.down)
                            corners.add(segments.get(i).corner(false,true));
                        else corners.add(segments.get(i).corner(false,false));
                        break;
                    }
                    case right: {
                        if (segments.get(i).getOrientation() == Direction.up)
                            corners.add(segments.get(i).corner(true,false));
                        else corners.add(segments.get(i).corner(true,true));
                        break;
                    }
                }
                lastOrientation = segments.get(i).getOrientation();
            }
        }

        return over;
    }

    public void grow(){
        snakeSize++;
    }

    public boolean setDirection(Direction direction) {
        if (changeAvailable) {
            this.direction = direction;
            changeAvailable = false;
            return true;
        }else if(nextDirection == null){
            this.nextDirection = direction;
        }
        changeAvailable = false;
        return false;
    }

    public Direction getNextDirection(){
        return nextDirection;
    }

    public Direction getDirection(){
        return direction;
    }

    public Segment getHeadSegment(){
        return segments.get(segments.size()-1);
    }

    public Segment getSegmentForCamera(){
        return segments.get(segments.size()-2);
    }

    public void seColor(Color snakeColor) {
        this.snakeColor = snakeColor;
    }

    public void draw(SpriteBatch batch, Camera camera){
        //renderer.setColor(snakeColor);
        float width = main.BLOCK_WIDTH;
        float height = main.BLOCK_HEIGHT;


        if(over){
            skinTexture = skin.getDeadHead();
        }else {
            skinTexture  = skin.getHead();
        }
        int x = Math.round((segments.get(segments.size()-1).getX()-camera.getX()) * width);
        int y = Math.round((segments.get(segments.size()-1).getY()-camera.getY()) * height);
        batch.draw(skinTexture,x-width/2,y-height/2,width/2,height/2,width,height,1,1,head.getRotation(),0,0,skinTexture.getWidth(),
                skinTexture.getHeight(),false,false);
        skinTexture = skin.getBody();
        for(Segment s:body){
            x = Math.round((s.getX()-camera.getX()) * width);
            y = Math.round((s.getY()-camera.getY()) * height);
            batch.draw(skinTexture,x-width/2,y-height/2,width/2,height/2,width,height,1,1,s.getRotation(),0,0,skinTexture.getWidth(),
                    skinTexture.getHeight(),false,false);
        }
        skinTexture = skin.getCorner();
        for(Segment s:corners){
            x = Math.round((s.getX()-camera.getX()) * width);
            y = Math.round((s.getY()-camera.getY()) * height);
            batch.draw(skinTexture,x-width/2,y-height/2,x,y,width,height,1,1,0f,0,0,skinTexture.getWidth(),
                    skinTexture.getHeight(),s.getHFlip(),s.getVFlip());
        }

    }

    public List<Segment> getSegments() {
        return segments;
    }

    public void setskin(int skin){
        this.skin.setSkin(skin);
    }

    public void move(int dx, int dy) {

        for (Segment a:segments){
            a.move(dx,dy);
        }

    }
    public void died(){
        //segments.add(0, reverse);
        //segments.remove(segments.size()-1);
        //over = true;
    }
}
