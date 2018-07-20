package com.semblergames.snake.utilities;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by lazar on 12.9.17..
 */

public class Skin {
    private int skin;

    private boolean hasCorner;

    private Texture head;
    private Texture corner;
    private Texture body;
    private Texture deadHead;

    public Skin(int skin){
        setSkin(skin);
    }

    public void setSkin(int skin) {
        this.skin = skin;
        head = new Texture("skins/parts/"+skin+"head.png");
        body = new Texture("skins/parts/"+skin+"body.png");
        hasCorner = false;
        if (skin > 6 && skin != 30 && skin != 31){
            corner = new Texture("skins/parts/"+skin+"corner.png");
            hasCorner = true;
        }
        deadHead = new Texture("skins/parts/"+skin+"headd.png");
    }

    public Texture getBody() {
        return body;
    }

    public Texture getCorner() {
        return corner;
    }

    public Texture getHead() {
        return head;
    }

    public boolean isHasCorner(){
        return hasCorner;
    }

    public void dispose(){
        head.dispose();
        body.dispose();
        deadHead.dispose();
        if(corner != null) corner.dispose();
    }

    public Texture getDeadHead() {
        return deadHead;
    }
}
