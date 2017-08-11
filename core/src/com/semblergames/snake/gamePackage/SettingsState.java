package com.semblergames.snake.gamePackage;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.semblergames.snake.main;
import com.semblergames.snake.utilities.Button;
import com.semblergames.snake.utilities.CheckBox;

public class SettingsState extends GameState {



    private Texture backTexture;
    private Texture [] playMusicTextures;
    private Texture [] playSoundTextures;
    private Texture snakeSpeedTexture;
    private Texture noteTexture;
    private Texture [] numberTextures;
    private Texture incTexture;


    private Button backButton;
    private CheckBox playMusic;
    private CheckBox playSound;
    private Button incButton;
    private Button decButton;


    @Override
    public void init() {

        backButton = new Button(backTexture);
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
        backTexture = new Texture("buttons/menu.png");

        playMusicTextures = new Texture[2];
        playSoundTextures = new Texture[2];



        noteTexture = new Texture("buttons/note.png");


    }

    @Override
    protected void disposeTexturesAndFonts() {
        noteTexture.dispose();

        backTexture.dispose();


    }

    @Override
    public void backPressed() {
        listener.changeState(main.MAIN_MENU_STATE);
    }
}
