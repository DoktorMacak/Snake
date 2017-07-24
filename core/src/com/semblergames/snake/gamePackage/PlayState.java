package com.semblergames.snake.gamePackage;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.DataInput;
import com.semblergames.snake.utilities.Direction;

import java.util.ArrayList;
import java.util.Map;

public class PlayState extends GameState {

    public static ArrayList <Pattern> patterns;

    private PlayingRegion[][] regions;


    Snake snake;

    private float speed;
    private float time;

    public PlayState() {
    }

    @Override
    public void init() {
        patterns = Pattern.loadPatterns();

        regions = new PlayingRegion[3][3];
        for(int i = 0; i < 3;i++){
            for (int j = 0; j < 3; j++){
                regions[i][j] = new PlayingRegion();
                regions[i][j].init();
            }
        }

        snake = new Snake(3, Direction.up, 5, 3);
        speed = 0.8f;
        time = 0f;

    }

    @Override
    protected void initTextures() {

    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer renderer, float alpha, float delta) {
        renderer.setAutoShapeType(true);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        time+=delta;
        snake.draw(renderer);
        if (time > speed){
            if(snake.update()) listener.changeState(1);
            time = 0f;
        }
        renderer.end();
    }

    @Override
    public void touchDown(int x, int y) {
    }

    @Override
    public void touchDragged(int prevX, int prevY, int x, int y){

    }

    @Override
    public void touchUp(int x, int y) {
        if (Math.abs(x) > Math.abs(y)){
            if(x > 0) snake.setDirection(Direction.left);
            else snake.setDirection(Direction.right);
        }else{
            if(y > 0) snake.setDirection(Direction.down);
            else snake.setDirection(Direction.up);
        }
        //snake.update();
    }

    @Override
    public void backPressed() {

    }

    @Override
    protected void disposeTextures() {

    }
}
