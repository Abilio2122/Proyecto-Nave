package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public interface PowerUp {
	void apply(NaveJugador nave);
	
	int getySpeed();
	
	int getXSpeed();
	
	void setXSpeed(int xSpeed);
	
	void setySpeed(int ySpeed);
	
	void draw(SpriteBatch batch,PantallaJuego juego);
	
	void update();
	
	void checkCollision(Cohete b2);
	
	Rectangle getArea();
}