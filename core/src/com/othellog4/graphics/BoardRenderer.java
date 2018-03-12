package com.othellog4.graphics;

import java.util.Optional;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.othellog4.Othello;
import com.othellog4.game.GameModel;
import com.othellog4.game.board.Piece;
import com.othellog4.game.board.Position;
import com.othellog4.game.board.ProxyGameBoard;
//import com.badlogic.gdx.utils.BaseScreen.VIEWPORT.FitViewport;
import com.othellog4.screens.BaseScreen;

/**
 * This class deals with rendering the board game, including all textures and sprites used.
 * @see com.othellog4.screens.GameScreen GameScreen
 *
 * @author Zakeria Hirsi
 * @author James Shorthouse
 * @author Sailesh Patel
 * @since 30/11/2017
 * @version 08/03/2018
 */
public class BoardRenderer {

	final float boardPadding = 60;
	final float boardBackgroundPadding = 30;
	final float piecePaddingPercent = 8;
	final float lineWidth = 5;

	private int boardSize;
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
	private boolean drawHighlight;

	private boolean[][] tutorialHighlights;

	private ProxyGameBoard board;
	private GameModel model;

	private float timer;

	Batch spriteBatch;
	ShapeRenderer shape;
	Texture whitePiece, blackPiece, emptyPiece, pieceHighlight;
	private Texture background;

	static Texture pieceSheet;
	static Texture[] animationFrames;

	/**
	 * The constructor for {@link com.othellog4.graphics.BoardRender BoardRender}.
	 * In this class, the majority of the work is initialising sprites and shapes for later use.
	 * @param model an instance of {@link com.othello.game.GameModel GameModel}
	 */
	public BoardRenderer(GameModel model) {
		this.spriteBatch = BaseScreen.SPRITE_BATCH;
		shape = BaseScreen.SHAPE_RENDER;
		this.model = model;

		/*
		 * A series of images needed
		 */
		background = new Texture("wood.jpeg");
		whitePiece = GraphicsUtil.createMipMappedTex("whitepiece.png");
		blackPiece = GraphicsUtil.createMipMappedTex("blackpiece.png");
		emptyPiece = GraphicsUtil.createMipMappedTex("emptypiece.png");
		pieceHighlight = GraphicsUtil.createMipMappedTex("piecehighlight.png");

		drawHighlight = true;

		this.board = (ProxyGameBoard) model.getBoard();

		pieceXPositions = new float[board.size()];
		pieceYPositions = new float[board.size()];

		tutorialHighlights = new boolean[board.size()][board.size()];

		boardWidth = Othello.GAME_WORLD_HEIGHT - (2 * boardPadding);
		boardSize = board.size();
		columnWidth = boardWidth / boardSize;
		piecePaddingActual = (columnWidth * piecePaddingPercent) / 100;
		pieceSizeActual = columnWidth - (2 * piecePaddingActual);

		startingPosX = (Othello.GAME_WORLD_WIDTH / 2) - (boardWidth / 2);
		startingPosY = (Othello.GAME_WORLD_HEIGHT / 2) + (boardWidth / 2);

		boardBackgroundWidth = Othello.GAME_WORLD_HEIGHT - (2 * boardBackgroundPadding);
		boardBackgroundX = (Othello.GAME_WORLD_WIDTH / 2) - (boardBackgroundWidth / 2);
		boardBackgroundY = (Othello.GAME_WORLD_HEIGHT / 2) - (boardBackgroundWidth / 2);

		timer = 0f;

		generatePieceCoordinates();


		pieceSheet = new Texture("animations/piece/sheet.png");
		TextureRegion[][] tempFrames = TextureRegion.split(pieceSheet, 256, 256);
		animationFrames = new Texture[30];

		int index = 0;
		for (int i = 0; i <= 6; i++) {
			for (int j = 0; j <= 5; j++) {
				animationFrames[index++] = tempFrames[i][j].getTexture();
			}
		}

	}

