package com.semblergames.snake.gamePackage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.DistanceFieldFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.semblergames.snake.fieldPackage.Field;
import com.semblergames.snake.fieldPackage.FieldRenderer;
import com.semblergames.snake.fieldPackage.Pattern;
import com.semblergames.snake.fieldPackage.PlayingRegion;
import com.semblergames.snake.main;
import com.semblergames.snake.utilities.Camera;
import com.semblergames.snake.utilities.Direction;
import com.semblergames.snake.utilities.GameData;


public class PlayState extends GameState {

    public static Texture [] wallTextures;
    public static Texture [] standardCoinTextures;
    public static Texture [] magnetCoinTextures;
    public static Texture [] speedCoinTexture;
    public static Texture [] pointTexture;


    private static final int COLUMNS = 5;
    private static final int ROWS = 7;

    private PlayingRegion[][] regions;

    private FieldRenderer fieldRenderer;

    private Camera camera;


    private Snake snake;

    private float speed;
    private float time;




    private boolean unstoppable;
    private float unstoppableTime;


    private int xPressed;
    private int yPressed;

    private float touchDownTime;


    private int speedCoins;

    private int score;

    public PlayState() {
    }

    @Override
    public void init() {


        regions = new PlayingRegion[ROWS][COLUMNS];
        for(int i = 0; i < ROWS;i++){
            for (int j = 0; j < COLUMNS; j++){

                if(i == (ROWS - 1)/2 && j == (COLUMNS -1)/2){
                    regions[i][j] = new PlayingRegion(PlayingRegion.EMPTY);
                }else{
                    regions[i][j] = new PlayingRegion(PlayingRegion.FILLED);
                }

            }
        }

        fieldRenderer = new FieldRenderer();

        snake = new Snake(3, Direction.up, (COLUMNS* PlayingRegion.width) /2, (ROWS* PlayingRegion.height) /2);

        speed = 0.7f;

        time = 0f;

        unstoppable = false;

        unstoppableTime = 0;

        camera = new Camera();
        camera.align(snake);

        camera.setSpeedX(0);
        camera.setSpeedY(1/speed);

        score = 0;

        speedCoins = 0;

        touchDownTime = 1;

    }



