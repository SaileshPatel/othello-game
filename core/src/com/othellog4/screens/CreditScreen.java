package com.othellog4.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.othellog4.Othello;
import com.othellog4.graphics.GraphicsUtil;

public class CreditScreen extends BaseScreen {

	Othello othello;

	Texture background;

	int startingY = -1900;
	int currentY;

	public CreditScreen(Othello othello) {
		this.othello = othello;

		background = new Texture("credits.png");
		background.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		currentY = -2500;

	}

	@Override
	public void render(float delta) {

		SPRITE_BATCH.begin();

		if (currentY <= 0) {
			currentY += 1.15;
			SPRITE_BATCH.draw(background, 0, currentY, 1600, 3500);
		}

		SPRITE_BATCH.end();

		if (Gdx.input.isKeyPressed(Input.Keys.ENTER) || Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			this.dispose();
			othello.switchToOption();
		}

	}

}
