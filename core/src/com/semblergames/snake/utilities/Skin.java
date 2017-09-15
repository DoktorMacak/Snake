package com.semblergames.snake.utilities;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by lazar on 12.9.17..
 */

public class Skin {
    private int skin;

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
        if (skin > 6) corner = new Texture("skins/parts/"+skin+"corner.png");
        deadHead = new Texture("skins/parts/"+skin+"headd.png");
    }

    public Texture getBody() {
        return body;
    }

    public Texture getCorner() {
        if (skin < 7) return body;
        return corner;
    }

    public Texture getHead() {
        return head;
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
