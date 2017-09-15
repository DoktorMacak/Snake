package com.semblergames.snake.gamePackage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.semblergames.snake.main;
import com.semblergames.snake.utilities.Button;
import com.semblergames.snake.utilities.GameData;


public class GameOverState extends GameState {

    private Texture background;

    private Texture playAgainTexture;

    private Texture quitTexture;

    private BitmapFont font1;
    private BitmapFont font2;
    private BitmapFont font;

    private Button playAgain;
    private Button quit;

    private float textX;
    private float textY;

    @Override
    public void init() {

        playAgain = new Button(playAgainTexture);
        playAgain.setPosition(main.WIDTH/2, 440*main.SCALEY);

        quit = new Button(quitTexture);
        quit.setPosition(main.WIDTH/2, 260*main.SCALEY);



        if (GameData.HIGH_SCORE > GameData.CURRENT_SCORE){
            font = font1;
        }else {
            GameData.HIGH_SCORE = GameData.CURRENT_SCORE;
            font = font2;
        }

        GlyphLayout layout = new GlyphLayout(font, "SCORE: "+ GameData.CURRENT_SCORE);

        textX = main.WIDTH/2 - layout.width/2;

        textY = 924*main.SCALEY;



    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer renderer, float alpha, float delta) {
        batch.begin();

        drawTexture(batch, main.WIDTH/2, main.HEIGHT/2, background);

        playAgain.draw(batch);
        playAgain.update(delta);

        quit.draw(batch);
        quit.update(delta);

        font.getColor().a = alpha;

        font.draw(batch, "SCORE: "+ GameData.CURRENT_SCORE, textX, textY);

        batch.end();
    }

    @Override
    public void touchDown(int x, int y) {
        playAgain.handleDown(x,y);
        quit.handleDown(x,y);
    }

    @Override
    public void touchDragged(int x, int y) {
        playAgain.handleDown(x,y);
        quit.handleDown(x,y);
    }

    @Override
    public void touchUp(int x, int y) {
        if(playAgain.handleUp(x,y)){
            listener.changeState(main.PLAY_STATE);
            listener.playClicked();
        }
        if(quit.handleUp(x,y)){
            listener.changeState(main.MAIN_MENU_STATE);
            listener.playClicked();
        }
    }

    @Override
    public void backPressed() {
        listener.changeState(main.MAIN_MENU_STATE);
    }

    @Override
    public void initTexturesAndFonts(FreeTypeFontGenerator generator) {
        background = new Texture("game_over/end.png");

        playAgainTexture = new Texture("game_over/again.png");

        quitTexture = new Texture("game_over/quit.png");

        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();


        parameter.color = new Color(0.918f,0.49f,0.15f, 1);

        parameter.size = (int)(60*main.SCALEX);

        font1 = generator.generateFont(parameter);

        parameter.color = new Color(0.329f,0.667f,0.863f, 1);

        font2 = generator.generateFont(parameter);


    }

    @Override
    protected void disposeTexturesAndFonts() {

        background.dispose();

        playAgainTexture.dispose();

        quitTexture.dispose();

        font1.dispose();
        font2.dispose();

    }
}
