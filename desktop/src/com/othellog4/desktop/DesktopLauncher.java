package com.othellog4.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.othellog4.Othello;
/**
 * Initialises the game and renders everything on screen. 
 * <br>
 * Also sets the desktop icons and deals with the default screen height/width
 * 
 * @author James Shorthouse
 * @since 27/11/2017
 *
 */
public class DesktopLauncher {
	/**
	 * The main class allows the game to be launched and run properly. 
	 * <br>
	 * It also sets the desktop icon as well as the defaults for a LibGDX application
	 * @param arg
	 */
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		System.setProperty(
				"org.lwjgl.opengl.Display.allowSoftwareOpenGL",
				"true");
		config.height = 720;
		config.width = 1280;
		config.addIcon("icon/icon32.png", Files.FileType.Internal);
		config.addIcon("icon/icon128.png", Files.FileType.Internal);
		new LwjglApplication(new Othello(), config);
	}
}
