package com.othellog4.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.othellog4.Othello;
import com.othellog4.environment.GameMode;
import com.othellog4.environment.Launcher;
import com.othellog4.environment.PlayerType;
import com.othellog4.graphics.GraphicsUtil;
import com.othellog4.graphics.ScreenBoxField;


/**
 * This is the player select screen where
 * users can toggle different options
 * @author Zakeria Hirsi
 * @author John Berg
 * @since 07/03/2018
 * @version 07/03/2018
 */
public class PlayerSelectScreen extends BaseScreen{
	final int GAME_WORLD_WIDTH = 1600;
	final int GAME_WORLD_HEIGHT = 900;	
	
	Texture background;
	Texture whitePiece;
	Texture blackPiece;
	Texture blackStart;
	Texture whiteStart;
	Texture mascotButton;
	
	
	int buttonWidth = 100;
	int buttonHeight = 100;
	
	Othello othello;
	private PlayerType player1 = PlayerType.USER;
	private PlayerType player2 = PlayerType.USER;
	private BitmapFont optionsFont;
	
	public PlayerSelectScreen(Othello othello){
		this.othello=othello;
		background = new Texture("wood.jpeg");
		blackPiece = GraphicsUtil.createMipMappedTex("blackPiece.png");
		whitePiece = GraphicsUtil.createMipMappedTex("whitePiece.png");
		blackStart =  GraphicsUtil.createMipMappedTex("blackStart.png");
		whiteStart =  GraphicsUtil.createMipMappedTex("whiteStart.png");
		mascotButton = GraphicsUtil.createMipMappedTex("backButton.png");

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("segoeuib.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 50; // Size in px
		parameter.spaceY = -25; // Vertical spacing
		optionsFont = generator.generateFont(parameter);
		optionsFont.setUseIntegerPositions(false);
		generator.dispose();
		optionsFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		optionsFont.setColor(1f, 1f, 1f, 1f);

	}
	private void preparePlayer1()
	{
		Vector2 mousePos = GraphicsUtil.getMousePos();
		new ScreenBoxField(206, 540, 180, 40)
		.before(box ->
		{
			if (getPlayer1() == PlayerType.USER)
				drawBlackPieceInBox(box);
		})
		.onHover(box ->
		{
			setColourHover();
			if(Gdx.input.isTouched())
				setPlayer1(PlayerType.USER);
		})
		.noHover(box -> setColourNoHover())
		.after(box -> drawTextInBox("Player", box))
		.hover(mousePos.x, mousePos.y);
		new ScreenBoxField(206, 460, 250, 40)
		.before(box ->
		{
			if (getPlayer1() == PlayerType.AI_EASY)
				drawBlackPieceInBox(box);
		})
		.onHover(box ->
		{
			setColourHover();
			if(Gdx.input.isTouched())
				setPlayer1(PlayerType.AI_EASY);
		})
		.noHover(box -> setColourNoHover())
		.after(box -> drawTextInBox("COM Easy", box))
		.hover(mousePos.x, mousePos.y);
		new ScreenBoxField(206, 380, 340, 40)
		.before(box ->
		{
			if (getPlayer1() == PlayerType.AI_MEDIUM)
				drawBlackPieceInBox(box);
		})
		.onHover(box ->
		{
			setColourHover();
			if(Gdx.input.isTouched())
				setPlayer1(PlayerType.AI_MEDIUM);
		})
		.noHover(box -> setColourNoHover())
		.after(box -> drawTextInBox("COM Medium", box))
		.hover(mousePos.x, mousePos.y);
		new ScreenBoxField(206, 300, 250, 40)
		.before(box ->
		{
			if (getPlayer1() == PlayerType.AI_HARD)
				drawBlackPieceInBox(box);
		})
		.onHover(box ->
		{
			setColourHover();
			if(Gdx.input.isTouched())
				setPlayer1(PlayerType.AI_HARD
						);
		})
		.noHover(box -> setColourNoHover())
		.after(box -> drawTextInBox("COM Hard", box))
		.hover(mousePos.x, mousePos.y);
	}
	
	
	private void preparePlayer2()
	{
		Vector2 mousePos = GraphicsUtil.getMousePos();
		new ScreenBoxField(900, 540, 180, 40)
		.before(box ->
		{
			if (getPlayer2() == PlayerType.USER)
				drawWhitePieceInBox(box);
		})
		.onHover(box ->
		{
			setColourHover();
			if(Gdx.input.isTouched())
				setPlayer2(PlayerType.USER);
		})
		.noHover(box -> setColourNoHover())
		.after(box -> drawTextInBox("Player", box))
		.hover(mousePos.x, mousePos.y);
		new ScreenBoxField(900, 460, 250, 40)
		.before(box ->
		{
			if (getPlayer2() == PlayerType.AI_EASY)
				drawWhitePieceInBox(box);
		})
		.onHover(box ->
		{
			setColourHover();
			if(Gdx.input.isTouched())
				setPlayer2(PlayerType.AI_EASY);
		})
		.noHover(box -> setColourNoHover())
		.after(box -> drawTextInBox("COM Easy", box))
		.hover(mousePos.x, mousePos.y);
		new ScreenBoxField(900, 380, 340, 40)
		.before(box ->
		{
			if (getPlayer2() == PlayerType.AI_MEDIUM)
				drawWhitePieceInBox(box);
		})
		.onHover(box ->
		{
			setColourHover();
			if(Gdx.input.isTouched())
				setPlayer2(PlayerType.AI_MEDIUM);
		})
		.noHover(box -> setColourNoHover())
		.after(box -> drawTextInBox("COM Medium", box))
		.hover(mousePos.x, mousePos.y);
		new ScreenBoxField(900, 300, 250, 40)
		.before(box ->
		{
			if (getPlayer2() == PlayerType.AI_HARD)
				drawWhitePieceInBox(box);
		})
		.onHover(box ->
		{
			setColourHover();
			if(Gdx.input.isTouched())
				setPlayer2(PlayerType.AI_HARD);
		})
		.noHover(box -> setColourNoHover())
		.after(box -> drawTextInBox("COM Hard", box))
		.hover(mousePos.x, mousePos.y);
	}
	
	
	public void render (float delta){
		SPRITE_BATCH.begin();
		SPRITE_BATCH.draw(background, 0, 0, GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
		Vector2 mousePos = GraphicsUtil.getMousePos();
		//System.out.println(mousePos.x + " " + mousePos.y);
		
		
		
		new ScreenBoxField(0, 800, 100, 100)
		.onHover(box -> {
			if(Gdx.input.justTouched()){
				this.dispose();
				othello.switchToMenu();
			}
		})
		.after(box -> SPRITE_BATCH.draw(
				mascotButton,
				box.getX(),
				box.getY(),
				box.getWidth(),
				box.getHeight()))
		.hover(mousePos.x, mousePos.y);
		
		
		
		
		
		new ScreenBoxField(206, 626, 200, 40)
		.before(box -> setColourNoHover())
		.after(box -> drawTextInBox("Player 1", box))
		.hover(mousePos.x, mousePos.y);
		
		new ScreenBoxField(900, 626, 200, 40)
		.before(box -> setColourNoHover())
		.after(box -> drawTextInBox("Player 2", box))
		.hover(mousePos.x, mousePos.y);
		preparePlayer1();
		preparePlayer2();
		new ScreenBoxField(500, 30, 180, 180)
		.onHover(box -> {
			if(Gdx.input.isTouched())
				othello.runGame(Launcher.get()
						.newGame(getPlayer1(), getPlayer2(), GameMode.CASUAL));
		})
		.after(box -> 
		SPRITE_BATCH.draw(whiteStart, box.getX(), box.getY(), box.getWidth(), box.getHeight()))
		.hover(mousePos.x, mousePos.y);
		
		new ScreenBoxField(800, 30, 180, 180)
		.onHover(box -> {
			if(Gdx.input.isTouched())
				//START GAME
				othello.runGame(Launcher.get()
						.newGame(getPlayer1(), getPlayer2(),
								GameMode.COMPETATIVE));
		})
		.after(box -> 
		SPRITE_BATCH.draw(blackStart, box.getX(), box.getY(), box.getWidth(), box.getHeight()))
		.hover(mousePos.x, mousePos.y);

		
		SPRITE_BATCH.end();
		
	}
	private void setPlayer1(final PlayerType type)
	{
		player1 = type;
	}
	private PlayerType getPlayer1()
	{
		return player1;
	}
	private void setPlayer2(final PlayerType type)
	{
		player2 = type;
	}
	private PlayerType getPlayer2()
	{
		return player2;
	}
	private void setColourHover()
	{
		optionsFont.setColor(0.83f, 0.94f, 0.68f, 1f);
	}
	
	private void setColourNoHover()
	{
		optionsFont.setColor(1f, 1f, 1f, 1f);
	}
	
	private void drawTextInBox(
			final String text,
			final ScreenBoxField box)
	{
		optionsFont.draw(
				SPRITE_BATCH,
				text,
				box.getX(),
				box.getY() + box.getHeight(),
				box.getWidth(),
				Align.left,
				true);
	}
	private void drawBlackPieceInBox(final ScreenBoxField box)
	{
		SPRITE_BATCH.draw(
				blackPiece, box.getX() + box.getWidth(),
				box.getY(),
				box.getHeight(),
				box.getHeight());
	}
	
	private void drawWhitePieceInBox(final ScreenBoxField box)
	{
		SPRITE_BATCH.draw(
				whitePiece, box.getX() + box.getWidth(),
				box.getY(),
				box.getHeight(),
				box.getHeight());
	}
}
