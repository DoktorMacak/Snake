package com.semblergames.snake.gamePackage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.semblergames.snake.main;
import com.semblergames.snake.utilities.Animation;
import com.semblergames.snake.utilities.Button;
import com.semblergames.snake.utilities.GameData;

public class MainMenuState extends GameState{

    private static final float FONT_SIZE = 30;

    private static final int NEXT_PLAY = 1;
    private static final int NEXT_SETTINGS = 2;
    private static final int NEXT_SHOP = 3;
    private static final int NEXT_NULL = 0;

    private Texture[] tSnakeTexture;

    private Animation tSnakeAnimation;

    private Texture playTexture;
    private Texture settingsTexture;
    private Texture shopTexture;
    private Texture quitTexture;

    private Button playButton;
    private Button settingsButton;
    private Button shopButton;
    private Button quitButton;

    private BitmapFont font;
    private GlyphLayout glyphLayout;

    private int nextAction;

    private float hsX;
    private float hsY;

    private boolean ready;

    @Override
    public void init() {
        playButton = new Button(playTexture);
        playButton.setPosition(main.WIDTH/2, 800*main.SCALEY);

        settingsButton = new Button(settingsTexture);
        settingsButton.setPosition(main.WIDTH/2, 620*main.SCALEY);

        shopButton = new Button(shopTexture);
        shopButton.setPosition(main.WIDTH/2, 440*main.SCALEY);

        quitButton = new Button(quitTexture);
        quitButton.setPosition(main.WIDTH/2, 260*main.SCALEY);

        tSnakeAnimation = new Animation();
        for(int i = 0; i < 36;i++){
            tSnakeAnimation.addFrame(0.05f);
        }
        tSnakeAnimation.playOnce();



        ready = true;

        nextAction = NEXT_NULL;

        glyphLayout = new GlyphLayout(font, "HIGH SCORE: "+ GameData.HIGH_SCORE);


        hsX = main.WIDTH/2 - glyphLayout.width/2;

        hsY = 990*main.SCALEY;

    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer renderer, float alpha, float delta) {
        batch.begin();

        Texture texture = tSnakeTexture[tSnakeAnimation.getCurrentFrame()];

        drawTexture(batch, main.WIDTH/2, 1400*main.SCALEY, texture);

        tSnakeAnimation.update(delta);

        if(tSnakeAnimation.getCurrentFrame() == 19 && ready){
            tSnakeAnimation.pause();
            ready = false;
        }

        if(tSnakeAnimation.isFinished()){
            tSnakeAnimation.setFinished(false);
            switch (nextAction){
                case NEXT_PLAY:{
                    if(GameData.SHOW_TUTORIAL){
                        listener.changeState(main.TUTORIAL_STATE);
                    }else{
                        listener.changeState(main.PLAY_STATE);
                        GameData.SHOW_TUTORIAL = false;
                    }

                    break;
                }
                case NEXT_SETTINGS:{
                    listener.changeState(main.SETTINGS_STATE);
                    break;
                }
                case NEXT_SHOP:{
                    listener.changeState(main.SHOP_STATE);
                    break;
                }
            }
        }

        playButton.update(delta);
        playButton.draw(batch);

        settingsButton.update(delta);
        settingsButton.draw(batch);

        shopButton.update(delta);
        shopButton.draw(batch);

        quitButton.update(delta);
        quitButton.draw(batch);

        font.getColor().a = alpha;

        font.draw(batch, "HIGH SCORE: "+ GameData.HIGH_SCORE, hsX, hsY);

        batch.end();
    }

    @Override
    public void touchDown(int x, int y) {
        playButton.handleDown(x,y);
        settingsButton.handleDown(x,y);
        shopButton.handleDown(x,y);
        quitButton.handleDown(x,y);
    }

    @Override
    public void touchDragged(int x, int y) {
        playButton.handleDown(x,y);
        settingsButton.handleDown(x,y);
        shopButton.handleDown(x,y);
        quitButton.handleDown(x,y);
    }

    @Override
    public void touchUp(int x, int y) {
        if(playButton.handleUp(x,y)){
            tSnakeAnimation.play();
            nextAction = NEXT_PLAY;
        }
        if(settingsButton.handleUp(x,y)){
            tSnakeAnimation.play();
            nextAction = NEXT_SETTINGS;
        }
        if(shopButton.handleUp(x,y)){
            tSnakeAnimation.play();
            nextAction = NEXT_SHOP;
        }
        if(quitButton.handleUp(x,y)){
            Gdx.app.exit();
        }
    }

    @Override
    public void initTexturesAndFonts(FreeTypeFontGenerator generator) {

        float fontSize = FONT_SIZE * main.SCALEX;

        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int)fontSize;
        parameter.color = new Color(0.329f,0.667f,0.863f, 1);

        font = generator.generateFont(parameter);

        playTexture = new Texture("buttons/play.png");
        settingsTexture = new Texture("buttons/settings.png");
        shopTexture = new Texture("buttons/shop.png");
        quitTexture = new Texture("buttons/quit.png");

        tSnakeTexture = new Texture[36];

        for(int i = 1; i < 37;i++){
            tSnakeTexture[i-1] = new Texture("logo/"+i+".png");
        }

    }

    @Override
    protected void disposeTexturesAndFonts() {
        playTexture.dispose();
        settingsTexture.dispose();
        shopTexture.dispose();
        quitTexture.dispose();

        for(Texture texture:tSnakeTexture){
            texture.dispose();
        }

        font.dispose();
    }

    @Override
    public void backPressed() {
        Gdx.app.exit();
    }
}
