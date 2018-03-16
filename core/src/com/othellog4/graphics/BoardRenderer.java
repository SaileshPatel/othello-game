package com.othellog4.graphics;

import java.util.Optional;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.othellog4.Othello;
import com.othellog4.game.GameModel;
import com.othellog4.game.board.FlipEvent;
import com.othellog4.game.board.Piece;
import com.othellog4.game.board.Position;
import com.othellog4.game.board.ProxyGameBoard;
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
public class BoardRenderer implements Disposable {

	final float boardPadding = 60;
	final float boardBackgroundPadding = 30;
	final float piecePaddingPercent = 8;
	final float pieceOverlapPercent = 27; // Overlap for shadows and piece flipping animation
	final float lineWidth = 5;

	private int boardSize;
	private float boardWidth;
	private float piecePaddingActual, pieceSizeActual, pieceOverlapActual;
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
	private boolean drawGhost;

	private boolean[][] tutorialHighlights;

	private ProxyGameBoard board;
	private GameModel model;

	private Texture pieceHighlight;
	private Texture background;

	private Texture tempFelt;
	private TextureRegion felt;

	static Texture pieceSheet;
	static TextureRegion[] animationFrames;

	private VisualBoard visualBoard;

	/**
	 * The constructor for {@link com.othellog4.graphics.BoardRender BoardRender}.
	 * In this class, the majority of the work is initialising sprites and BaseScreen.SHAPE_RENDERs for later use.
	 * @param model an instance of {@link com.othello.game.GameModel GameModel}
	 */
	public BoardRenderer(GameModel model) {
		//this.BaseScreen.SPRITE_BATCH = BaseScreen.SPRITE_BATCH;
		//BaseScreen.SHAPE_RENDER = BaseScreen.BaseScreen.SHAPE_RENDER_RENDER;
		this.model = model;

		/*
		 * A series of images needed
		 */
		background = GraphicsUtil.createMipMappedTex("wood.jpg");
		pieceHighlight = GraphicsUtil.createMipMappedTex("piecehighlight.png");

		tempFelt = GraphicsUtil.createMipMappedTex("felt.png");
		tempFelt.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
		felt = new TextureRegion(tempFelt);
		felt.setRegion(0,0,tempFelt.getWidth()*4,tempFelt.getHeight()*4);


		drawGhost = true;

		this.board = (ProxyGameBoard) model.getBoard();

		pieceXPositions = new float[board.size()];
		pieceYPositions = new float[board.size()];

		tutorialHighlights = new boolean[board.size()][board.size()];

		boardWidth = Othello.GAME_WORLD_HEIGHT - (2 * boardPadding);
		boardSize = board.size();
		columnWidth = boardWidth / boardSize;
		piecePaddingActual = (columnWidth * piecePaddingPercent) / 100;
		pieceOverlapActual = (columnWidth * pieceOverlapPercent) / 100;
		pieceSizeActual = (columnWidth - (2 * piecePaddingActual)) + pieceOverlapActual * 2;


		startingPosX = (Othello.GAME_WORLD_WIDTH / 2) - (boardWidth / 2);
		startingPosY = (Othello.GAME_WORLD_HEIGHT / 2) + (boardWidth / 2);

		boardBackgroundWidth = Othello.GAME_WORLD_HEIGHT - (2 * boardBackgroundPadding);
		boardBackgroundX = (Othello.GAME_WORLD_WIDTH / 2) - (boardBackgroundWidth / 2);
		boardBackgroundY = (Othello.GAME_WORLD_HEIGHT / 2) - (boardBackgroundWidth / 2);

		generatePieceCoordinates();


		pieceSheet = GraphicsUtil.createMipMappedTex("animations/piece/sheet.png");
		TextureRegion[][] tempFrames = TextureRegion.split(pieceSheet, 256, 256);
		animationFrames = new TextureRegion[30];

		int index = 0;
		for (int row = 0; row < 5; row++) {
			for (int col = 0; col < 6; col++) {
				//System.out.println("Attempting to access: " + x + ", " + y);
				animationFrames[index++] = tempFrames[row][col];
			}
		}

		visualBoard = new VisualBoard();

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
		visualBoard.update();
		// System.out.println(posUnderMouse);
		model.enableInput(doneAnimating());
		//System.out.println(doneAnimating());
	}

