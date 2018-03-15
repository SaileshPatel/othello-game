package com.othellog4.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.othellog4.Othello;
import com.othellog4.environment.GameMode;
import com.othellog4.environment.Launcher;
import com.othellog4.environment.PlayerType;
import com.othellog4.graphics.GraphicsUtil;
import com.othellog4.graphics.ScreenBoxField;

/**
 * Allows players to access online play
 * 
 * @author Zak Hirsi
 * @author John Berg
 * @since 08/03/2018
 */
public class MultiplayerMenuScreen extends BaseScreen {
	Texture background;
	Texture mascotButton;
	Texture numbers;
	TextureRegion[] animationFrames;
	Animation<TextureRegion> animation;
	float elapsedTime;

	int buttonWidth = 100;
	int buttonHeight = 100;
	int xPos = 0;
	int yPos = Othello.GAME_WORLD_HEIGHT - buttonHeight;

	TextField textBox;
	TextButton textButton;
	CheckBox checkbox;

	Othello othello;
	
	private PlayerType players = PlayerType.USER;

	Boolean con;

	final int GAME_WORLD_WIDTH = 1600;
	final int GAME_WORLD_HEIGHT = 900;

	Stage stage;
	private BitmapFont optionsFont;

	public MultiplayerMenuScreen(Othello othello) {
		this.othello = othello;

		background = new Texture("backgroundNew.png");
		mascotButton = GraphicsUtil.createMipMappedTex("backButton.png");
		numbers = new Texture("animation.png");

		TextureRegion[][] tempFrames = TextureRegion.split(numbers, 400, 400);
		animationFrames = new TextureRegion[4];
		int index = 0;

		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				animationFrames[index++] = tempFrames[i][j];

			}
		}
		animation = new Animation<TextureRegion> (1f/4f,animationFrames);

		con = false;
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("segoeuib.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 50; // Size in px
		parameter.spaceY = -25; // Vertical spacing
		optionsFont = generator.generateFont(parameter);
		optionsFont.setUseIntegerPositions(false);
		generator.dispose();
		optionsFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		optionsFont.setColor(1f, 1f, 1f, 1f);


		Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
		stage = new Stage();
		stage.setViewport(VIEWPORT);
		textBox = new TextField("", skin);
		textBox.setSize(225, 30);
		textBox.setMessageText("Enter lobby name or IP address");
		textBox.setPosition(620, 405);



		textButton = new TextButton("", skin);
		textButton.setSize(100, 30);
		textButton.setText("Submit!");
		textButton.setPosition(850, 405);
		stage.addActor(textBox);
		stage.addActor(textButton);

		//checkbox decleration
		checkbox = new CheckBox("", skin);
		checkbox.setPosition(980, 405);
		checkbox.setText("  Host");
		stage.addActor(checkbox);

	}

	public void render(float delta) {
		elapsedTime += delta;
		SPRITE_BATCH.begin();
		SPRITE_BATCH.draw(background, 0, 0, GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);


		Vector2 mousePos = GraphicsUtil.getMousePos();

		new ScreenBoxField(650, 450, 400, 40).before(box -> setColourNoHover())
		.after(box -> drawTextInBox("Multiplayer", box)).hover(mousePos.x, mousePos.y);

		new ScreenBoxField(0, 800, 100, 100).onHover(box -> {
			if (Gdx.input.justTouched()) {
				this.dispose();
				othello.switchToMenu();
			}
		}).after(box -> SPRITE_BATCH.draw(mascotButton, box.getX(), box.getY(), box.getWidth(), box.getHeight()))
		.hover(mousePos.x, mousePos.y);

		SPRITE_BATCH.end();

		if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || textButton.isPressed()) {
			if(textBox.getText().equals("")){
				//do nothing
			}else{
				String temp = "";
				if(checkbox.isChecked()) {
					temp = "host!:";
				}
				othello.runOnlineGame(Launcher.get().newGame(players, players,GameMode.CASUAL),temp + textBox.getText());
				con = true;
			}
		}

		SPRITE_BATCH.begin();

		if (con) {
			new ScreenBoxField(650, 300, 400, 40).before(box -> setColourNoHover())
			.after(box -> drawTextInBox("Connecting", box)).hover(mousePos.x, mousePos.y);
			SPRITE_BATCH.draw(animation.getKeyFrame(elapsedTime,true),550,25);

		}
		SPRITE_BATCH.end();

		stage.act();
		stage.draw();
		Gdx.input.setInputProcessor(stage);

	}

	private void setColourNoHover() {
		optionsFont.setColor(1f, 1f, 1f, 1f);
	}

	private void drawTextInBox(final String text, final ScreenBoxField box) {
		optionsFont.draw(SPRITE_BATCH, text, box.getX(), box.getY() + box.getHeight(), box.getWidth(), Align.left,
				true);
	}

	//	protected void postResize(int width, int height){
	//		stage.getViewport().update(width, height, true);
	//	}

}
