package com.semblergames.snake.gamePackage;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.DataInput;
import com.semblergames.snake.utilities.Direction;

import java.util.ArrayList;
import java.util.Map;

public class PlayState extends GameState {

    public static Map patterns;

    PlayingRegion pl;

    Snake snake;

    private float speed;
    private float time;

    public PlayState() {
    }

    @Override
    public void init() {
        patterns = Pattern.loadPatterns();

        pl = new PlayingRegion();
        pl.setRelativePos(0,0);
        snake = new Snake(3, Direction.up, 5, 3);
        speed = 0.8f;
        time = 0f;

    }

    @Override
    protected void initTextures() {

    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer renderer, float alpha, float delta) {
        //ShapeRenderer.ShapeType.Filled
        renderer.setAutoShapeType(true);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        time+=delta;
        snake.draw(renderer);
        pl.drawRegion(renderer);
        if (time > speed){
            snake.update();
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
