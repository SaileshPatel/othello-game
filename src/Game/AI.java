package Game;

/**
 * This is the AI class. It will eventually have some AI implementation in order to make smart decisions
 * @author Sailesh Patel
 * @author Zak Hirsi
 * @since 23/10/2017
 *
 */
public class AI implements Participant{
	
	private GameViewControl gvc; 
	
	/**
	 * The constructor for the AI class. 
	 * @param gvc - the GameViewController which controls the game from the perspective of the AI 
	 */
	public AI(GameViewControl gvc){
		this.gvc = gvc;
	}
}
