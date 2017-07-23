package com.semblergames.snake.gamePackage;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class PlayState extends GameState {

    PlayingRegion pl;

    public PlayState() {
    }

    @Override
    public void init() {
        pl = new PlayingRegion();
        pl.setRelativePos(0,0);
    }

    @Override
    protected void initTextures() {

    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer renderer, float alpha) {
        //ShapeRenderer.ShapeType.Filled
        renderer.setAutoShapeType(true);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        pl.drawRegion(renderer);
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

    }

    @Override
    public void backPressed() {

    }

    @Override
    protected void disposeTextures() {

    }
}
