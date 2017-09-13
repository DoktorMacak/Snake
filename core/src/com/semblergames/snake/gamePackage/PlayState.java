package com.semblergames.snake.gamePackage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
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
import com.semblergames.snake.utilities.CountDownView;
import com.semblergames.snake.utilities.Direction;
import com.semblergames.snake.utilities.GameData;
import com.semblergames.snake.utilities.Image;
import com.semblergames.snake.utilities.ImageShow;
import com.semblergames.snake.utilities.MagnetedCoin;
import com.semblergames.snake.utilities.MagnetedCoinGroup;
import com.semblergames.snake.utilities.Point;
import com.semblergames.snake.utilities.PowerupHandler;
import com.semblergames.snake.utilities.Skin;


public class PlayState extends GameState {

    private static final int COLUMNS = 5;
    private static final int ROWS = 7;


    //pozicija misa
    private int xPressed;
    private int yPressed;


    // texture za teren

    public static Texture [] wallTextures;
    public static Texture [] standardCoinTextures;
    public static Texture [] magnetCoinTextures;
    public static Texture [] speedCoinTexture;
    public static Texture [] pointTexture;

    //teksture za okruzenje

    private Texture scoreTexture;
    private Texture whiteTexture;
    private Texture lineTexture;
    private Texture [] magnetTextures;
    private Texture [] speedTextures;
    private Texture [] numbers;
    private Texture fadeTexture;
    private Texture pauseTexture;


    //texture za tastere

    private Texture backTexture;
    private Texture resumeTexture;
    private Texture quitTexture;


    //texture za zmiju

    //font

    private BitmapFont font;


    // elementi terena

    private PlayingRegion[][] regions;

    private FieldRenderer fieldRenderer;

    private MagnetedCoinGroup magnetedCoinGroup;

    //elementi okruzenja


    private Button backButton;
    private Button resumeButton;
    private Button quitButton;

    private Image fadeImage;
    private Image pauseImage;
    private Image whiteImage;
    private Image scoreImage;
    private Image lineL;
    private Image lineR;

    private PowerupHandler speedPowerup;
    private PowerupHandler magnetPowerup;

    private CountDownView countDownView;


    //indikatori faza

    private boolean countDown;
    private boolean playing;
    private boolean paused;
    private boolean dead;

    //kamera

    private Camera camera;

    //zmija

    private Snake snake;
    private float speed;
    private float time;
    private Skin skin;


    //skor

    private GlyphLayout scoreTextLayout;

    private float scoreX;
    private float scoreY;

    private int score;

    //vreme mrtvog;
    private float deadTime;

    //zvuk
    private Sound boostSound;
    private Sound coinSound;
    private Sound dieSound;
    private Sound hitSound;

    public PlayState() {
    }

