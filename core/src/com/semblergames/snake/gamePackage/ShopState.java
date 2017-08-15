package com.semblergames.snake.gamePackage;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.semblergames.snake.main;
import com.semblergames.snake.utilities.Button;

public class ShopState extends GameState {

    private Texture menuTexture;
    private Texture lineTexture;

    private Texture [] skins;

    private Texture lockTexture;
    private Texture starTexture;
    private Texture selectedTexture;

    private BitmapFont font;


    private Button backButton;

    @Override
    public void init() {
        backButton = new Button(menuTexture);
        backButton.setPosition(100*main.SCALEX, main.HEIGHT - 100*main.SCALEY);
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer renderer, float alpha, float delta) {
        batch.begin();

        backButton.draw(batch);
        backButton.update(delta);

        batch.end();
    }

    @Override
    public void touchDown(int x, int y) {
        backButton.handleDown(x,y);
    }

    @Override
    public void touchDragged(int x, int y) {
        backButton.handleDown(x,y);
    }

    @Override
    public void touchUp(int x, int y) {
        if(backButton.handleUp(x,y)){
            listener.changeState(main.MAIN_MENU_STATE);
        }
    }

    @Override
    public void initTexturesAndFonts(FreeTypeFontGenerator generator) {
        menuTexture = new Texture("buttons/menu.png");
        lineTexture = new Texture("skins/lines.png");


        lockTexture = new Texture("skins/lock.png");
        starTexture = new Texture("field/point.png");
        selectedTexture = new Texture("skins/selected.png");



    }

    @Override
    protected void disposeTexturesAndFonts() {
        menuTexture.dispose();
        lineTexture.dispose();



        lockTexture.dispose();
        starTexture.dispose();
        selectedTexture.dispose();
    }

    @Override
    public void backPressed() {
        listener.changeState(main.MAIN_MENU_STATE);
    }
}
