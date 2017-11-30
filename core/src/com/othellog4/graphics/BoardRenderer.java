package com.othellog4.graphics;

import java.util.Optional;

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
import com.othellog4.game.board.GameBoard;
import com.othellog4.game.board.Piece;
import com.othellog4.game.board.Position;
import com.othellog4.game.board.ProxyGameBoard;
/**
 * @author Zakeria Hirsi
 * @author James Shorthouse
 * @version 30/11/2017
 */
public class BoardRenderer {

	final int GAME_WORLD_WIDTH = 1600;
	final int GAME_WORLD_HEIGHT = 900;
	final int boardPadding = 60;
	final int boardBackgroundPadding = 30;
	final int piecePaddingPercent = 8;
	final int lineWidth = 10;

	private int boardSize;
	private int boardWidth;
	private int piecePaddingActual, pieceSizeActual;
	private int columnWidth;
	private int startingPosX;
	private int startingPosY;
	private int boardBackgroundX;
	private int boardBackgroundY;
	private int boardBackgroundWidth;

	private ProxyGameBoard board;

	Batch spriteBatch;
	GameSession session;
	OrthographicCamera cam;
	Viewport viewport;
	Texture image;
	ShapeRenderer shape;
	Texture whitePiece;
	Texture blackPiece;
	Texture emptyPiece;

	public BoardRenderer(Batch spriteBatch, GameSession session) {
		this.spriteBatch = spriteBatch;
		this.session = session;

		image = new Texture("badlogic.jpg");
		whitePiece = new Texture("whitepiece.png");
		blackPiece = new Texture("blackpiece.png");
		emptyPiece = new Texture("emptypiece.png");

		shape = new ShapeRenderer();

		cam = new OrthographicCamera();
		cam.position.set(GAME_WORLD_WIDTH / 2, GAME_WORLD_HEIGHT / 2, 0);
		viewport = new FitViewport(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT, cam);
		viewport.apply();

		board = (ProxyGameBoard) session.getBoard();

		boardWidth = GAME_WORLD_HEIGHT - (2 * boardPadding);
		boardSize = board.size();
		columnWidth = boardWidth / boardSize;
		piecePaddingActual = (columnWidth * piecePaddingPercent) / 100;
		pieceSizeActual = columnWidth - (2 * piecePaddingActual);

		startingPosX = (GAME_WORLD_WIDTH / 2) - (boardWidth / 2);
		startingPosY = (GAME_WORLD_HEIGHT / 2) + (boardWidth / 2);

		boardBackgroundWidth = GAME_WORLD_HEIGHT - (2 * boardBackgroundPadding);
		boardBackgroundX = (GAME_WORLD_WIDTH / 2) - (boardBackgroundWidth / 2);
		boardBackgroundY = (GAME_WORLD_HEIGHT / 2) - (boardBackgroundWidth / 2);

		System.out.println(startingPosY);

	}

	public void resize(int width, int height) {
		viewport.update(width, height);
		cam.position.set(GAME_WORLD_WIDTH / 2, GAME_WORLD_HEIGHT / 2, 0);
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
		shape.setColor(0.27f, 0.12f, 0.02f, 1);
		shape.rect(0, 0, GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
		shape.setColor(0.01f, 0.2f, 0.022f, 1);
		shape.rect(boardBackgroundX, boardBackgroundY, boardBackgroundWidth, boardBackgroundWidth);
		shape.setColor(0.02f, 0.4f, 0.043f, 1);
		shape.rect(startingPosX,startingPosY - boardWidth,boardWidth,boardWidth);
		shape.setColor(0.01f, 0.2f, 0.022f, 1);
		int startingY = startingPosY - boardWidth;
		for(int x = 1; x < boardSize ; x++){
			shape.rect((int)(startingPosX + (x*columnWidth) - (0.5*lineWidth)),startingY,lineWidth,boardWidth);
		}
		for (int y = 1 ; y < boardSize; y++){
			shape.rect(startingPosX,(int)(startingY + (y*columnWidth) - (0.5*lineWidth)),boardWidth,lineWidth);
			
		}
		System.out.println(boardBackgroundX + " " + boardBackgroundY);
		
		
//		for (int x = 0; x<boardSize;x++){
//			for (int y = 0;boardSize;y++)
//			
//		}
		
		
		shape.end();

		spriteBatch.begin();

		for (int x = 0; x < boardSize; x++) {
			for (int y = 0; y < boardSize; y++) {
				
				
				Texture actualPiece = null;
				Optional<Piece> optional = board.view(new Position(x, y));
				
				if (optional.isPresent() == false) {
					actualPiece = emptyPiece;
				} else {
					Piece piece = (Piece) optional.get();
					switch (piece) {
					case PIECE_A:
						actualPiece = blackPiece;
						break;
					case PIECE_B:
						actualPiece = whitePiece;
						break;

					}
					spriteBatch.draw(actualPiece, startingPosX + piecePaddingActual + (columnWidth * x),
							startingPosY - piecePaddingActual - (columnWidth * y) - pieceSizeActual, pieceSizeActual,
							pieceSizeActual);

				}

			}
		}

		spriteBatch.end();

	}
}