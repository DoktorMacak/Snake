package com.semblergames.snake.gamePackage;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.DataInput;
import com.semblergames.snake.main;
import com.semblergames.snake.utilities.Camera;
import com.semblergames.snake.utilities.Direction;

import java.util.ArrayList;
import java.util.Map;

public class PlayState extends GameState {



    private static final int COLUMNS = 7;
    private static final int ROWS = 9;

    private PlayingRegion[][] regions;
    private Camera camera;


    private Snake snake;

    private float speed;
    private float time;

    private int xPressed;
    private int yPressed;


    private BitmapFont font;

    public PlayState() {
    }

    @Override
    public void init() {
        Pattern.loadPatterns();

        font = new BitmapFont();

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

        snake = new Snake(3, Direction.up, (COLUMNS*PlayingRegion.width) /2, (ROWS*PlayingRegion.height) /2);

        speed = 0.7f;

        time = 0f;

        camera = new Camera();
        camera.align(snake);

        camera.setSpeedX(0);
        camera.setSpeedY(1/speed);



    }

    @Override
    protected void initTextures() {

    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer renderer, float alpha, float delta) {
        renderer.setAutoShapeType(true);
        renderer.begin(ShapeRenderer.ShapeType.Filled);


        for(int i = 1; i < ROWS-1;i++) {
            for (int j = 1; j < COLUMNS-1; j++) {
                regions[i][j].draw(j, i, renderer, camera);
            }
        }

        time+=delta;
        snake.draw(renderer, camera);
        if (time > speed){

            if(snake.update() ||
                    regions[snake.getHeadSegment().getY() / PlayingRegion.height][snake.getHeadSegment().getX() / PlayingRegion.width]
                            .isWall(snake.getHeadSegment().getX() % PlayingRegion.width, snake.getHeadSegment().getY() % PlayingRegion.height)){
                listener.changeState(main.GAME_OVER_STATE);
            }

            switch (snake.getDirection()){
                case left:
                    camera.setSpeedX(-1/speed);
                    camera.setSpeedY(0);
                    break;
                case right:
                    camera.setSpeedX(1/speed);
                    camera.setSpeedY(0);
                    break;
                case up:
                    camera.setSpeedX(0);
                    camera.setSpeedY(1/speed);
                    break;
                case down:
                    camera.setSpeedX(0);
                    camera.setSpeedY(-1/speed);
                    break;
            }

            if(snake.getHeadSegment().getX() == ((COLUMNS/2)+1)*PlayingRegion.width){
                moveEverything(Direction.left);
            }
            if(snake.getHeadSegment().getX() == (COLUMNS/2)*PlayingRegion.width-1){
                moveEverything(Direction.right);
            }
            if(snake.getHeadSegment().getY() == ((ROWS/2)+1)*PlayingRegion.height){
                moveEverything(Direction.down);
            }
            if(snake.getHeadSegment().getY() == (ROWS/2)*PlayingRegion.height-1){
                moveEverything(Direction.up);
            }

            time = 0f;
            camera.align(snake);
        }

        renderer.end();

        camera.update(delta);

        //gluposti na ekranu

        batch.begin();
        font.draw(batch, Integer.toString(snake.getHeadSegment().getX()), 300 , 300);
        font.draw(batch, Integer.toString(snake.getHeadSegment().getY()), 350 , 300);
        font.draw(batch, Float.toString(main.WIDTH) + " " + Float.toString(main.HEIGHT), 400, 400);
        batch.end();

    }

    @Override
    public void touchDown(int x, int y) {
        xPressed = x;
        yPressed = y;
    }

    @Override
    public void touchDragged(int prevX, int prevY, int x, int y){

    }

    @Override
    public void touchUp(int x, int y) {

        int dx = xPressed - x;
        int dy = yPressed - y;

        float timeLeft = speed - time;

        if (Math.abs(dx) > Math.abs(dy)){
            if(snake.getDirection() != Direction.right && snake.getDirection() != Direction.left && Math.abs(dx) > 50*main.SCALEX) {
                if (dx > 0) {
                    snake.setDirection(Direction.left);
                    camera.setSpeedX(-1 / timeLeft);
                } else {
                    snake.setDirection(Direction.right);
                    camera.setSpeedX(1 / timeLeft);
                }

                float deltaY = (float) snake.getHeadSegment().getY() - ((float)(main.SCREEN_HEIGHT - 1)) / 2 - camera.getY();


                camera.setSpeedY(deltaY / timeLeft);
            }
        }else{
            if (snake.getDirection() != Direction.up && snake.getDirection() != Direction.down && Math.abs(dy) > 50*main.SCALEY) {
                if (dy > 0) {
                    snake.setDirection(Direction.down);
                    camera.setSpeedY(-1 / timeLeft);
                } else {
                    snake.setDirection(Direction.up);
                    camera.setSpeedY(1 / timeLeft);
                }

                float deltaX = (float) snake.getHeadSegment().getX() - ((float)(main.SCREEN_WIDTH - 1)) / 2 - camera.getX();


                camera.setSpeedX(deltaX / timeLeft);
            }
        }
    }

    @Override
    public void backPressed() {

    }

    @Override
    protected void disposeTextures() {

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
                snake.move(0,PlayingRegion.height);
                camera.move(0,PlayingRegion.height);
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
