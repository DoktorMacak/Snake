package com.semblergames.snake.gamePackage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.semblergames.snake.main;


public class GameOverState extends GameState {



    @Override
    public void init() {

    }

    @Override
    public void initTexturesAndFonts(FreeTypeFontGenerator generator) {

    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer renderer, float alpha, float delta) {
        renderer.setAutoShapeType(true);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.RED);
        renderer.rect(300, 300, main.WIDTH-600, main.HEIGHT-600);
        renderer.end();
    }

    @Override
    public void touchDown(int x, int y) {

    }

    @Override
    public void touchDragged(int x, int y) {

    }

    @Override
    public void touchUp(int x, int y) {

    }

    @Override
    public void backPressed() {

    }

    @Override
    protected void disposeTexturesAndFonts() {

    }
}
