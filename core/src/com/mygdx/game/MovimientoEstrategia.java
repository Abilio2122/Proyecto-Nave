package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface MovimientoEstrategia {

	void procesarEntrada(float x, float y, float xVel, float yVel, Sprite spr);
}
