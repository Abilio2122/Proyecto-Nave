package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class PowerUp {
	protected int x;
	protected int y;
    protected int xSpeed;
    protected int ySpeed;
    protected Sprite spr;
	
    public void processPowerUp(NaveJugador nave, Texture tx) {
    	instanciación(x, x, x, x, x, tx);
        apply(nave);
        update();
        // Otros pasos comunes para todos los power-ups
    }

    public void instanciación(int x, int y, int size, int xSpeed, int ySpeed, Texture tx) {
    	spr = new Sprite(tx);
    	this.x = x; 
        
    	if (x-size < 0) this.x = x+size;
    	if (x+size > Gdx.graphics.getWidth())this.x = x-size;
         
        this.y = y;
        
    	if (y-size < 0) this.y = y+size;
    	if (y+size > Gdx.graphics.getHeight())this.y = y-size;
    	
        spr.setPosition(x, y);
        this.setXSpeed(xSpeed);
        this.setySpeed(ySpeed);
    }

	public abstract void apply(NaveJugador nave);

    public abstract int getySpeed();

    public abstract int getXSpeed();

    public abstract void setXSpeed(int xSpeed);

    public abstract void setySpeed(int ySpeed);

    public abstract void draw(SpriteBatch batch);

    public abstract void update();

    public abstract void checkCollision(PowerUp b2);

    public abstract Rectangle getArea();
}
