package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class MovimientoMantenido implements MovimientoEstrategia{
	
	public MovimientoMantenido() {
    }
	
	public void procesarEntrada(float x, float y, float xVel, float yVel, Sprite spr) {
	    // Incrementa o disminuye la velocidad según las teclas mantenidas presionadas
	    if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
	        xVel -=15;
	    }
	    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
	        xVel +=15;
	    }
	    if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
	        yVel -=15;
	    }
	    if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
	        yVel +=15;
	    }

	    // Realiza el rebote en los límites de la pantalla
	    if (x + xVel < 0 || x + xVel + spr.getWidth() > Gdx.graphics.getWidth()) {
	        xVel *= -1;
	    }
	    if (y + yVel < 0 || y + yVel + spr.getHeight() > Gdx.graphics.getHeight()) {
	        yVel *= -1;
	    }

	    // Actualiza la posición de la nave
	    spr.setPosition(x + xVel, y + yVel);
	}
}
