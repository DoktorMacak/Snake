package com.semblergames.snake.gamePackage;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.semblergames.snake.main;
import com.semblergames.snake.utilities.Button;

public class TutorialState extends GameState {

    private Texture helpTexture;
    private Texture gotItTexture;

    private Button gotItButton;

    @Override
    public void init() {
        gotItButton = new Button(gotItTexture);
        gotItButton.setPosition(main.WIDTH/2, 200*main.SCALEY);
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer renderer, float alpha, float delta) {
        batch.begin();

        drawTexture(batch, main.WIDTH/2, main.HEIGHT/2, helpTexture);

        gotItButton.draw(batch);
        gotItButton.update(delta);

        batch.end();
    }

    @Override
    public void touchDown(int x, int y) {
        gotItButton.handleDown(x,y);
    }

    @Override
    public void touchDragged(int x, int y) {
        gotItButton.handleDown(x,y);
    }

    @Override
    public void touchUp(int x, int y) {
        if(gotItButton.handleUp(x,y)){
            listener.changeState(main.PLAY_STATE, main.TUTORIAL_STATE);
            listener.playClicked();
        }
    }

    @Override
    public void initTexturesAndFonts(FreeTypeFontGenerator generator) {
        helpTexture = new Texture("tutorial/help.png");
        gotItTexture = new Texture("buttons/gotit.png");
    }

    @Override
    protected void disposeTexturesAndFonts() {
        helpTexture.dispose();
        gotItTexture.dispose();
    }

    @Override
    public void backPressed() {

    }
}