	// public void resize(int width, int height) {
	// BaseScreen.VIEWPORT.update(width, height);
	// cam.position.set(Othello.GAME_WORLD_WIDTH / 2, Othello.GAME_WORLD_HEIGHT
	// / 2, 0);
	// }

	/**
	 *
	 */
	public void update() {
		updatePosUnderMouse();
		// System.out.println(posUnderMouse);
	}

	/**
	 * Deals with rendering the game board, and it renders the board itself, colours, the game grid and
	 * @see com.othellog4.screens.GameScreen GameScreen
	 * @param delta
	 *            used to increment the
	 *            {@link com.othellog4.graphics.BoardRender#Timer Timer}
	 */
	public void render(float delta) {
		timer += delta;
		// cam.update();
		BaseScreen.VIEWPORT.apply();
		// spriteBatch.setProjectionMatrix(cam.combined);
		// shape.setProjectionMatrix(cam.combined);

		spriteBatch.begin();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		spriteBatch.end();

		shape.begin(ShapeType.Filled);

		// wooden background
		spriteBatch.begin();
		spriteBatch.draw(background, 0, 0, 1600, 900);
		spriteBatch.end();

		// Dark green background
		shape.setColor(0.01f, 0.2f, 0.022f, 1); // this line ensures that the
												// border is kept - do not
												// remove
		shape.rect(boardBackgroundX, boardBackgroundY, boardBackgroundWidth, boardBackgroundWidth);
		shape.setColor(0.02f, 0.4f, 0.043f, 1);

		// Light green inner
		shape.rect(startingPosX, startingPosY - boardWidth, boardWidth, boardWidth);
		shape.setColor(0.01f, 0.2f, 0.022f, 1);

		float startingY = startingPosY - boardWidth;

		// Draw board lines
		for (int x = 0; x <= boardSize; x++) {
			shape.rect((startingPosX + (x * columnWidth) - (lineWidth / 2)), startingY, lineWidth, boardWidth);
		}
		for (int y = 0; y <= boardSize; y++) {
			shape.rect(startingPosX, (startingY + (y * columnWidth) - (lineWidth / 2)), boardWidth, lineWidth);

		}
		shape.end();

		spriteBatch.begin();
		for (int x = 0; x < boardSize; x++) {
			for (int y = 0; y < boardSize; y++) {
				Texture actualPiece = null;
				Optional<Piece> optional = board.view(Position.at(x, y));

				if (optional.isPresent() == false) {
					// actualPiece = emptyPiece;
				} else {
					Piece piece = optional.get();
					switch (piece) {
					case PIECE_A:
						actualPiece = blackPiece;
						break;
					case PIECE_B:
						actualPiece = whitePiece;
						break;
					}

					spriteBatch.draw(actualPiece, pieceXPositions[x], pieceYPositions[y], pieceSizeActual,
							pieceSizeActual);
				}

				// Draw tutorial highlight
				// spriteBatch.setColor(1.0f, 1.0f, 1.0f, Math.abs(((float)
				// Math.sin(timer*3 +1))));
				if (tutorialHighlights[x][y]) {
					spriteBatch.draw(pieceHighlight, pieceXPositions[x], pieceYPositions[y], pieceSizeActual,
							pieceSizeActual);
				}
				// spriteBatch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
			}
		}

		Texture hoverTexture = null;

		switch (model.getCurrentPiece()) {
		case PIECE_A:
			hoverTexture = blackPiece;
			break;
		case PIECE_B:
			hoverTexture = whitePiece;
			break;
		}

		// Draw mouse highlight
		spriteBatch.setColor(1.0f, 1.0f, 1.0f, 0.5f);
		if (drawHighlight && posUnderMouse != null && !board.view(posUnderMouse).isPresent()) {
			spriteBatch.draw(hoverTexture, pieceXPositions[posUnderMouse.col], pieceYPositions[posUnderMouse.row],
					pieceSizeActual, pieceSizeActual);
		}
		spriteBatch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		spriteBatch.end();

	}

