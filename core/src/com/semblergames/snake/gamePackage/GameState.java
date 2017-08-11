package com.semblergames.snake.gamePackage;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.semblergames.snake.utilities.ChangeState;

public abstract class GameState {

   protected ChangeState listener;

  //  protected DataBundle bundle;


    public GameState() {

    }

    public abstract void init();



    public abstract void render(SpriteBatch batch, ShapeRenderer renderer, float alpha, float delta);

    public abstract void touchDown(int x, int y);

    public abstract void touchDragged(int x, int y);

    public abstract void touchUp(int x, int y);

    public void dispose(){
     disposeTexturesAndFonts();
    }

    public abstract void initTexturesAndFonts(FreeTypeFontGenerator generator);

    protected abstract void disposeTexturesAndFonts();

    public void setChangeListener(ChangeState listener){
        this.listener = listener;
    }

    public abstract void backPressed();

}
