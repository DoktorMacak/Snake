package com.semblergames.snake.gamePackage;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.semblergames.snake.main;
import com.semblergames.snake.utilities.Button;
import com.semblergames.snake.utilities.CheckBox;
import com.semblergames.snake.utilities.GameData;

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

        playMusic = new CheckBox(playMusicTextures[1], playMusicTextures[0]);
        playMusic.setPosition(main.WIDTH/2, 1200*main.SCALEY);
        playMusic.setChecked(GameData.PLAY_MUSIC);

        playSound = new CheckBox(playSoundTextures[1], playSoundTextures[0]);
        playSound.setPosition(main.WIDTH/2, 1020*main.SCALEY);
        playSound.setChecked(GameData.PLAY_SOUNDS);


    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer renderer, float alpha, float delta) {
        batch.begin();

        backButton.draw(batch);
        backButton.update(delta);

        playMusic.draw(batch);
        playMusic.update(delta);

        playSound.draw(batch);
        playSound.update(delta);

        drawTexture(batch, main.WIDTH/2, 840*main.SCALEY, snakeSpeedTexture);

        batch.end();
    }

    @Override
    public void touchDown(int x, int y) {
        backButton.handleDown(x,y);
        playMusic.handleDown(x,y);
        playSound.handleDown(x,y);
    }

    @Override
    public void touchDragged(int x, int y) {
        backButton.handleDown(x,y);
        playMusic.handleDown(x,y);
        playSound.handleDown(x,y);
    }

    @Override
    public void touchUp(int x, int y) {
        if(backButton.handleUp(x,y)){
            listener.changeState(main.MAIN_MENU_STATE);
        }
        if(playMusic.handleUp(x,y)){
            GameData.PLAY_MUSIC = playMusic.isChecked();
        }
        if(playSound.handleUp(x,y)){
            GameData.PLAY_SOUNDS = playSound.isChecked();
        }
    }

    @Override
    public void initTexturesAndFonts(FreeTypeFontGenerator generator) {
        backTexture = new Texture("buttons/menu.png");

        playMusicTextures = new Texture[2];
        playSoundTextures = new Texture[2];

        for(int i = 0; i < 2;i++){
            playMusicTextures[i] = new Texture("buttons/music"+(i+1)+".png");
            playSoundTextures[i] = new Texture("buttons/sound"+(i+1)+".png");
        }

        snakeSpeedTexture = new Texture("buttons/speed.png");


        noteTexture = new Texture("buttons/note.png");


    }

    @Override
    protected void disposeTexturesAndFonts() {
        backTexture.dispose();

        for(Texture texture:playMusicTextures){
            texture.dispose();
        }

        for(Texture texture:playSoundTextures){
            texture.dispose();
        }

        snakeSpeedTexture.dispose();

        noteTexture.dispose();


    }

    @Override
    public void backPressed() {
        listener.changeState(main.MAIN_MENU_STATE);
    }
}