	/**
	 * Deals with rendering the game board, and it renders the board itself, colours, the game grid and
	 * @see com.othellog4.screens.GameScreen GameScreen
	 * @param delta time since last frame
	 */
	public void render(float delta) {
		// cam.update();
		BaseScreen.VIEWPORT.apply();
		// BaseScreen.SPRITE_BATCH.setProjectionMatrix(cam.combined);
		// BaseScreen.SHAPE_RENDER.setProjectionMatrix(cam.combined);

		BaseScreen.SPRITE_BATCH.begin();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		BaseScreen.SPRITE_BATCH.end();


		// wooden background
		BaseScreen.SPRITE_BATCH.begin();
		BaseScreen.SPRITE_BATCH.draw(background, 0, 0, Othello.GAME_WORLD_WIDTH,
				Othello.GAME_WORLD_HEIGHT);
		BaseScreen.SPRITE_BATCH.end();


		BaseScreen.SHAPE_RENDER.begin(ShapeType.Filled);
		// Dark green background
		BaseScreen.SHAPE_RENDER.setColor(0.01f, 0.2f, 0.022f, 1); // this line ensures that the
												// border is kept - do not
												// remove
		BaseScreen.SHAPE_RENDER.rect(boardBackgroundX, boardBackgroundY, boardBackgroundWidth, boardBackgroundWidth);
		BaseScreen.SHAPE_RENDER.setColor(0.02f, 0.4f, 0.043f, 1);
		BaseScreen.SHAPE_RENDER.end();
		// Light green inner
		BaseScreen.SPRITE_BATCH.begin();
		BaseScreen.SPRITE_BATCH.draw(felt, startingPosX, startingPosY - boardWidth, boardWidth, boardWidth);
		BaseScreen.SPRITE_BATCH.end();

		BaseScreen.SHAPE_RENDER.begin(ShapeType.Filled);
		//BaseScreen.SHAPE_RENDER.rect(startingPosX, startingPosY - boardWidth, boardWidth, boardWidth);
		BaseScreen.SHAPE_RENDER.setColor(0.01f, 0.2f, 0.022f, 1);

		float startingY = startingPosY - boardWidth;

		// Draw board lines
		for (int x = 0; x <= boardSize; x++) {
			BaseScreen.SHAPE_RENDER.rect((startingPosX + (x * columnWidth) - (lineWidth / 2)), startingY, lineWidth, boardWidth);
		}
		for (int y = 0; y <= boardSize; y++) {
			BaseScreen.SHAPE_RENDER.rect(startingPosX, (startingY + (y * columnWidth) - (lineWidth / 2)), boardWidth, lineWidth);

		}
		BaseScreen.SHAPE_RENDER.end();

		BaseScreen.SPRITE_BATCH.begin();
		for (int x = 0; x < boardSize; x++) {
			for (int y = 0; y < boardSize; y++) {
				// Draw tutorial highlight
				if (tutorialHighlights[x][y] && (posUnderMouse == null ||
						!(drawGhost && posUnderMouse.col == x && posUnderMouse.row == y))) {
					BaseScreen.SPRITE_BATCH.draw(pieceHighlight, pieceXPositions[x], pieceYPositions[y], pieceSizeActual,
							pieceSizeActual);
				}

				if(visualBoard.getTexture(x, y) != null)
				BaseScreen.SPRITE_BATCH.draw(visualBoard.getTexture(x, y), pieceXPositions[x], pieceYPositions[y], pieceSizeActual,
						pieceSizeActual);
				//}
				// BaseScreen.SPRITE_BATCH.setColor(1.0f, 1.0f, 1.0f, 1.0f);
			}
		}

		TextureRegion hoverTexture = null;

		switch (model.getCurrentPiece()) {
		case PIECE_A:
			hoverTexture = animationFrames[animationFrames.length-1];
			break;
		case PIECE_B:
			hoverTexture = animationFrames[0];
			break;
		}

		// Draw piece ghost
		BaseScreen.SPRITE_BATCH.setColor(1.0f, 1.0f, 1.0f, 0.5f);
		if (drawGhost && posUnderMouse != null && !board.view(posUnderMouse).isPresent()) {
			BaseScreen.SPRITE_BATCH.draw(hoverTexture, pieceXPositions[posUnderMouse.col], pieceYPositions[posUnderMouse.row],
					pieceSizeActual, pieceSizeActual);
		}
		BaseScreen.SPRITE_BATCH.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		BaseScreen.SPRITE_BATCH.end();

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
		drawGhost = bool;
	}