    @Override
    public void render(SpriteBatch batch, ShapeRenderer renderer, float alpha, float delta) {

        camera.update(delta);



        renderer.setAutoShapeType(true);
        renderer.begin(ShapeRenderer.ShapeType.Filled);

        renderer.setColor(Color.BLACK);

       // renderer.rect(main.WIDTH/2 - main.BLOCK_WIDTH/2, main.HEIGHT/2 - main.BLOCK_HEIGHT/2, main.BLOCK_WIDTH, main.BLOCK_HEIGHT);

        /**
         * novo
         */

        if(touchDownTime < 1) {
            touchDownTime += delta;
        }

        if(unstoppable){
            unstoppableTime += delta;
            if(unstoppableTime > 4){
                unstoppable = false;
                speed = speed*2;
                unstoppableTime = 0;
            }
        }


        time+=delta;
        snake.draw(renderer, camera);
        if (time > speed){

            if(snake.update()){
                listener.changeState(main.MAIN_MENU_STATE);
            }

            Field field = regions[snake.getHeadSegment().getY() / PlayingRegion.height][snake.getHeadSegment().getX() / PlayingRegion.width]
                    .getField(snake.getHeadSegment().getX() % PlayingRegion.width, snake.getHeadSegment().getY() % PlayingRegion.height);

            switch (field.getType()){
                case Field.WALL:{
                    if(unstoppable){
                        field.getAnimation().play();
                    }else {
                        listener.changeState(main.MAIN_MENU_STATE);
                    }
                    break;
                }
                case Field.MAGNET_COIN:{
                    field.getAnimation().play();
                    break;
                }
                case Field.STANDARD_COIN:{
                    field.getAnimation().play();
                    score++;
                    if(score % 5 == 0){
                        snake.grow();
                    }
                    break;
                }
                case Field.SPEED_COIN:{
                    field.getAnimation().play();
                    speedCoins++;
                    break;
                }
                case Field.POINT_STAR:{
                    field.getAnimation().play();
                    GameData.POINT_STARS++;
                    break;
                }
            }


            Direction direction = snake.getNextDirection();
            if(direction == null){
                direction = snake.getDirection();
            }



            switch (direction) {
                case left:
                    camera.setSpeed(-1 / speed, 0);
                    break;
                case right:
                    camera.setSpeed(1 / speed, 0);
                    break;
                case up:
                    camera.setSpeed(0, 1 / speed);
                    break;
                case down:
                    camera.setSpeed(0, -1 / speed);
                    break;
            }


            if(snake.getHeadSegment().getX() == ((COLUMNS/2)+1)* PlayingRegion.width){
                moveEverything(Direction.left);
            }
            if(snake.getHeadSegment().getX() == (COLUMNS/2)* PlayingRegion.width-1){
                moveEverything(Direction.right);
            }
            if(snake.getHeadSegment().getY() == ((ROWS/2)+1)* PlayingRegion.height){
                moveEverything(Direction.down);
            }
            if(snake.getHeadSegment().getY() == (ROWS/2)* PlayingRegion.height-1){
                moveEverything(Direction.up);
            }

            time = 0f;
            camera.align(snake);
        }

        renderer.end();


        for(int i = 0; i < ROWS;i++) {
            for (int j = 0; j < COLUMNS; j++) {
                regions[i][j].processFields(j,i,camera,fieldRenderer,delta);
            }
        }

       /* Color color = batch.getColor();

        batch.setColor(color.r, color.g, color.b, alpha);*/

        batch.begin();

        fieldRenderer.render(batch);

       /* font.draw(batch, Integer.toString(snake.getHeadSegment().getX()), 300 , 300);
        font.draw(batch, Integer.toString(snake.getHeadSegment().getY()), 350 , 300);
        font.draw(batch, Float.toString(main.WIDTH) + " " + Float.toString(main.HEIGHT), 400, 400);
        font.draw(batch, Float.toString(delta), 500,500);*/
        batch.end();



        //gluposti na ekranu


    }

    @Override
    public void touchDown(int x, int y) {
        xPressed = x;
        yPressed = y;

        if(touchDownTime < 0.3f && speedCoins > 5 && !unstoppable){
            unstoppable = true;
            speedCoins = 0;
            speed = speed/2;
        }

        touchDownTime = 0;

    }

    @Override
    public void touchDragged(int x, int y){

    }

    @Override
    public void touchUp(int x, int y) {

        int dx = xPressed - x;
        int dy = yPressed - y;

        float timeLeft = speed - time;

        if (Math.abs(dx) > Math.abs(dy)){
            if(snake.getDirection() != Direction.right && snake.getDirection() != Direction.left && Math.abs(dx) > 50*main.SCALEX) {

                float deltaY = (float) snake.getHeadSegment().getY() - (float)main.SCREEN_HEIGHT / 2 - camera.getY();

                if (dx > 0) {
                    if(snake.setDirection(Direction.left))

                    camera.setSpeed(-1 / timeLeft, deltaY / timeLeft);

                } else {
                    if(snake.setDirection(Direction.right))

                    camera.setSpeed(1 / timeLeft, deltaY / timeLeft);
                }
            }
        }else{
            if (snake.getDirection() != Direction.up && snake.getDirection() != Direction.down && Math.abs(dy) > 50*main.SCALEY) {

                float deltaX = (float) snake.getHeadSegment().getX() - (float)main.SCREEN_WIDTH / 2 - camera.getX();

                if (dy > 0) {
                    if(snake.setDirection(Direction.down))

                    camera.setSpeed(deltaX / timeLeft,-1 / timeLeft);

                } else {
                    if(snake.setDirection(Direction.up))

                    camera.setSpeed(deltaX / timeLeft, 1 / timeLeft);
                }
            }
        }
    }