	/**
	 * Return the board position of the piece the mouse is currently hovering
	 * over
	 *
	 * @return Position
	 */
	public Position getPosUnderMouse() {
		return posUnderMouse;
	}

	/**
	 * Update the board position of the piece the mouse is currently hovering
	 * over Used internally for rendering piece highlights
	 */
	private void updatePosUnderMouse() {
		Vector2 mousePos = GraphicsUtil.getMousePos();
		// System.out.println(mousePos);
		Integer xPos = null;
		Integer yPos = null;

		// Find if within x bounds of piece
		for (int x = 0; x < pieceXPositions.length; x++) {
			if (mousePos.x >= pieceXPositions[x] && mousePos.x <= pieceXPositions[x] + pieceSizeActual) {
				xPos = x;
				break;
			}
		}
		if (xPos == null) {
			posUnderMouse = null;
			return;
		}

		// Find if within y bounds of piece
		for (int y = 0; y < pieceYPositions.length; y++) {
			if (mousePos.y >= pieceYPositions[y] && mousePos.y <= pieceYPositions[y] + pieceSizeActual) {
				yPos = y;
				break;
			}
		}
		if (yPos == null) {
			posUnderMouse = null;
			return;
		} else {
			posUnderMouse = Position.at(xPos, yPos);
			return;
		}
	}

	/**
	 * Set whether a highlight should be drawn under the mouse cursor
	 *
	 * @param bool
	 *            Draw highlight
	 */
	public void setDrawHighlight(boolean bool) {
		drawHighlight = bool;
	}

	/**
	 * Update piece position coordinate arrays
	 */
	private void generatePieceCoordinates() {
		for (int x = 0; x < pieceXPositions.length; x++) {
			pieceXPositions[x] = startingPosX + piecePaddingActual + (columnWidth * x);
		}
		for (int y = 0; y < pieceYPositions.length; y++) {
			pieceYPositions[y] = startingPosY - piecePaddingActual - (columnWidth * y) - pieceSizeActual;
		}
	}

	public void addPieceHighlight(Position pos) {
		tutorialHighlights[pos.col][pos.row] = true;
	}

	public void resetAllPieceHighlights() {
		for (int x = 0; x < tutorialHighlights.length; x++) {
			for (int y = 0; y < tutorialHighlights[0].length; y++) {
				tutorialHighlights[x][y] = false;
			}
		}
	}

	private class VisualBoard {
		private VisualPiece[][] vBoard;

		private VisualBoard() {
			vBoard = new VisualPiece[boardSize][boardSize];
			update();
		}

		public void update() {
			for (int x = 0; x < boardSize; x++) {
				for (int y = 0; y < boardSize; y++) {
					Optional<Piece> optional = board.view(Position.at(x, y));
					if (optional.isPresent() == true) {
						if(vBoard[x][y] != null) { // Piece exists
							vBoard[x][y].update(optional.get());
						} else { // Piece newly placed
							vBoard[x][y] = new VisualPiece(optional.get());
						}
					} else if(vBoard[x][y] != null) { // Piece removed
						vBoard[x][y] = null;
					}
				}
			}
		}

		public Texture getTexture(int x, int y) {
			if(vBoard[x][y] == null) {
				return null;
			} else {
				return vBoard[x][y].getTexture();
			}
		}

		/**
		 * Inner class
		 */
		private class VisualPiece {
			int state;

			public VisualPiece(Piece piece) {
				state = getState(piece);
			}

			private void update(Piece piece) {
				if(getState(piece) > state){
					state--;
				}

				if(getState(piece) < state){
					state++;
				}
			}

			private Texture getTexture() {
				return animationFrames[state];
			}

			private int getState(Piece piece) {
				switch (piece) {
				case PIECE_A:
					return animationFrames.length - 1;
				case PIECE_B:
					return 0;

				}
				return 0;
			}
		}
	}

	/**
	 * Dispose of textures when application closes
	 */
	@Override
	public void finalize() {
		whitePiece.dispose();
		blackPiece.dispose();
		emptyPiece.dispose();
		pieceHighlight.dispose();
	}
}