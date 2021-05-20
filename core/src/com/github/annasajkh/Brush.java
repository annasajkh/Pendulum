package com.github.annasajkh;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Brush
{
	
	float x, y;
	Color color;

	public Brush(float x, float y, Color color)
	{
		this.x = x;
		this.y = y;
		this.color = color;
	}
	
	public void draw(ShapeRenderer shapeRenderer)
	{
		shapeRenderer.setColor(color);
		shapeRenderer.circle(x, y, Game.brushRadius);
	}
	
}
