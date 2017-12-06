package com.othellog4.graphics;

import java.util.Optional;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.othellog4.game.GameSession;
import com.othellog4.game.board.BoardView;
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
	final float boardPadding = 60;
	final float boardBackgroundPadding = 30;
	final float piecePaddingPercent = 8;
	final float lineWidth = 5;

	private float boardSize;
	private float boardWidth;
	private float piecePaddingActual, pieceSizeActual;
	private float columnWidth;
	private float startingPosX;
	private float startingPosY;
	private float boardBackgroundX;
	private float boardBackgroundY;
	private float boardBackgroundWidth;
	
	// X and Y positions to draw pieces from
	// Note that this is the bottom left of the piece
	private float pieceXPositions[];
	private float pieceYPositions[];
	
	private Position posUnderMouse;

	private ProxyGameBoard board;

	Batch spriteBatch;
	OrthographicCamera cam;
	Viewport viewport;
	Texture image;
	ShapeRenderer shape;
	Texture whitePiece;
	Texture blackPiece;
	Texture emptyPiece;

	public BoardRenderer(Batch spriteBatch, BoardView board) {
		this.spriteBatch = spriteBatch;

		image = new Texture("badlogic.jpg");
		whitePiece = new Texture("whitepiece.png");
		blackPiece = new Texture("blackpiece.png");
		emptyPiece = new Texture("emptypiece.png");

		shape = new ShapeRenderer();

		cam = new OrthographicCamera();
		cam.position.set(GAME_WORLD_WIDTH / 2, GAME_WORLD_HEIGHT / 2, 0);
		viewport = new FitViewport(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT, cam);
		viewport.apply();

		this.board = (ProxyGameBoard) board;
		
		pieceXPositions = new float[board.size()];
		pieceYPositions = new float[board.size()];

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
		
		generatePieceCoordinates();

	}

	public void resize(int width, int height) {
		viewport.update(width, height);
		cam.position.set(GAME_WORLD_WIDTH / 2, GAME_WORLD_HEIGHT / 2, 0);
	}
	
	public void update() {
		updatePosUnderMouse();
		//System.out.println(posUnderMouse);
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
		// Dark green background
		shape.rect(boardBackgroundX, boardBackgroundY, boardBackgroundWidth, boardBackgroundWidth);
		shape.setColor(0.02f, 0.4f, 0.043f, 1);
		// Light green inner
		shape.rect(startingPosX,startingPosY - boardWidth,boardWidth,boardWidth);
		shape.setColor(0.01f, 0.2f, 0.022f, 1);
		//shape.setColor(1.00f, 0.2f, 0.022f, 1);
		float startingY = startingPosY - boardWidth;
		
		//Draw board lines
		for(int x = 0; x <= boardSize ; x++){
			shape.rect((startingPosX + (x*columnWidth) - (lineWidth/2)),startingY,lineWidth,boardWidth);
		}
		for (int y = 0 ; y <= boardSize; y++){
			shape.rect(startingPosX,(startingY + (y*columnWidth) - (lineWidth/2)),boardWidth,lineWidth);
			
		}
		
		shape.end();

		spriteBatch.begin();

		for (int x = 0; x < boardSize; x++) {
			for (int y = 0; y < boardSize; y++) {
				
				
				Texture actualPiece = null;
				Optional<Piece> optional = board.view(Position.at(x, y));
				
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
				}
				spriteBatch.draw(actualPiece, pieceXPositions[x],
						pieceYPositions[y], pieceSizeActual, pieceSizeActual);
			}
		}

		spriteBatch.end();

	}
	
	/**
	 * Return the board position of the piece the mouse is currently hovering over
	 * @return Position
	 */
	public Position getPosUnderMouse() {
		return posUnderMouse;
	}
	
	/**
	 * Update the board position of the piece the mouse is currently hovering over
	 * Used internally for rendering piece highlights
	 */
	private void updatePosUnderMouse() {
		Vector2 mousePos = getUnprojectedMousePos();
		//System.out.println(mousePos);
		Integer xPos = null;
		Integer yPos = null;
		
		//Find if within x bounds of piece
		for(int x = 0; x<pieceXPositions.length; x++) {
			if(mousePos.x >= pieceXPositions[x] && 
					mousePos.x <= pieceXPositions[x] + pieceSizeActual) {
				xPos = x;
				break;
			}
		}
		if(xPos == null) {
			posUnderMouse = null;
			return;
		}
		
		//Find if within y bounds of piece
		for(int y = 0; y<pieceYPositions.length; y++) {
			if(mousePos.y >= pieceYPositions[y] && 
					mousePos.y <= pieceYPositions[y] + pieceSizeActual) {
				yPos = y;
				break;
			}
		}
		if(yPos == null) {
			posUnderMouse = null;
			return;
		} else {
			posUnderMouse = Position.at(xPos, yPos);
			return;
		}
	}
	
	/**
	 * Get the mouse position within the world space
	 * @return Vector2 mouse position
	 */
	private Vector2 getUnprojectedMousePos() {
		Vector3 mouseActualPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		viewport.unproject(mouseActualPos);
		return new Vector2(mouseActualPos.x, mouseActualPos.y);
	}
	
	/**
	 * Update piece position coordinate arrays
	 */
	private void generatePieceCoordinates() {
		for(int x=0; x<pieceXPositions.length; x++) {
			pieceXPositions[x] = startingPosX + piecePaddingActual + (columnWidth * x);
		}
		for(int y=0; y<pieceYPositions.length; y++) {
			pieceYPositions[y] = startingPosY - piecePaddingActual - (columnWidth * y) - pieceSizeActual;
		}
	}
}