    @Override
    public void backPressed() {
        listener.changeState(main.MAIN_MENU_STATE);
    }

    @Override
    public void initTexturesAndFonts(FreeTypeFontGenerator generator) {

        Pattern.loadPatterns();

        wallTextures = new Texture[6];
        wallTextures[0] = new Texture("field/wall.png");
        for(int i = 1; i < 6;i++){
            wallTextures[i] = new Texture("field/walld"+i+".png");
        }

        magnetCoinTextures = new Texture[6];
        magnetCoinTextures[0] = new Texture("field/mcoin.png");
        for(int i = 1; i < 6;i++){
            magnetCoinTextures[i] = new Texture("field/mcoind"+i+".png");
        }

        speedCoinTexture = new Texture[6];
        speedCoinTexture[0] = new Texture("field/scoin.png");
        for(int i = 1; i < 6;i++){
            speedCoinTexture[i] = new Texture("field/scoind"+i+".png");
        }

        standardCoinTextures = new Texture[6];
        standardCoinTextures[0] = new Texture("field/coin.png");
        for(int i = 1; i < 6;i++){
            standardCoinTextures[i] = new Texture("field/coind"+i+".png");
        }

        pointTexture = new Texture[6];
        pointTexture[0] = new Texture("field/point.png");
        for(int i = 1; i < 6;i++){
            pointTexture[i] = new Texture("field/pointd"+i+".png");
        }
    }

    @Override
    protected void disposeTexturesAndFonts() {
        for(Texture texture:wallTextures){
            texture.dispose();
        }
        for(Texture texture:standardCoinTextures){
            texture.dispose();
        }
        for(Texture texture:pointTexture){
            texture.dispose();
        }
        for(Texture texture:magnetCoinTextures){
            texture.dispose();
        }
        for(Texture texture:speedCoinTexture){
            texture.dispose();
        }
    }

    private void moveEverything(Direction direction){

        switch (direction){
            case left:
                snake.move(-PlayingRegion.width,0);
                camera.move(-PlayingRegion.width,0);
                for(int i = 0; i < ROWS;i++){
                    for(int j = 0; j < COLUMNS-1;j++){
                        regions[i][j] = regions[i][j+1];
                    }
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for(int i = 0; i < ROWS; i++) {
                            regions[i][COLUMNS-1] = new PlayingRegion(PlayingRegion.FILLED);
                        }
                    }
                }).start();

                break;
            case right:
                snake.move(PlayingRegion.width,0);
                camera.move(PlayingRegion.width,0);
                for(int i = 0; i < ROWS;i++){
                    for(int j = COLUMNS-1; j > 0;j--){
                        regions[i][j] = regions[i][j-1];
                    }
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for(int i = 0; i < ROWS; i++) {
                            regions[i][0] = new PlayingRegion(PlayingRegion.FILLED);
                        }
                    }
                }).start();
                break;
            case up:
                snake.move(0, PlayingRegion.height);
                camera.move(0, PlayingRegion.height);
                for(int i = 0; i < COLUMNS;i++){
                    for(int j = ROWS-1; j > 0;j--){
                        regions[j][i] = regions[j-1][i];
                    }
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for(int i = 0; i < COLUMNS; i++) {
                            regions[0][i] = new PlayingRegion(PlayingRegion.FILLED);
                        }
                    }
                }).start();
                break;
            case down:
                snake.move(0,-PlayingRegion.height);
                camera.move(0,-PlayingRegion.height);
                for(int i = 0; i < COLUMNS;i++){
                    for(int j = 0; j < ROWS-1;j++){
                        regions[j][i] = regions[j+1][i];
                    }
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for(int i = 0; i < COLUMNS; i++) {
                            regions[ROWS-1][i] = new PlayingRegion(PlayingRegion.FILLED);
                        }
                    }
                }).start();
                break;
        }

    }
}
