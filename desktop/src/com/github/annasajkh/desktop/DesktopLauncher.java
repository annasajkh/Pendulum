package com.github.annasajkh.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.github.annasajkh.Game;

public class DesktopLauncher
{
	public static void main(String[] args)
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 600;
		config.height = 600;
		config.title = "Double Pendulum";
		new LwjglApplication(new Game(), config);
	}
}
