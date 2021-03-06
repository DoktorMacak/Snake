package com.semblergames.snake.utilities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.semblergames.snake.main;

import java.util.ArrayList;

public class Animation {

    public final static int LAST_SCENE = -1;

    private ArrayList<Float> durations;

    private int index;

    private float movieTime;

    private boolean playing;

    private boolean finished;

    private boolean playingOnce;

    public Animation(){
        movieTime = 0;
        index = 0;
        playing = true;
        finished = false;
        playingOnce = false;

        durations = new ArrayList<Float>();

    }


    public void playOnce(){
        playing = true;
        index = 0;
        playingOnce = true;
        finished = false;
    }

    public void play(){
        playing = true;
    }

    public void pause(){
        playing = false;
    }

    public void stop(){
        playing = false;
        index = 0;
        playingOnce = false;
        finished = false;
    }

    public void restart(){
        playing = true;
        index = 0;
        playingOnce = false;
        finished = false;
    }

    public void update(float delta){

        if(playing){
            movieTime += delta;

            while(movieTime > durations.get(index)){
                movieTime-= durations.get(index);
                index++;
                if(index == durations.size()){
                    index = 0;
                    finished = true;
                    if(playingOnce){
                        playing = false;
                        movieTime = 0;
                        index = durations.size()-1;
                    }
                }
            }
        }

    }

    public boolean isPlaying() {
        return playing;
    }

    public boolean isFinished() {
        return finished;
    }

    public boolean isPlayingOnce() {
        return playingOnce;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public void setPlayingOnce(boolean playingOnce) {
        this.playingOnce = playingOnce;
    }

    public void setCurrentFrame(int index){
        if(index == LAST_SCENE){
            this.index = durations.size()-1;
        }else {
            this.index = index;
        }
    }

    public int getCurrentFrame(){
        return index;
    }

    public void addFrame(float duration){
        durations.add(duration);
    }

}