    @Override
    public void init() {

        //inicijalizacija terena

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

        magnetedCoinGroup = new MagnetedCoinGroup();

        //inicijalizacija okruzenja

        backButton = new Button(backTexture);
        backButton.setPosition(120*main.SCALEX, main.HEIGHT - 90*main.SCALEY);

        resumeButton = new Button(resumeTexture);
        resumeButton.setPosition(main.WIDTH /2, 1040*main.SCALEY);

        quitButton = new Button(quitTexture);
        quitButton.setPosition(main.WIDTH/2, 860*main.SCALEY);

        fadeImage = new Image(fadeTexture);
        fadeImage.setPosition(0,0);

        pauseImage = new Image(pauseTexture);
        pauseImage.setPosition(0,0);

        whiteImage = new Image(whiteTexture);
        whiteImage.setCentre(main.WIDTH/2, main.HEIGHT - main.SCALEY * whiteTexture.getHeight()/2);

        scoreImage = new Image(scoreTexture);
        scoreImage.setCentre(main.WIDTH/2, main.HEIGHT - 90*main.SCALEY);

        magnetPowerup = new PowerupHandler(magnetTextures, Direction.right);
        speedPowerup = new PowerupHandler(speedTextures, Direction.left);

        countDownView = new CountDownView(numbers);
        countDownView.setPosition(main.WIDTH/2, main.HEIGHT/2 -90*main.SCALEY);

        // postavljanje skora

        score = 0;
        scoreTextLayout = new GlyphLayout(font, "0");
        scoreY = main.HEIGHT - 145*main.SCALEY;
        scoreX = main.WIDTH /2 - scoreTextLayout.width /2;

        //postavljanje linija

        float lineWidth = (main.WIDTH - (80*main.SCALEX + scoreTextLayout.width))/2;

        lineL = new Image(lineTexture);
        lineR = new Image(lineTexture);

        lineL.setPosition(lineWidth - lineTexture.getWidth()*main.SCALEX, main.HEIGHT - 197*main.SCALEY);
        lineR.setPosition(main.WIDTH - lineWidth, main.HEIGHT - 197*main.SCALEY);


        //postavljanje faze

        countDown = true;
        playing = false;
        dead = false;
        paused = false;

        //postavljanje zmije


        snake = new Snake(3, Direction.up, (COLUMNS* PlayingRegion.width) /2, (ROWS* PlayingRegion.height) /2 - 3, skin);

        switch(GameData.SNAKE_SPEED){
            case 1:{
                speed = 0.95f;
                break;
            }
            case 2:{
                speed = 0.8f;
                break;
            }
            case 3:{
                speed = 0.65f;
                break;
            }
            case 4:{
                speed = 0.5f;
                break;
            }
            case 5:{
                speed = 0.35f;
                break;
            }
        }

        time = 0f;


        //postavljanje kamere

        camera = new Camera();
        camera.align(snake);

        camera.setSpeedX(0);
        camera.setSpeedY(1/speed);

        //vreme mrtvog
        deadTime = 0;

    }



