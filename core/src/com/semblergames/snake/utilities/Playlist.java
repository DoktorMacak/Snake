package com.semblergames.snake.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.semblergames.snake.main;

import java.util.Random;

public class Playlist {

    private static final float MAX_Y = 50*main.SCALEY;
    private static final float MIN_Y = -30*main.SCALEY;

    private Music[] songs;


    private Texture[] textures;

    private int current;

    private float time;

    private boolean playing;

    private int phase;

    private float x;
    private float y;

    private float vy;

    private Music currentSong;

    public Playlist() {
        songs = new Music[10];
        textures = new Texture[10];


        for (int i = 0; i < 10; i++) {
            songs[i] = Gdx.audio.newMusic(Gdx.files.internal("music/"+(i+1)+".mp3"));
            textures[i] = new Texture("music/" + (i + 1) + ".png");
        }
        playing = false;


        x = main.WIDTH / 2;
        y = MIN_Y;

        vy = 40;

        time = 0;

        phase = 0;


        current = 0;
        currentSong = songs[current];

    }

    public void pause(){
        playing = false;
        currentSong.stop();
        y = MIN_Y;
    }

    public void play(){
        playing = true;
        Random random = new Random();
        current = random.nextInt(songs.length);
        currentSong = songs[current];
        currentSong.play();
        y = MIN_Y;
        vy = 40;
        phase = 0;
        time = 0;
    }

    public void update(float delta){

        if(playing){
            switch (phase){
                case 0:{
                    y += vy*delta;
                    if(y > MAX_Y){
                        y = MAX_Y;
                        vy = 0;
                        phase = 1;
                        time = 0;
                    }
                    break;
                }
                case 1:{
                    time+=delta;
                    if(time > 10){
                        phase = 2;
                        time = 0;
                        vy = -40;
                    }
                    break;
                }
                case 2:{
                    y += vy*delta;
                    if(y < MIN_Y){
                        phase = 3;
                    }
                    break;
                }
            }

            if(!currentSong.isPlaying()){
                current++;
                if(current == songs.length){
                    current = 0;
                }
                currentSong = songs[current];
                currentSong.play();
                y = MIN_Y;
                vy = 40;
                phase = 0;
                time = 0;
            }
        }
    }

    public void draw(SpriteBatch batch){
        if(playing && y != MIN_Y) {
            Texture texture = textures[current];

            float width = texture.getWidth() * main.SCALEX;
            float height = texture.getHeight() * main.SCALEY;

            batch.draw(
                    texture,
                    x - width / 2,
                    y - height / 2,
                    x,
                    y,
                    width,
                    height,
                    1,
                    1,
                    0,
                    0,
                    0,
                    texture.getWidth(),
                    texture.getHeight(),
                    false, false
            );
        }
    }

    public void dispose(){
        for(int i =0; i < 10;i++){
            textures[i].dispose();
            songs[i].stop();
            songs[i].dispose();
        }
    }

}
