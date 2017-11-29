package com.othellog4;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.othellog4.screens.GameScreen;

public class Othello extends Game {
	private SpriteBatch spriteBatch;
	
	@Override
	public void create () {
		spriteBatch = new SpriteBatch();
		//Settings.load()
		//Assets.load()
		setScreen(new GameScreen(this));
	}

	@Override
	public void render () {
		super.render();
		//Gdx.gl.glClearColor(1, 0, 0, 1);
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//spriteBatch.begin();
		//spriteBatch.draw(img, 0, 0);
		//spriteBatch.end();
	}
	
	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}
	
//	@Override
//	public void dispose () {
//		batch.dispose();
//		img.dispose();
//	}
}
