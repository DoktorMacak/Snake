package com.semblergames.snake.gamePackage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.DistanceFieldFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.semblergames.snake.fieldPackage.Field;
import com.semblergames.snake.fieldPackage.FieldRenderer;
import com.semblergames.snake.fieldPackage.Pattern;
import com.semblergames.snake.fieldPackage.PlayingRegion;
import com.semblergames.snake.main;
import com.semblergames.snake.utilities.Button;
import com.semblergames.snake.utilities.Camera;
import com.semblergames.snake.utilities.Direction;
import com.semblergames.snake.utilities.GameData;
import com.semblergames.snake.utilities.Image;
import com.semblergames.snake.utilities.ImageShow;
import com.semblergames.snake.utilities.Point;
import com.semblergames.snake.utilities.PowerupHandler;


public class PlayState extends GameState {

    private static final int COLUMNS = 5;
    private static final int ROWS = 7;

    public static Texture [] wallTextures;
    public static Texture [] standardCoinTextures;
    public static Texture [] magnetCoinTextures;
    public static Texture [] speedCoinTexture;
    public static Texture [] pointTexture;

    private Texture scoreTexture;
    private Texture whiteTexture;
    private Texture lineTexture;
    private Texture [] magnetTextures;
    private Texture [] speedTextures;
    private Texture menuTexture;

    private BitmapFont font;

    private Button backButton;
    private Image whiteImage;
    private Image scoreImage;

    private PowerupHandler speedPowerup;
    private PowerupHandler magnetPowerup;


    private boolean countDown;

    private PlayingRegion[][] regions;

    private FieldRenderer fieldRenderer;

    private Camera camera;


    private Snake snake;

    private float speed;
    private float time;



    private int xPressed;
    private int yPressed;


    private GlyphLayout scoreTextLayout;

    private float scoreX;
    private float scoreY;


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

        backButton = new Button(menuTexture);
        backButton.setPosition(120*main.SCALEX, main.HEIGHT - 90*main.SCALEY);

        fieldRenderer = new FieldRenderer();

        snake = new Snake(3, Direction.up, (COLUMNS* PlayingRegion.width) /2, (ROWS* PlayingRegion.height) /2);

        speed = 0.7f;

        time = 0f;


        magnetPowerup = new PowerupHandler(magnetTextures, Direction.right);
        speedPowerup = new PowerupHandler(speedTextures, Direction.left);

        whiteImage = new Image(whiteTexture);
        whiteImage.setCentre(main.WIDTH/2, main.HEIGHT - main.SCALEY * whiteTexture.getHeight()/2);

        scoreImage = new Image(scoreTexture);
        scoreImage.setCentre(main.WIDTH/2, main.HEIGHT - 90*main.SCALEY);

        camera = new Camera();
        camera.align(snake);

        camera.setSpeedX(0);
        camera.setSpeedY(1/speed);

        score = 0;
        scoreTextLayout = new GlyphLayout(font, "0");


        scoreY = main.HEIGHT - 146*main.SCALEY;
        scoreX = main.WIDTH /2 - main.SCALEX * scoreTextLayout.width /2;

        countDown = true;

    }



    @Override
    public void render(SpriteBatch batch, ShapeRenderer renderer, float alpha, float delta) {

        camera.update(delta);





        speedPowerup.update(delta);
        if(speedPowerup.isFinished()){
            speed *=2;
        }

        magnetPowerup.update(delta);

        backButton.update(delta);


        time+=delta;

        if (time > speed){

            if(snake.update()){
                listener.changeState(main.MAIN_MENU_STATE);
            }

            Field field = regions[snake.getHeadSegment().getY() / PlayingRegion.height][snake.getHeadSegment().getX() / PlayingRegion.width]
                    .getField(snake.getHeadSegment().getX() % PlayingRegion.width, snake.getHeadSegment().getY() % PlayingRegion.height);

            switch (field.getType()){
                case Field.WALL:{
                    if(speedPowerup.isActive()){
                        field.getAnimation().play();
                    }else {
                        GameData.CURRENT_SCORE = score;
                        listener.changeState(main.GAME_OVER_STATE);
                    }
                    break;
                }
                case Field.MAGNET_COIN:{
                    field.getAnimation().play();
                    magnetPowerup.activate();
                    break;
                }
                case Field.STANDARD_COIN:{
                    field.getAnimation().play();
                    score+=GameData.SNAKE_SPEED;
                    scoreTextLayout.setText(font, Integer.toString(score));
                    scoreX = main.WIDTH /2 - main.SCALEX * scoreTextLayout.width /2;
                    snake.grow();
                    break;
                }
                case Field.SPEED_COIN:{
                    field.getAnimation().play();
                    speedPowerup.activate();
                    speed /= 2;
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



        for(int i = 0; i < ROWS;i++) {
            for (int j = 0; j < COLUMNS; j++) {
                regions[i][j].processFields(j,i,camera,fieldRenderer,delta);
            }
        }




        renderer.setAutoShapeType(true);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        snake.draw(renderer, camera);
        renderer.end();



        batch.begin();

        fieldRenderer.render(batch);

        whiteImage.draw(batch);
        scoreImage.draw(batch);


        backButton.draw(batch);

        magnetPowerup.draw(batch);
        speedPowerup.draw(batch);

        font.getColor().a = alpha;

        font.draw(batch, Integer.toString(score), scoreX, scoreY);

        batch.end();



    }

    @Override
    public void touchDown(int x, int y) {
        xPressed = x;
        yPressed = y;


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

        scoreTexture = new Texture("play_gui/score.png");
        whiteTexture = new Texture("play_gui/white.png");
        lineTexture = new Texture("play_gui/lineg.png");

        magnetTextures = new Texture[3];
        magnetTextures[0] = new Texture("play_gui/magnet2.png");
        magnetTextures[1] = new Texture("play_gui/magnet1.png");
        magnetTextures[2] = new Texture("play_gui/magneticon.png");

        speedTextures = new Texture[3];
        speedTextures[0] = new Texture("play_gui/speed2.png");
        speedTextures[1] = new Texture("play_gui/speed1.png");
        speedTextures[2] = new Texture("play_gui/speedicon.png");

        menuTexture = new Texture("buttons/menu.png");

        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int)(80*main.SCALEX);
        parameter.color = new Color(0.929f, 0.78f, 0.255f, 1f);

        font = generator.generateFont(parameter);
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

        scoreTexture.dispose();
        whiteTexture.dispose();
        lineTexture.dispose();

        for(Texture texture:magnetTextures){
            texture.dispose();
        }
        for(Texture texture:speedTextures){
            texture.dispose();
        }

        menuTexture.dispose();

        font.dispose();
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
