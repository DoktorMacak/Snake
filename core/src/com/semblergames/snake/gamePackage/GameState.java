package com.semblergames.snake.gamePackage;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GameState {

   // protected ChangeState listener = null;

  //  protected DataBundle bundle;

    protected float scaleX;

    protected float scaleY;

    protected int width;

    protected int height;

    public GameState(
          //  DataBundle bundle,
            float scaleX,
            float scaleY,
            int width,
            int height) {
     //   this.bundle = bundle;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.width = width;
        this.height = height;
    }

    public abstract void init();

    public abstract void initTextures();

    public abstract void render(SpriteBatch batch, float alpha);

    public abstract void touchDown(int x, int y);

    public abstract void touchUp(int x, int y);

    public abstract void backPressed();

    public void update(float delta){}

    public void dispose(){
        disposeTextures();
    }

    protected void disposeTextures(){}

  //  public void setChangeListener(ChangeState listener){
      //  this.listener = listener;
   // }

}
