package com.semblergames.snake.utilities;

public class FieldAnimation {


    public final static int LAST_SCENE = -1;

    private int index;

    private int length;

    private float duration;

    private float movieTime;

    private boolean playing;

    private boolean finished;

    private boolean playingOnce;

    public FieldAnimation(int length, float duration){
        movieTime = 0;
        this.length = length;
        this.duration = duration;
        index = 0;
        playing = false;
        finished = false;
        playingOnce = false;

    }


    public void playOnce(){
        playing = true;
        playingOnce = true;
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

            while(movieTime > duration){
                movieTime-= duration;
                index++;
                if(index == length){
                    index = 0;
                    finished = true;
                    if(playingOnce){
                        playingOnce = false;
                        playing = false;
                        movieTime = 0;
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

    public int getCurrentFrame(){
        return index;
    }



}
