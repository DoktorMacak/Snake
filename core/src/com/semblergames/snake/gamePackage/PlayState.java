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
import com.semblergames.snake.utilities.WallHit;


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
    public static Texture [] wallDeadTexture;

    //teksture za okruzenje

    private Texture tipTexture;
    private Texture tipContinueTexture;
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
    private Texture restartTexture;


    //texture za zmiju

    //font

    private BitmapFont font;


    // elementi terena

    private PlayingRegion[][] regions;

    private FieldRenderer fieldRenderer;

    private MagnetedCoinGroup magnetedCoinGroup;

    private WallHit wallHit;

    //elementi okruzenja


    private Button backButton;
    private Button resumeButton;
    private Button quitButton;
    private Button restartButton;

    private Image fadeImage;
    private Image pauseImage;
    private Image whiteImage;
    private Image scoreImage;
    private Image lineL;
    private Image lineR;

    private PowerupHandler speedPowerup;
    private PowerupHandler magnetPowerup;

    private Image tipImage;
    private Image tipContinueImage;


    //indikatori faza

    private boolean tip;
    private boolean postPause;
    private boolean playing;
    private boolean paused;
    private boolean dead;

    //kamera

    private Camera camera;

    //zmija

    private Snake snake;
    private float speed;
    private float time;
    private Skin[] skins;
    private float [] speeds;
    private float speedAccel;


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
    private Sound pointSound;

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

        wallHit = new WallHit(wallDeadTexture);

        //inicijalizacija okruzenja

        backButton = new Button(backTexture);
        backButton.setPosition(120*main.SCALEX, main.HEIGHT - 90*main.SCALEY);

        resumeButton = new Button(resumeTexture);
        resumeButton.setPosition(main.WIDTH /2, 1130*main.SCALEY);

        restartButton = new Button(restartTexture);
        restartButton.setPosition(main.WIDTH/2, 950*main.SCALEY);

        quitButton = new Button(quitTexture);
        quitButton.setPosition(main.WIDTH/2, 770*main.SCALEY);

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

        tipImage = new Image(tipTexture);
        tipImage.setPosition(main.WIDTH/2 - (float)tipTexture.getWidth()*main.SCALEX/2, 930*main.SCALEY);

        tipContinueImage = new Image(tipContinueTexture);
        tipContinueImage.setPosition(main.WIDTH/2 - (float)tipTexture.getWidth()*main.SCALEX/2, 930*main.SCALEY);

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

        tip = true;
        playing = false;
        dead = false;
        paused = false;
        postPause = false;

        //postavljanje zmije


        snake = new Snake(3, Direction.up, (COLUMNS* PlayingRegion.width) /2, (ROWS* PlayingRegion.height) /2 - 3, skins[GameData.SKIN_POINTER]);

        speeds = new float[5];
        speeds[0] = 0.26f;
        speeds[1] = 0.24f;
        speeds[2] = 0.22f;
        speeds[3] = 0.20f;
        speeds[4] = 0.18f;

        speed = speeds[GameData.SNAKE_SPEED-1];

        speedAccel = speed/3f;

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
            if(speedPowerup.isActive() && speedPowerup.getTimeLapsed() > 6.5f){
                speed+=delta*speedAccel;
                if(speed > speeds[GameData.SNAKE_SPEED-1]){
                    speed = speeds[GameData.SNAKE_SPEED-1];
                }
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

                if(!checkWllInFront()) {

                    snakeChanged = true;

                    if (snake.update()) {
                        dead = true;
                        playing = false;
                        if (GameData.PLAY_SOUNDS) {
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

                            if (GameData.PLAY_SOUNDS) {
                                hitSound.play();
                            }

                            break;
                        }
                        case Field.MAGNET_COIN: {
                            field.getAnimation().play();
                            magnetPowerup.activate();
                            if (GameData.PLAY_SOUNDS) {
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
                            if (GameData.PLAY_SOUNDS) {
                                coinSound.play();
                            }
                            break;
                        }
                        case Field.SPEED_COIN: {
                            field.getAnimation().play();
                            speed = speeds[GameData.SNAKE_SPEED-1] / 2;
                            speedPowerup.activate();
                            if (GameData.PLAY_SOUNDS) {
                                boostSound.play();
                            }
                            break;
                        }
                        case Field.POINT_STAR: {
                            field.getAnimation().play();
                            GameData.POINT_STARS++;
                            if (GameData.PLAY_SOUNDS) {
                                pointSound.play();
                            }
                            break;
                        }
                    }


                    if (snake.getHeadSegment().getX() == ((COLUMNS / 2) + 1) * PlayingRegion.width) {
                        moveEverything(Direction.left);
                    }
                    else if (snake.getHeadSegment().getX() == (COLUMNS / 2) * PlayingRegion.width - 1) {
                        moveEverything(Direction.right);
                    }
                    else if (snake.getHeadSegment().getY() == ((ROWS / 2) + 1) * PlayingRegion.height) {
                        moveEverything(Direction.down);
                    }
                    else if (snake.getHeadSegment().getY() == (ROWS / 2) * PlayingRegion.height - 1) {
                        moveEverything(Direction.up);
                    }

                    time = 0f;
                    camera.setSpeedToSnake(snake, speed);

                }

            }

        }else if(paused){

            resumeButton.update(delta);
            restartButton.update(delta);
            quitButton.update(delta);

        }else if(dead){

            wallHit.update(delta);

            //promena vremena i prelazak na ekran kraja

            deadTime+= delta;

            if(deadTime > 1.5f){
                deadTime = -20;
                GameData.CURRENT_SCORE = score;
                listener.changeState(main.GAME_OVER_STATE);
            }

        }else if(tip || postPause){
            backButton.update(delta);
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


        if(tip){

            tipImage.draw(batch);

        }else if(paused){

            fadeImage.draw(batch);
            pauseImage.draw(batch);

            resumeButton.draw(batch);
            restartButton.draw(batch);
            quitButton.draw(batch);

        }else if(dead){
            wallHit.draw(batch);
        }else if(postPause){
            tipContinueImage.draw(batch);
        }

        batch.end();

    }

    @Override
    public void pause(){
        playing = false;
        paused = true;
    }

    @Override
    public void touchDown(int x, int y) {

        if(playing){

            xPressed = x;
            yPressed = y;

            backButton.handleDown(x,y);
        }else if(paused){

            resumeButton.handleDown(x,y);
            restartButton.handleDown(x,y);
            quitButton.handleDown(x,y);

        }else if(tip || postPause){
            backButton.handleDown(x,y);
            xPressed = x;
            yPressed = y;
        }

    }

    @Override
    public void touchDragged(int x, int y) {

        if(playing){
            backButton.handleDown(x,y);
        }else if(paused){
            resumeButton.handleDown(x,y);
            restartButton.handleDown(x,y);
            quitButton.handleDown(x,y);
        }else if(tip || postPause){
            backButton.handleDown(x,y);
        }

    }

    @Override
    public void touchUp(int x, int y) {

        if(playing){

            int dx = xPressed - x;
            int dy = yPressed - y;


            if (Math.abs(dx) > Math.abs(dy)){
                if(snake.getDirection() != Direction.right && snake.getDirection() != Direction.left && Math.abs(dx) > 20*main.SCALEX) {

                    if (dx > 0) {
                        if(snake.setDirection(Direction.left)){}

                    } else {
                        if(snake.setDirection(Direction.right)){}

                    }
                }
            }else{
                if (snake.getDirection() != Direction.up && snake.getDirection() != Direction.down && Math.abs(dy) > 20*main.SCALEY) {

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
                postPause = true;
                listener.playClicked();
            }

            if(quitButton.handleUp(x,y)){
                listener.changeState(main.MAIN_MENU_STATE);
                listener.playClicked();
            }

            if(restartButton.handleUp(x,y)){

                for(int i = 0; i < ROWS;i++){
                    for (int j = 0; j < COLUMNS; j++){

                        if(i == (ROWS - 1)/2 && j == (COLUMNS -1)/2){
                            regions[i][j].init(PlayingRegion.EMPTY);
                        }else{
                            regions[i][j].init(PlayingRegion.FILLED);
                        }

                    }
                }

                magnetedCoinGroup.clear();

                speedPowerup.reset();
                magnetPowerup.reset();


                score = 0;
                scoreTextLayout.setText(font, "0");
                scoreY = main.HEIGHT - 145*main.SCALEY;
                scoreX = main.WIDTH /2 - scoreTextLayout.width /2;

                float lineWidth = (main.WIDTH - (80*main.SCALEX + scoreTextLayout.width))/2;

                lineL.setPosition(lineWidth - lineTexture.getWidth()*main.SCALEX, main.HEIGHT - 197*main.SCALEY);
                lineR.setPosition(main.WIDTH - lineWidth, main.HEIGHT - 197*main.SCALEY);


                tip = true;
                playing = false;
                dead = false;
                paused = false;
                postPause = false;

                snake = new Snake(3, Direction.up, (COLUMNS* PlayingRegion.width) /2, (ROWS* PlayingRegion.height) /2 - 3, skins[GameData.SKIN_POINTER]);

                speed = speeds[GameData.SNAKE_SPEED-1];

                time = 0f;

                camera.align(snake);

                camera.setSpeedX(0);
                camera.setSpeedY(1/speed);

                //vreme mrtvog
                deadTime = 0;


                listener.playClicked();
            }

        }else if(tip || postPause){

            if(backButton.handleUp(x,y)){
                tip = false;
                postPause = false;
                paused = true;
                listener.playClicked();
            }

            int dx = xPressed - x;
            int dy = yPressed - y;

            if (Math.abs(dx) > Math.abs(dy)){
                if(Math.abs(dx) > 20*main.SCALEX) {
                    if (dx > 0) {
                        if(snake.getDirection() == Direction.left){
                            playing = true;
                            tip = false;
                            postPause = false;
                        }else if(snake.getDirection() != Direction.right) {
                            snake.setDirection(Direction.left);
                            playing = true;
                            tip = false;
                            postPause = false;
                        }
                    } else {
                        if(snake.getDirection() == Direction.right){
                            playing = true;
                            tip = false;
                            postPause = false;
                        }else if(snake.getDirection() != Direction.left) {
                            snake.setDirection(Direction.right);
                            playing = true;
                            tip = false;
                            postPause = false;
                        }
                    }
                }
            }else{
                if (Math.abs(dy) > 20*main.SCALEY) {
                    if (dy > 0) {
                        if(snake.getDirection() == Direction.down){
                            playing = true;
                            tip = false;
                            postPause = false;
                        }else if(snake.getDirection() != Direction.up) {
                            snake.setDirection(Direction.down);
                            playing = true;
                            tip = false;
                            postPause = false;
                        }
                    } else {
                        if(snake.getDirection() == Direction.up){
                            playing = true;
                            tip = false;
                            postPause = false;
                        }else if(snake.getDirection() != Direction.down) {
                            snake.setDirection(Direction.up);
                            playing = true;
                            tip = false;
                            postPause = false;
                        }
                    }

                }
            }

        }

    }

    @Override
    public void backPressed() {

        if(playing){
            playing = false;
            paused = true;
        }else if(paused){
            paused = false;
            postPause = true;
        }else if(tip || postPause){
            postPause = false;
            tip = false;
            paused = true;
        }
    }

    @Override
    public void initTexturesAndFonts(FreeTypeFontGenerator generator) {

        Pattern.loadPatterns();

        skins = new Skin[GameData.SKINS.length];
        for (int i = 0;i<skins.length;i++){
            skins[i] = new Skin(i+1);
        }

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

        wallDeadTexture = new Texture[5];
        for(int i = 1; i < 6;i++){
            wallDeadTexture[i-1] = new Texture("field/wallh"+i+".png");
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
        restartTexture = new Texture("buttons/restart.png");

        tipTexture = new Texture("tutorial/start.png");
        tipContinueTexture = new Texture("tutorial/continue.png");



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
        pointSound = Gdx.audio.newSound(Gdx.files.internal("sounds/point.wav"));
    }

    @Override
    protected void disposeTexturesAndFonts() {

        for (Skin skin:skins){
            skin.dispose();
        }

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

        for(Texture texture:wallDeadTexture){
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
        restartTexture.dispose();

        tipTexture.dispose();
        tipContinueTexture.dispose();

        font.dispose();

        boostSound.dispose();
        coinSound.dispose();
        dieSound.dispose();
        hitSound.dispose();
        pointSound.dispose();
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

    private boolean checkWllInFront(){

        int snakeFutureX = 0;
        int snakeFutureY = 0;

        switch(snake.getDirection()){
            case right: {
                snakeFutureX = snake.getHeadSegment().getX() + 1;
                snakeFutureY = snake.getHeadSegment().getY();
                wallHit.setOrientation(Direction.left);
                break;
            }
            case left:{
                snakeFutureX = snake.getHeadSegment().getX() - 1;
                snakeFutureY = snake.getHeadSegment().getY();
                wallHit.setOrientation(Direction.right);
                break;
            }
            case down:{
                snakeFutureX = snake.getHeadSegment().getX() ;
                snakeFutureY = snake.getHeadSegment().getY() - 1;
                wallHit.setOrientation(Direction.up);
                break;
            }
            case up:{
                snakeFutureX = snake.getHeadSegment().getX();
                snakeFutureY = snake.getHeadSegment().getY() + 1;
                wallHit.setOrientation(Direction.down);
                break;
            }
        }

        wallHit.setPosition((snake.getHeadSegment().getX() - camera.getX())*main.BLOCK_WIDTH,(snake.getHeadSegment().getY() - camera.getY())*main.BLOCK_HEIGHT);

        int regionRow = snakeFutureY / PlayingRegion.height;
        int regionColumn = snakeFutureX / PlayingRegion.width;


        Field field = regions[regionRow][regionColumn]
                .getField(snakeFutureX % PlayingRegion.width, snakeFutureY % PlayingRegion.height);

        if(field.getType() == Field.WALL && !speedPowerup.isActive()){

            wallHit.activate();

            snake.setOver(true);
            dead = true;
            playing = false;
            if (GameData.PLAY_SOUNDS) {
                dieSound.play();
            }
            return true;
        }
        return false;
    }
}
