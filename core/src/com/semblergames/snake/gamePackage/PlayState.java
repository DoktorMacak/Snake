package com.semblergames.snake.gamePackage;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.DataInput;
import com.semblergames.snake.main;
import com.semblergames.snake.utilities.Camera;
import com.semblergames.snake.utilities.Direction;

import java.util.ArrayList;
import java.util.Map;

public class PlayState extends GameState {

    public static ArrayList <Pattern> patterns;

    private PlayingRegion[][] regions;
    private Camera camera;


    Snake snake;

    private float speed;
    private float time;

    public PlayState() {
    }

    @Override
    public void init() {
        patterns = Pattern.loadPatterns();

        regions = new PlayingRegion[7][5];
        for(int i = 0; i < 7;i++){
            for (int j = 0; j < 5; j++){

                if(i == 3 && j == 2){
                    regions[i][j] = new PlayingRegion(PlayingRegion.EMPTY);
                }else{
                    regions[i][j] = new PlayingRegion(PlayingRegion.FILLED);
                }

            }
        }

        snake = new Snake(3, Direction.up, 22, 27);

        speed = 0.7f;

        camera = new Camera(13.5f, 11.5f);

        camera.setSpeedX(0);
        camera.setSpeedY(1/speed);

        time = 0f;

    }

    @Override
    protected void initTextures() {

    }

    @Override
    public void render(SpriteBatch batch, ShapeRenderer renderer, float alpha, float delta) {
        renderer.setAutoShapeType(true);
        renderer.begin(ShapeRenderer.ShapeType.Filled);


        for(int i = 1; i < 6;i++) {
            for (int j = 1; j < 4; j++) {
                regions[i][j].draw(j, i, renderer, camera);
            }
        }

        time+=delta;
        snake.draw(renderer, camera);
        if (time > speed){

            if(snake.update()) listener.changeState(main.GAME_OVER_STATE);

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

            if(snake.getHeadSegment().getX() == 27){
                moveEverything(Direction.left);
            }
            if(snake.getHeadSegment().getX() == 17){
                moveEverything(Direction.right);
            }
            if(snake.getHeadSegment().getY() == 23){
                moveEverything(Direction.up);
            }
            if(snake.getHeadSegment().getY() == 32){
                moveEverything(Direction.down);
            }

            time = 0f;
            camera.align(snake);
        }
        renderer.end();

        camera.update(delta);

    }

    @Override
    public void touchDown(int x, int y) {
    }

    @Override
    public void touchDragged(int prevX, int prevY, int x, int y){

    }

    @Override
    public void touchUp(int x, int y) {
        float timeLeft = speed - time;

        if (Math.abs(x) > Math.abs(y)){
            // && (snake.getDirection() == Direction.down || snake.getDirection() == Direction.up)
            if(x > 0) {
                snake.setDirection(Direction.left);
                camera.setSpeedX(-1/timeLeft);
            }
            else {
                snake.setDirection(Direction.right);
                camera.setSpeedX(1/timeLeft);
            }

            float deltaY = (float)snake.getHeadSegment().getY() - 15.5f - camera.getY();


            camera.setSpeedY(deltaY/timeLeft);
        }else{
            if(y > 0) {
                snake.setDirection(Direction.down);
                camera.setSpeedY(-1/timeLeft);
            }
            else {
                snake.setDirection(Direction.up);
                camera.setSpeedY(1/timeLeft);
            }

            float deltaX = (float)snake.getHeadSegment().getX() - 8.5f - camera.getX();


            camera.setSpeedX(deltaX/timeLeft);
        }
        //snake.update();
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
                snake.move(-9,0);
                camera.move(-9,0);
                for(int i = 0; i < 7;i++){
                    regions[i][0] = regions[i][1];
                    regions[i][1] = regions[i][2];
                    regions[i][2] = regions[i][3];
                    regions[i][3] = regions[i][4];
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for(int i = 0; i < 7; i++) {
                            regions[i][4] = new PlayingRegion(PlayingRegion.FILLED);
                        }
                    }
                }).start();

                break;
            case right:
                snake.move(9,0);
                camera.move(9,0);
                for(int i = 0; i < 7;i++){
                    regions[i][4] = regions[i][3];
                    regions[i][3] = regions[i][2];
                    regions[i][2] = regions[i][1];
                    regions[i][1] = regions[i][0];
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for(int i = 0; i < 7; i++) {
                            regions[i][0] = new PlayingRegion(PlayingRegion.FILLED);
                        }
                    }
                }).start();
                break;
            case up:
                snake.move(0,8);
                camera.move(0,8);
                for(int i = 0; i < 5;i++){
                    regions[6][i] = regions[5][i];
                    regions[5][i] = regions[4][i];
                    regions[4][i] = regions[3][i];
                    regions[3][i] = regions[2][i];
                    regions[2][i] = regions[1][i];
                    regions[1][i] = regions[0][i];
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for(int i = 0; i < 5; i++) {
                            regions[0][i] = new PlayingRegion(PlayingRegion.FILLED);
                        }
                    }
                }).start();
                break;
            case down:
                snake.move(0,-8);
                camera.move(0,-8);
                for(int i = 0; i < 5;i++){
                    regions[0][i] = regions[1][i];
                    regions[1][i] = regions[2][i];
                    regions[2][i] = regions[3][i];
                    regions[3][i] = regions[4][i];
                    regions[4][i] = regions[5][i];
                    regions[5][i] = regions[6][i];
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for(int i = 0; i < 5; i++) {
                            regions[6][i] = new PlayingRegion(PlayingRegion.FILLED);
                        }
                    }
                }).start();
                break;
        }

    }
}
