package com.semblergames.snake.fieldPackage;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.semblergames.snake.main;

import java.util.ArrayList;

public class FieldRenderer {

    private ArrayList<Field> walls;
    private ArrayList<Field> speedCoins;
    private ArrayList<Field> magnetCoins;
    private ArrayList<Field> standardCoins;
    private ArrayList<Field> points;

    private float minX;
    private float maxX;
    private float minY;
    private float maxY;

    public FieldRenderer(){
        walls = new ArrayList<Field>();
        speedCoins = new ArrayList<Field>();
        magnetCoins = new ArrayList<Field>();
        standardCoins = new ArrayList<Field>();
        points = new ArrayList<Field>();

        minX = -main.BLOCK_WIDTH;
        maxX = main.WIDTH + main.BLOCK_WIDTH;

        minY = -main.BLOCK_HEIGHT;
        maxY = main.HEIGHT + main.BLOCK_HEIGHT;

    }

    public void render(SpriteBatch batch){
        for(Field field:walls){
            field.draw(batch);
        }
        walls.clear();
        for(Field field:magnetCoins){
            field.draw(batch);
        }
        magnetCoins.clear();
        for(Field field:standardCoins){
            field.draw(batch);
        }
        standardCoins.clear();
        for(Field field:points){
            field.draw(batch);
        }
        points.clear();
        for(Field field:speedCoins){
            field.draw(batch);
        }
        speedCoins.clear();
    }

    public void process(Field field, float delta, float x, float y) {

        if(x > minX && x < maxX && y > minY && y < maxY)

        switch (field.getType()){
            case Field.WALL:{
                field.update(delta);
                field.setPosition(x,y);
                walls.add(field);
                break;
            }
            case Field.MAGNET_COIN:{
                field.update(delta);
                field.setPosition(x,y);
                magnetCoins.add(field);
                break;
            }
            case Field.STANDARD_COIN:{
                field.update(delta);
                field.setPosition(x,y);
                standardCoins.add(field);
                break;
            }
            case Field.SPEED_COIN:{
                field.update(delta);
                field.setPosition(x,y);
                speedCoins.add(field);
                break;
            }
            case Field.POINT_STAR:{
                field.update(delta);
                field.setPosition(x,y);
                points.add(field);
                break;
            }
        }

    }

    public ArrayList <Field> getStandardCoins(){
        return standardCoins;
    }
}
