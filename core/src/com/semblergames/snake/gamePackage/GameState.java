package com.semblergames.snake.gamePackage;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class GameState {

   // protected ChangeState listener = null;

  //  protected DataBundle bundle;


    public GameState() {

    }

    public abstract void init();

    protected abstract void initTextures();

    public abstract void render(SpriteBatch batch, ShapeRenderer renderer, float alpha);

    public abstract void touchDown(int x, int y);

    public abstract void touchDragged(int prevX, int prevY, int x, int y);

    public abstract void touchUp(int x, int y);

    public abstract void backPressed();

    public void update(float delta){}

    public void dispose(){
        disposeTextures();
    }

    protected abstract void disposeTextures();

    //  public void setChangeListener(ChangeState listener){
      //  this.listener = listener;
   // }

}
