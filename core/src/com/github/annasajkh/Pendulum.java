package com.github.annasajkh;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class Pendulum
{
	float x1,y1;
	float x2, y2;
	float size;
	float rotationRad;
	Pendulum otherPendulum;
	
	public Pendulum(float x1, float y1, float rotationRad, float size)
	{
		this.x1 = x1;
		this.y1 = y1;
		this.rotationRad = rotationRad;
		this.size = size;
	}
	
	public void connect(Pendulum other)
	{
		otherPendulum = other;
	}
	

	public void update()
	{

		
		this.x2 = x1 - MathUtils.cos(rotationRad) * size;
		this.y2 = y1 - MathUtils.sin(rotationRad) * size;
		
		if(otherPendulum != null)
		{			
			otherPendulum.x1 = x2;
			otherPendulum.y1 = y2;
		}
	}
	
	public void draw(ShapeRenderer shapeRenderer)
	{
		shapeRenderer.rectLine(x1, y1, x2, y2, 10);
	}
}
