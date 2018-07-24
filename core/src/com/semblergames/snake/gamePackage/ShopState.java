package com.semblergames.snake.gamePackage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.semblergames.snake.main;
import com.semblergames.snake.utilities.Button;
import com.semblergames.snake.utilities.GameData;
import com.semblergames.snake.utilities.Point;
import com.semblergames.snake.utilities.ScrollItem;
import com.semblergames.snake.utilities.ScrollView;

import java.util.ArrayList;

public class ShopState extends GameState {

    private static final int FONT_SIZE = 60;

    private Texture menuTexture;
    private Texture lineTexture;

    private Texture [] skins;

    private Texture lockTexture;
    private Texture starTexture;
    private Texture selectedTexture;

    private BitmapFont font;


    private Button backButton;



    private float textX;
    private float textY;
    private float starX;
    private float starY;

    private ArrayList<Point> lockPoints;
    private ArrayList<Point> starPoints;
    private ScrollView scrollView;

    private GlyphLayout layout;

    private Sound lockedSound;

    @Override
    public void init() {
        backButton = new Button(menuTexture);
        backButton.setPosition(120*main.SCALEX, main.HEIGHT - 90*main.SCALEY);

        scrollView = new ScrollView(skins.length, font, main.HEIGHT - 256*main.SCALEY);

        lockPoints = new ArrayList<Point>();
        starPoints = new ArrayList<Point>();

        starX = main.WIDTH - 90*main.SCALEX;
        starY = main.HEIGHT - 90*main.SCALEY;

        textY = main.HEIGHT - 60*main.SCALEY;

        layout = new GlyphLayout(font, Integer.toString(GameData.POINT_STARS));

        float twid = layout.width;

        textX = main.WIDTH - 143*main.SCALEX - twid;
    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer renderer, float alpha, float delta) {
        batch.begin();

        lockPoints.clear();
        starPoints.clear();

        font.getColor().a = alpha;

        ScrollItem [] items = scrollView.getItems();

        for(int i = 0; i < items.length;i++){
            if(!GameData.SKINS[i]){
                lockPoints.add(new Point(items[i].getImageX(), items[i].getImageY()));
                starPoints.add(new Point(items[i].getStarX(), items[i].getStarY()));
                font.draw(batch, Integer.toString(GameData.SKIN_PRICES[i]), items[i].getTextX(), items[i].getTextY());
            }
            drawTexture(batch, items[i].getImageX(), items[i].getImageY(), skins[i]);

            if(GameData.SKIN_POINTER == i){
                drawTexture(batch,items[i].getImageX(), items[i].getImageY(), selectedTexture);
            }

        }

        for(Point point:lockPoints){
            drawTexture(batch, point.getX(), point.getY(), lockTexture);
        }

        for(Point point:starPoints){
            drawTexture(batch, point.getX(), point.getY(), starTexture);
        }



        drawTexture(batch, main.WIDTH/2, main.HEIGHT - main.SCALEY * lineTexture.getHeight()/2, lineTexture);

        backButton.draw(batch);
        backButton.update(delta);

        layout.setText(font, Integer.toString(GameData.POINT_STARS));

        float twid = layout.width;
        textX = main.WIDTH - 143*main.SCALEX - twid;

        font.getColor().a = alpha;

        font.draw(batch, Integer.toString(GameData.POINT_STARS), textX, textY);

        drawTexture(batch, starX, starY, starTexture);


        batch.end();
    }

    @Override
    public void touchDown(int x, int y) {
        backButton.handleDown(x,y);
        if(y < main.HEIGHT - 186*main.SCALEY) {
            scrollView.handleDown(x,y);
        }

    }

    @Override
    public void touchDragged(int x, int y) {
        backButton.handleDown(x,y);
        if(y < main.HEIGHT - 186*main.SCALEY) {
            scrollView.handleDown(x,y);
        }
    }

    @Override
    public void touchUp(int x, int y) {
        if(backButton.handleUp(x,y)){
            listener.changeState(main.MAIN_MENU_STATE,main.SHOP_STATE);
            listener.playClicked();
        }

        int pointer = -1;

        if(y < main.HEIGHT - 186*main.SCALEY) {

            pointer = scrollView.handleUp(x, y);
        }

        if(pointer!= -1){

            if(GameData.SKINS[pointer]){
                GameData.SKIN_POINTER = pointer;
                listener.playClicked();
            }else if(GameData.POINT_STARS >= GameData.SKIN_PRICES[pointer]){
                GameData.SKIN_POINTER = pointer;
                GameData.SKINS[pointer] = true;
                GameData.POINT_STARS -= GameData.SKIN_PRICES[pointer];
                listener.playClicked();
            }else{
                lockedSound.play();
            }



        }
    }

    @Override
    public void initTexturesAndFonts(FreeTypeFontGenerator generator) {
        menuTexture = new Texture("buttons/menu.png");
        lineTexture = new Texture("skins/lines.png");

        skins = new Texture[GameData.SKINS.length];

        for(int i = 0; i < skins.length; i++){
            skins[i] = new Texture("skins/snake"+(i+1)+".png");
        }

        lockTexture = new Texture("skins/lock.png");
        starTexture = new Texture("field/point.png");
        selectedTexture = new Texture("skins/selected.png");

        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.color = new Color(0,0,0,1);
        parameter.size = (int)(FONT_SIZE*main.SCALEX);

        font = generator.generateFont(parameter);

        lockedSound = Gdx.audio.newSound(Gdx.files.internal("sounds/locked.wav"));

    }

    @Override
    protected void disposeTexturesAndFonts() {
        menuTexture.dispose();
        lineTexture.dispose();

        for(Texture texture:skins){
            texture.dispose();
        }

        lockTexture.dispose();
        starTexture.dispose();
        selectedTexture.dispose();

        font.dispose();

        lockedSound.dispose();
    }

    @Override
    public void backPressed() {
        listener.changeState(main.MAIN_MENU_STATE,main.SHOP_STATE);
    }
}
