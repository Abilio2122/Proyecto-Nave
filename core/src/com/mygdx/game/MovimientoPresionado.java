package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MovimientoPresionado implements MovimientoEstrategia {
	// Constructor
    public MovimientoPresionado() {
    }
	
	@Override
	public void procesarEntrada(float x, float y, float xVel, float yVel, Sprite spr) {
		// TODO Auto-generated method stub
	
		if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            xVel-=50;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            xVel+=50;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            yVel-=50;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            yVel+=50;
        }

        if (x + xVel < 0 || x + xVel + spr.getWidth() > Gdx.graphics.getWidth())
            xVel *= -1;
        if (y + yVel < 0 || y + yVel + spr.getHeight() > Gdx.graphics.getHeight())
            yVel *= -1;

        spr.setPosition(x + xVel, y + yVel);
        
	}
		
}
