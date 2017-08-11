package com.semblergames.snake.gamePackage;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.semblergames.snake.main;
import com.semblergames.snake.utilities.CheckBox;

public class SettingsState extends GameState {

    private Texture noteTexture;

    private Texture [] showTutorialTextures;

    private CheckBox showTutorial;

    @Override
    public void init() {
        showTutorial = new CheckBox(showTutorialTextures[1], showTutorialTextures[0]);
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer renderer, float alpha, float delta) {
        batch.begin();

        showTutorial.draw(batch);

        showTutorial.update(delta);

        batch.end();
    }

    @Override
    public void touchDown(int x, int y) {
        showTutorial.handleDown(x,y);
    }

    @Override
    public void touchDragged(int x, int y) {
        showTutorial.handleDown(x,y);
    }

    @Override
    public void touchUp(int x, int y) {
        showTutorial.handleUp(x,y);
    }

    @Override
    public void initTexturesAndFonts(FreeTypeFontGenerator generator) {
        noteTexture = new Texture("buttons/note.png");

        showTutorialTextures = new Texture[2];
        for(int i = 0; i < 2;i++){
            showTutorialTextures[i] = new Texture("buttons/tutorial"+(i+1)+".png");
        }
    }

    @Override
    protected void disposeTexturesAndFonts() {
        noteTexture.dispose();

        for(Texture texture:showTutorialTextures){
            texture.dispose();
        }
    }

    @Override
    public void backPressed() {
        listener.changeState(main.MAIN_MENU_STATE);
    }
}
