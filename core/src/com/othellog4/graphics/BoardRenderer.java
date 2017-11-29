package com.othellog4.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.othellog4.game.GameSession;

public class BoardRenderer {
	
	final float GAME_WORLD_WIDTH = 1600;
	final float GAME_WORLD_HEIGHT = 900;
	
	Batch spriteBatch;
	GameSession session;
	OrthographicCamera cam;
	Viewport viewport;
	Texture image;
	ShapeRenderer shape;
	
	public BoardRenderer(Batch spriteBatch, GameSession session) {
		this.spriteBatch = spriteBatch;
		this.session = session;
		
		image = new Texture("badlogic.jpg");
		
		shape = new ShapeRenderer();
		
		cam = new OrthographicCamera();
		cam.position.set(GAME_WORLD_WIDTH/2, GAME_WORLD_HEIGHT/2, 0);
		viewport = new FitViewport(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT, cam);
		viewport.apply();
	}
	
	public void resize(int width, int height) {
		viewport.update(width, height);
		cam.position.set(GAME_WORLD_WIDTH/2, GAME_WORLD_HEIGHT/2, 0);
	}
	
	public void render() {
		cam.update();
		spriteBatch.setProjectionMatrix(cam.combined);
		shape.setProjectionMatrix(cam.combined);
		
		
		spriteBatch.begin();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		spriteBatch.end();
		
		shape.begin(ShapeType.Filled);
		shape.setColor(1,0,0,0);
		shape.rect(0, 0, GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
		shape.end();
		
		spriteBatch.begin();
		spriteBatch.draw(image, 0, 0);
		spriteBatch.end();
	}
}