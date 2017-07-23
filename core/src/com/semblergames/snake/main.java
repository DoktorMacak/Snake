package com.semblergames.snake;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.semblergames.snake.gamePackage.PlayState;

public class main extends ApplicationAdapter implements InputProcessor{

	public static float SCALEX;
	public static float SCALEY;

	public static float WIDTH;
	public static float HEIGHT;

	public static float BLOCK_WIDTH = 120;
	public static float BLOCK_HEIGHT = 120;

	private int prevX;
	private int prevY;


	private PlayState playState;


	private float alpha;

	private SpriteBatch batch;
	private ShapeRenderer renderer;

	@Override
	public void create () {

		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();

		SCALEX = WIDTH / (float)1080;
		SCALEY = HEIGHT / (float)1920;

		BLOCK_WIDTH *= SCALEX;
		BLOCK_HEIGHT *= SCALEY;

		batch = new SpriteBatch();
		renderer = new ShapeRenderer();

		playState = new PlayState();
		playState.init();

		alpha = 1f;

		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		playState.render(batch, renderer, alpha);
	}
	
	@Override
	public void dispose () {
		playState.dispose();
		batch.dispose();
		renderer.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		prevX = screenX;
		int y = (int)HEIGHT - screenY;
		prevY = y;
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		int y = (int)HEIGHT - screenY;
		playState.touchDragged(prevX, prevX, screenX, y);

		prevX = screenX;
		prevY = y;
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
