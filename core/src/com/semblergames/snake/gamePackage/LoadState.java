package com.semblergames.snake.gamePackage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.semblergames.snake.main;
import com.semblergames.snake.utilities.Animation;

public class LoadState extends GameState{

    private Texture [] logoTexture;

    private Animation animation;

    @Override
    public void init() {

        animation = new Animation();

        animation.addFrame(4f);
        for(int i = 0; i < 8; i++){
            animation.addFrame(0.045f);
        }
        animation.addFrame(2f);

        animation.playOnce();
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer renderer, float alpha, float delta) {
        batch.begin();

        Texture texture = logoTexture[animation.getCurrentFrame()];

        batch.draw(
                texture,
                0,
                0,
                main.WIDTH/2,
                main.HEIGHT/2,
                (float)texture.getWidth()* main.SCALEX,
                (float)texture.getHeight()*main.SCALEY,
                1,
                1,
                0,
                0,
                0,
                texture.getWidth(),
                texture.getHeight(),
                false,false
        );

        animation.update(delta);

        if(animation.isFinished()){
            animation.setFinished(false);
            listener.changeState(main.MAIN_MENU_STATE);
        }

        batch.end();

    }

    @Override
    public void touchDown(int x, int y) {
        animation.setCurrentFrame(Animation.LAST_SCENE);
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
    public void initTexturesAndFonts(FreeTypeFontGenerator generator) {
        logoTexture = new Texture[10];

        for(int i = 0; i < 10; i++){
            logoTexture[i] = new Texture("intro/"+(i+1)+".jpg");
        }
    }

    @Override
    protected void disposeTexturesAndFonts() {
        for(Texture texture:logoTexture){
            texture.dispose();
        }
    }
}
