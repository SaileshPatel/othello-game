package com.othellog4.screens;

import java.util.Observer;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.othellog4.Othello;
import com.othellog4.graphics.BoardRenderer;
import com.othellog4.game.GameSession;
import com.othellog4.game.board.GameBoard;
import com.othellog4.game.player.Participant;
import com.othellog4.game.player.Player;

public class GameScreen extends ScreenAdapter implements Observer {
	Othello game;
	SpriteBatch spriteBatch;
	BoardRenderer boardRenderer;
	GameSession session;
	ParticipantProxy[] participants;
	ParticipantProxy[] localParticipants;
	PlayerKey[] keys;
	
	
	public GameScreen(GameSession session, ParticipantKey[] keys) {
		this.session = session;
		this.participants = session.getParticipants(); 
		
		localParticipants = new ParticpantProxy[keys.length];
		
		for(int i=0; i<localParticipants.length; i++) {
			localParticipants[i] = keys[i].getPlayer();
		}
		
		spriteBatch = game.getSpriteBatch();
		
		//Board created in main menu
		boardRenderer = new BoardRenderer(spriteBatch, board);
	}
	
	public void updatedGameSession(GameSession gamesession) {
		this.gamesession = gamesession;
	}
	
	public void update() {
		
	}
	
	public void draw() {
		
	}
}