    @Override
    public void render(SpriteBatch batch, ShapeRenderer renderer, float alpha, float delta) {

        //
        //
        //update
        //
        //

        //promena zmije
        boolean snakeChanged = false;


        //dok se igra

        if(playing){

            // update powerupova

            speedPowerup.update(delta);
            if(speedPowerup.isFinished()){
                speed *=2;

            }

            magnetPowerup.update(delta);

            //update magnetizovanih
            magnetedCoinGroup.update(delta);

            //taster za meni
            backButton.update(delta);

            //kamera
            camera.update(delta);

            //zmija

            time+=delta;

            if (time > speed) {
                //ciscenje magnetnih novcica
                magnetedCoinGroup.clear();

                snakeChanged = true;


                if (snake.update()) {
                    dead = true;
                    playing = false;
                    if(GameData.PLAY_SOUNDS) {
                        dieSound.play();
                    }
                }

                int regionRow = snake.getHeadSegment().getY() / PlayingRegion.height;
                int regionColumn = snake.getHeadSegment().getX() / PlayingRegion.width;


                Field field = regions[regionRow][regionColumn]
                        .getField(snake.getHeadSegment().getX() % PlayingRegion.width, snake.getHeadSegment().getY() % PlayingRegion.height);

                switch (field.getType()) {
                    case Field.WALL: {
                        field.getAnimation().play();
                        if (!speedPowerup.isActive()) {
                            dead = true;
                            playing = false;
                            if(GameData.PLAY_SOUNDS) {
                                dieSound.play();
                            }
                        }else{
                            if(GameData.PLAY_SOUNDS) {
                                hitSound.play();
                            }
                        }
                        break;
                    }
                    case Field.MAGNET_COIN: {
                        field.getAnimation().play();
                        magnetPowerup.activate();
                        if(GameData.PLAY_SOUNDS) {
                            boostSound.play();
                        }
                        break;
                    }
                    case Field.STANDARD_COIN: {
                        field.getAnimation().play();
                        score += GameData.SNAKE_SPEED;
                        scoreTextLayout.setText(font, Integer.toString(score));
                        scoreX = main.WIDTH / 2 - scoreTextLayout.width / 2;

                        float lineWidth = (main.WIDTH - (80 * main.SCALEX + scoreTextLayout.width)) / 2;

                        lineL.setX(lineWidth - lineTexture.getWidth() * main.SCALEX);

                        lineR.setX(main.WIDTH - lineWidth);

                        snake.grow();
                        if(GameData.PLAY_SOUNDS) {
                            coinSound.play();
                        }
                        break;
                    }
                    case Field.SPEED_COIN: {
                        field.getAnimation().play();
                        if (!speedPowerup.isActive()) {
                            speed /= 2;
                        }
                        speedPowerup.activate();
                        if(GameData.PLAY_SOUNDS) {
                            boostSound.play();
                        }
                        break;
                    }
                    case Field.POINT_STAR: {
                        field.getAnimation().play();
                        GameData.POINT_STARS++;
                        if(GameData.PLAY_SOUNDS) {
                            coinSound.play();
                        }
                        break;
                    }
                }


                if (snake.getHeadSegment().getX() == ((COLUMNS / 2) + 1) * PlayingRegion.width) {
                    moveEverything(Direction.left);
                }
                if (snake.getHeadSegment().getX() == (COLUMNS / 2) * PlayingRegion.width - 1) {
                    moveEverything(Direction.right);
                }
                if (snake.getHeadSegment().getY() == ((ROWS / 2) + 1) * PlayingRegion.height) {
                    moveEverything(Direction.down);
                }
                if (snake.getHeadSegment().getY() == (ROWS / 2) * PlayingRegion.height - 1) {
                    moveEverything(Direction.up);
                }

                time = 0f;
                camera.setSpeedToSnake(snake,speed);


            }

        }else if(countDown){

            countDownView.update(delta);
            if(countDownView.isFinished()){
                countDown = false;
                playing = true;
            }

        }else if(paused){

            resumeButton.update(delta);
            quitButton.update(delta);

        }else if(dead){

            //promena vremena i prelazak na ekran kraja

            deadTime+= delta;

            if(deadTime > 1.5f){
                deadTime = -1;
                GameData.CURRENT_SCORE = score;
                if(score > GameData.HIGH_SCORE){
                    GameData.HIGH_SCORE = score;
                }
                listener.changeState(main.GAME_OVER_STATE);
            }

        }


        //update terena

        for(int i = 0; i < ROWS;i++) {
            for (int j = 0; j < COLUMNS; j++) {
                regions[i][j].processFields(j,i,camera,fieldRenderer,delta);
            }
        }

        if(snakeChanged && magnetPowerup.isActive()){
            float headX = (snake.getHeadSegment().getX()-camera.getX()) * main.BLOCK_WIDTH;
            float headY = (snake.getHeadSegment().getY() - camera.getY()) * main.BLOCK_HEIGHT;

            boolean coinPicked = false;

            for(Field field:fieldRenderer.getStandardCoins()){
                if(magnetedCoinGroup.processField(field, headX, headY,speed)){
                    field.setType(Field.EMPTY);

                    score += GameData.SNAKE_SPEED;
                    scoreTextLayout.setText(font, Integer.toString(score));
                    scoreX = main.WIDTH / 2 - scoreTextLayout.width / 2;

                    float lineWidth = (main.WIDTH - (80 * main.SCALEX + scoreTextLayout.width)) / 2;

                    lineL.setX(lineWidth - lineTexture.getWidth() * main.SCALEX);

                    lineR.setX(main.WIDTH - lineWidth);

                    snake.grow();

                    coinPicked = true;
                }

            }

            if(coinPicked) {
                if(GameData.PLAY_SOUNDS) {
                    coinSound.play();
                }
            }

        }



        //
        //
        //render
        //
        //


        //render zmije

        renderer.setAutoShapeType(true);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.end();

        batch.begin();
        snake.draw(batch, camera);

        //render terena
        fieldRenderer.render(batch);

        //render magnetizovanih
        magnetedCoinGroup.draw(batch);

        //render okruzenja
        whiteImage.draw(batch);

        scoreImage.draw(batch);

        backButton.draw(batch);

        magnetPowerup.draw(batch);
        speedPowerup.draw(batch);

        lineL.draw(batch);
        lineR.draw(batch);

        font.getColor().a = alpha;

        font.draw(batch, Integer.toString(score), scoreX, scoreY);


        if(countDown){

            fadeImage.draw(batch);
            countDownView.draw(batch);

        }else if(paused){

            fadeImage.draw(batch);
            pauseImage.draw(batch);

            resumeButton.draw(batch);
            quitButton.draw(batch);

        }

        batch.end();

    }

