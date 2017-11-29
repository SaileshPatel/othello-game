package com.othellog4.environment.context;

/**
 * 
 * @author	John
 * @since 	20/11/2017
 * @version 21/11/2017
 */
public final class ContextManager
{
	private static ContextManager instance;
	private ContextManager()
	{
		
	}
	public final void push()
	{
		
	}
	public final GameContext peek()
	{
		return null;
	}
	public final void pop()
	{
		
	}
	public static final ContextManager get()
	{
		return instance != null? instance: (instance = new ContextManager());
	}
}
