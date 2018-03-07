package com.othellog4.graphics;

import java.util.function.Consumer;

/**
 * The {@code ScreenBoxField} class represents a box shape which has both a
 * position and dimensions, and provide a declarative api for detecting if a
 * {@code ScreenBoxField} is under a cursor.
 * 
 * @author 	159014260 John Berg
 * @since	06/03/2018
 * @version 06/03/2018
 */
public class ScreenBoxField
{
	//=========================================================================
	//Fields.
	/**
	 * The x position of the {@code ScreenBoxField} object.
	 */
	private int x;
	/**
	 * The y position of the {@code ScreenBoxField} object.
	 */
	private int y;
	/**
	 * The width of the {@code ScreenBoxField} object.
	 */
	private int width;
	/**
	 * The height of the {@code ScreenBoxField} object.
	 */
	private int height;
	/**
	 * The function object to execute before the hover check.
	 */
	private Consumer<ScreenBoxField> before;
	/**
	 * The function object to execute after the hover check.
	 */
	private Consumer<ScreenBoxField> after;
	/**
	 * The function object to execute if the hover check determines that
	 * <code>this</code> {@code ScreenBoxField} is hovered over.
	 */
	private Consumer<ScreenBoxField> onHover;
	/**
	 * The function object to execute if <code>this</code>
	 * {@code ScreenBoxField} is not being hovered over.
	 */
	private Consumer<ScreenBoxField> noHover;
	//=========================================================================
	//Constructors.
	/**
	 * Create a {@code ScreenBoxField} object of a specified width and height,
	 * and at a specified x and y position.
	 * 
	 * @param x The x position of the {@code ScreenBoxField}.
	 * @param y The y position of the {@code ScreenBoxField}.
	 * @param width The width of the {@code ScreenBoxField}.
	 * @param height The height of the of the {@code ScreenBoxField}.
	 */
	public ScreenBoxField(
			final int x,
			final int y,
			final int width,
			final int height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
	}
	//=========================================================================
	//Methods.
	/**
	 * Get the x position of <code>this</code> {@code ScreenBoxField} object.
	 * 
	 * @return The x position of <code>this</code> {@code ScreenBoxField}
	 * 			object.
	 */
	public final int getX()
	{
		return x;
	}
	/**
	 * Get the y position of <code>this</code> {@code ScreenBoxField} object.
	 * 
	 * @return the y position of <code>this</code> {@code ScreenBoxField}
	 * 			object.
	 */
	public final int getY()
	{
		return y;
	}
	/**
	 * Get the width of <code>this</code> {@code ScreenBoxField} object.
	 * 
	 * @return The width position of <code>this</code> {@code ScreenBoxField}
	 * 			object.
	 */
	public final int getWidth()
	{
		return width;
	}
	/**
	 * Get the height of <code>this</code> {@code ScreenBoxField} object.
	 * 
	 * @return The height of <code>this</code> {@code ScreenBoxField} object.
	 */
	public final int getHeight()
	{
		return height;
	}
	/**
	 * Set the x position of <code>this</code> {@code ScreenBoxField} object.
	 * 
	 * @param x The new x position.
	 */
	public final void setX(final int x)
	{
		this.x = x;
	}
	/**
	 * Set the y position of <code>this</code> {@code ScreenBoxField} object.
	 * 
	 * @param y The new y position.
	 */
	public final void setY(final int y)
	{
		this.y = y;
	}
	/**
	 * Set the width of <code>this</code> {@code ScreenBoxWidth} object.
	 * 
	 * @param width The new width.
	 */
	public final void setWidth(final int width)
	{
		this.width = width;
	}
	/**
	 * Set the height of <code>this</code> {@code ScreenBoxWidth} object.
	 * 
	 * @param height The new height.
	 */
	public final void setHeight(final int height)
	{
		this.height = height;
	}
	/**
	 * Check if <code>this</code> {@code ScreenBoxField} is being hovered
	 * over.
	 * 
	 * @param xPos The current x position.
	 * @param yPos The current y position.
	 */
	public final void hover(float xPos, float yPos)
	{
		if(before != null)
			before.accept(this);
		if(onHover != null
				&& x <= xPos && xPos <= x + width
				&&  y <= yPos && yPos <= y + height)
			onHover.accept(this);
		else if(noHover != null)
			noHover.accept(this);
		if(after != null)
			after.accept(this);
	}
	/**
	 * Define the function object to execute before each hover check.
	 * 
	 * @param callback The function object to execute before the hover check.
	 * @return <code>this</code> {@code ScreenBoxField} after the operation.
	 */
	public final ScreenBoxField before(final Consumer<ScreenBoxField> callback)
	{
		before = callback;
		return this;
	}
	/**
	 * Define the function object to execute after each hover check.
	 * 
	 * @param callback The function object to execute after the hover check.
	 * @return <code>this</code> {@code ScreenBoxField} after the operation.
	 */
	public final ScreenBoxField after(final Consumer<ScreenBoxField> callback)
	{
		after = callback;
		return this;
	}
	/**
	 * Define the function object to execute when being hovered over.
	 * 
	 * @param callback The function object to execute during hovering.
	 * @return <code>this</code> {@code ScreenBoxField} after the operation.
	 */
	public final ScreenBoxField onHover(
			final Consumer<ScreenBoxField> callback)
	{
		onHover = callback;
		return this;
	}
	/**
	 * Define the function object to execute when not being hovered over.
	 * 
	 * @param callback The function object to execute there is no hovering.
	 * @return <code>this</code> {@code ScreenBoxField} after the operation.
	 */
	public final ScreenBoxField noHover(
			final Consumer<ScreenBoxField> callback)
	{
		noHover = callback;
		return this;
	}
}
