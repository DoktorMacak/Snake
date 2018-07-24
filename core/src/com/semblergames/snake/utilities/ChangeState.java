package com.semblergames.snake.utilities;

/**
 * Created by momci on 7/24/2017.
 */

public interface ChangeState {
    public void changeState(int x, int y);

    public void playMusic();

    public void stopMusic();

    public void playClicked();

    public int getPreviousState();

    public void shareScore(int score);
}
