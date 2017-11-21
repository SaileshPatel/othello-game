package othello.environment.sound;

/**
 * 
 * @author 	John
 * @since 	20/11/2017
 * @version 21/11/2017
 */
public class SoundManager
{
	private static SoundManager instance;
	private SoundManager()
	{
		
	}
	public static final SoundManager get()
	{
		return instance != null? instance: (instance = new SoundManager());
	}
}
