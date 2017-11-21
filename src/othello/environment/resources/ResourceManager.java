package othello.environment.resources;

/**
 * 
 * @author 	John
 * @since 	21/11/2017
 * @version 21/11/2017
 */
public class ResourceManager
{
	private static ResourceManager instance;
	private ResourceManager()
	{
		
	}
	public static final ResourceManager get()
	{
		return instance != null? instance: (instance = new ResourceManager());
	}
}
