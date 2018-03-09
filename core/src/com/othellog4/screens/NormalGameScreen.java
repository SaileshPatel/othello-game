package com.othellog4.screens;

import com.othellog4.Othello;
import com.othellog4.environment.Launcher;
import com.othellog4.game.GameModel;
import com.othellog4.game.board.Position;

/**
 * Represents the average game screen.
 * @author 	159014260 John Berg
 * @since 	07/12/2017
 * @version 07/12/2017
 */
public final class NormalGameScreen extends GameScreen
{

	boolean gameOver = false;

	/**
	 * 
	 * @param model takes a {@link com.othellog4.game.GameModel Model} of the game
	 * @param game takes an instance of {@link com.othellog4.Othello Othello}
	 */
	public NormalGameScreen(
			final GameModel model,
			final Othello game)
	{
		super(model, game);
		super.setPlacementEnabled(true);
	}
	@Override
	protected boolean checkInput(Position position)
	{
		return true;
	}


	@Override
	protected void postRender(float delta) {
		// TODO Auto-generated method stub

	}
	@Override
	protected void postUpdate(float delta) {
		setPlacementEnabled(model.isWaiting());
		if(!gameOver && model.isGameOver()) {
			Launcher.get().clear();
			game.setScreen(new EndGameScreen(game, this, model.score()));
			gameOver = true;
		}
	}

}
