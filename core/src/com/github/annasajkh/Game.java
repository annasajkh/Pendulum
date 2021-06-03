package com.github.annasajkh;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class Game extends ApplicationAdapter
{
	
	ShapeRenderer shapeRenderer;
	
	Pendulum pendulumA;
	Pendulum pendulumB;
	Pendulum pendulumC;
	
	float r1;
	float r2;
	float r3;

	public static float brushRadius = 5;
	float maxSpeed;
	int time = 0;
	
	List<Brush> brushes;
	boolean remove = false;
	
	public static Color hsvToRgba(float hue, float saturation, float value, float alpha)
    	{
		int h = (int) (hue * 6);
		float f = hue * 6 - h;
		float p = value * (1 - saturation);
		float q = value * (1 - f * saturation);
		float t = value * (1 - (1 - f) * saturation);

		switch (h)
		{
		    case 0:
			return new Color(value, t, p, alpha);
		    case 1:
			return new Color(q, value, p, alpha);
		    case 2:
			return new Color(p, value, t, alpha);
		    case 3:
			return new Color(p, q, value, alpha);
		    case 4:
			return new Color(t, p, value, alpha);
		    case 5:
			return new Color(value, p, q, alpha);
		    default:
			throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " +
						   hue +
						   ", " +
						   saturation +
						   ", " +
						   value);
		}
	}
	
	
	@Override
	public void create()
	{
		maxSpeed = MathUtils.PI2 * 0.01f;
		shapeRenderer = new ShapeRenderer();
		
		pendulumA = new Pendulum(Gdx.graphics.getWidth() / 2,Gdx.graphics.getHeight() / 2,MathUtils.HALF_PI,100);
		pendulumB = new Pendulum(pendulumA.x1,pendulumA.y1,MathUtils.HALF_PI,100);
		pendulumC = new Pendulum(pendulumB.x1,pendulumB.y1,MathUtils.HALF_PI,100);
		
		pendulumA.connect(pendulumB);
		pendulumB.connect(pendulumC);
		
		Timer.schedule(new Task()
		{
			
			@Override
			public void run()
			{
				remove = true;
			}
		},1);
		
		brushes = new ArrayList<>();
	}
	
	
	public void update()
	{
		if(remove)
		{
			brushes.remove(0);
		}
		
		brushes.add(new Brush(pendulumC.x2,pendulumC.y2,hsvToRgba(MathUtils.map(-1,1,0,0.9999f,MathUtils.sin(time * 0.01f)),1,1,1)));
		
		r1 += MathUtils.PI2 * MathUtils.random(-0.0005f, 0.0005f);
		r2 += MathUtils.PI2 * MathUtils.random(-0.0005f, 0.0005f);
		r3 += MathUtils.PI2 * MathUtils.random(-0.0005f, 0.0005f);
		
		r1 = MathUtils.clamp(r1,-maxSpeed,maxSpeed);
		r2 = MathUtils.clamp(r2,-maxSpeed,maxSpeed);
		r3 = MathUtils.clamp(r3,-maxSpeed,maxSpeed);
		
		pendulumA.rotationRad += r1;
		pendulumB.rotationRad += r2;
		pendulumC.rotationRad += r3;
		
		
		pendulumA.update();
		pendulumB.update();
		pendulumC.update();
	}

	@Override
	public void render()
	{
		update();
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		shapeRenderer.begin(ShapeType.Filled);
		
		for(Brush brush : brushes)
		{
			brush.draw(shapeRenderer);
		}
		
		shapeRenderer.setColor(Color.WHITE);
		
		pendulumA.draw(shapeRenderer);
		pendulumB.draw(shapeRenderer);
		pendulumC.draw(shapeRenderer);
		
		shapeRenderer.end();

		time++;
	}

	@Override
	public void dispose()
	{
		shapeRenderer.dispose();
	}
}