    @Override
    public void touchDown(int x, int y) {

        if(playing){

            xPressed = x;
            yPressed = y;

            backButton.handleDown(x,y);
        }else if(paused){

            resumeButton.handleDown(x,y);
            quitButton.handleDown(x,y);

        }

    }

    @Override
    public void touchDragged(int x, int y) {

        if(playing){
            backButton.handleDown(x,y);
        }else if(paused){
            resumeButton.handleDown(x,y);
            quitButton.handleDown(x,y);
        }

    }

    @Override
    public void touchUp(int x, int y) {

        if(playing){

            int dx = xPressed - x;
            int dy = yPressed - y;


            if (Math.abs(dx) > Math.abs(dy)){
                if(snake.getDirection() != Direction.right && snake.getDirection() != Direction.left && Math.abs(dx) > 50*main.SCALEX) {


                    if (dx > 0) {
                        if(snake.setDirection(Direction.left)){}


                    } else {
                        if(snake.setDirection(Direction.right)){}

                    }
                }
            }else{
                if (snake.getDirection() != Direction.up && snake.getDirection() != Direction.down && Math.abs(dy) > 50*main.SCALEY) {


                    if (dy > 0) {
                        if(snake.setDirection(Direction.down)){}

                    } else {
                        if(snake.setDirection(Direction.up)){}

                    }
                }
            }

            if(backButton.handleUp(x,y)){

                playing = false;
                paused = true;
                listener.playClicked();
            }

        }else if(paused){

            if(resumeButton.handleUp(x,y)){
                paused = false;
                countDown = true;
                countDownView.restart();
                listener.playClicked();
            }

            if(quitButton.handleUp(x,y)){
                listener.changeState(main.MAIN_MENU_STATE);
                listener.playClicked();
            }

        }

    }

    @Override
    public void backPressed() {

        if(playing){
            playing = false;
            paused = true;
        }else if(countDown){
            countDown = false;
            paused = true;
        }else if(paused){
            paused = false;
            countDown = true;
            countDownView.restart();
        }
    }

    @Override
    public void initTexturesAndFonts(FreeTypeFontGenerator generator) {

        Pattern.loadPatterns();

        skin = new Skin(GameData.SKIN_POINTER+1);

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

        backTexture = new Texture("buttons/menu.png");
        resumeTexture = new Texture("buttons/resume.png");
        quitTexture = new Texture("buttons/quit.png");
        fadeTexture = new Texture("buttons/fade.png");
        pauseTexture = new Texture("buttons/pause.png");



        numbers = new Texture[3];
        for(int i = 0;i < 3;i++){
            numbers[i] = new Texture("buttons/numbers/"+(i+1)+"w.png");
        }

        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int)(80*main.SCALEX);
        parameter.color = new Color(0.929f, 0.78f, 0.255f, 1f);

        font = generator.generateFont(parameter);

        //zvuci
        boostSound = Gdx.audio.newSound(Gdx.files.internal("sounds/boost.wav"));
        coinSound = Gdx.audio.newSound(Gdx.files.internal("sounds/coin.wav"));
        dieSound = Gdx.audio.newSound(Gdx.files.internal("sounds/die.wav"));
        hitSound = Gdx.audio.newSound(Gdx.files.internal("sounds/hit.wav"));
    }

    @Override
    protected void disposeTexturesAndFonts() {

        skin.dispose();

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

        backTexture.dispose();
        resumeTexture.dispose();
        quitTexture.dispose();
        fadeTexture.dispose();
        pauseTexture.dispose();

        font.dispose();

        boostSound.dispose();
        coinSound.dispose();
        dieSound.dispose();
        hitSound.dispose();
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
