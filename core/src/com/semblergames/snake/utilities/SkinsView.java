package com.semblergames.snake.utilities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.semblergames.snake.main;

public class SkinsView {

    private static final int columns = 3;

    private int size;

    private float topY;

    private float pressY;

    private Texture [] skinTextures;

    private Texture lockTexture;

    private Texture selectedTexture;

    private Texture starTexture;

    private boolean pressed;

    private boolean moved;

    private int selectedIndex;

    public SkinsView(int size, Texture[] skinTextures, Texture lockTexture, Texture selectedTexture, Texture starTexture) {
        this.size = size;
        this.skinTextures = skinTextures;
        this.lockTexture = lockTexture;
        this.selectedTexture = selectedTexture;
        this.starTexture = starTexture;

        topY = main.HEIGHT - 186*main.SCALEY;

        pressed = false;
    }

    public void handleDown(int x, int y){

    }

    public void handleUp(int x, int y){

    }

    public void draw(SpriteBatch batch){

    }

}