	public boolean doneAnimating() {
		return visualBoard.doneAnimating();
	}

	/**
	 * Update piece position coordinate arrays
	 */
	private void generatePieceCoordinates() {
		for (int x = 0; x < pieceXPositions.length; x++) {
			pieceXPositions[x] = startingPosX + piecePaddingActual - pieceOverlapActual + (columnWidth * x);
		}
		for (int y = 0; y < pieceYPositions.length; y++) {
			pieceYPositions[y] = startingPosY - piecePaddingActual + pieceOverlapActual - (columnWidth * y) - pieceSizeActual;
		}
	}

	/**
	 * Adds an orange highlight in the tutorial for valid moves.
	 * <br>
	 * Primarily used in the {@link com.othellog4.screens.TutorialScreen Tutorial Screen}
	 */
	public void addPieceHighlight(Position pos) {
		tutorialHighlights[pos.col][pos.row] = true;
	}

	/**
	 * Aims to reset all of the piece highlights
	 */
	public void resetAllPieceHighlights() {
		for (int x = 0; x < tutorialHighlights.length; x++) {
			for (int y = 0; y < tutorialHighlights[0].length; y++) {
				tutorialHighlights[x][y] = false;
			}
		}
	}

	private class VisualBoard {
		private VisualPiece[][] vBoard;
		private boolean newPiece = false;
		private long animFinishTime = 0;

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
							newPiece = true;
						}
					} else if(vBoard[x][y] != null) { // Piece removed
						vBoard[x][y] = null;
					}
				}
			}

			if(newPiece) {
				Set<FlipEvent[]> flipped = board.flips();
				int curDelay = 0;
				int delay = 10;
				for(FlipEvent[] eventArray: flipped) {
					for(FlipEvent event: eventArray) {
						//event.at.col;
						//event.at.row;
						vBoard[event.at.col][event.at.row].setDelay(curDelay);
						curDelay += delay;
					}
				}
				                                              // Delay time            // Flipping time
				animFinishTime = System.currentTimeMillis() + ((curDelay/60) * 1000) + 500;
				newPiece = false;
			}
		}

		public TextureRegion getTexture(int x, int y) {
			if(vBoard[x][y] == null) {
				return null;
			} else {
				return vBoard[x][y].getTexture();
			}
		}

		public boolean doneAnimating() {
			return System.currentTimeMillis() > animFinishTime;
		}

		/**
		 * Inner class
		 */
		private class VisualPiece {
			int state;
			int delay;

			public VisualPiece(Piece piece) {
				state = getState(piece);
				delay = 0;
			}

			public void update(Piece piece) {
				if(delay > 0) {
					delay--;
					} else {
					if(getState(piece) > state){
						state++;
					}

					if(getState(piece) < state){
						state--;
					}
				}
			}

			public void setDelay(int delay) {
				this.delay = delay;
			}

			private TextureRegion getTexture() {
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
	 * Dispose of textures
	 */
	@Override
	public void dispose() {
		System.out.println("Disposing board renderer");
		pieceSheet.dispose();
		tempFelt.dispose();
		pieceHighlight.dispose();
		background.dispose();
	}
}