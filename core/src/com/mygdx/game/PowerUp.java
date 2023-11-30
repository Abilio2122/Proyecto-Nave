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
	
    public PowerUp(int x, int y, int size, int xSpeed, int ySpeed, Texture tx) {
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;

        spr = new Sprite(tx);
        spr.setPosition(x, y);

        // Asegurar que las coordenadas estén dentro de los límites
        if (x - size < 0) this.x = x + size;
        if (x + size > Gdx.graphics.getWidth()) this.x = x - size;
        if (y - size < 0) this.y = y + size;
        if (y + size > Gdx.graphics.getHeight()) this.y = y - size;
    }
    
    //Plantilla
    public final void processPowerUp(NaveJugador nave, Texture tx, SpriteBatch batch, PowerUp b2) {
        apply(nave);
        draw(batch);
        checkCollision(b2);
        update();
    }
    
    public abstract void apply(NaveJugador nave);
    
    public void draw(SpriteBatch batch) {
    	spr.draw(batch);
    	
    }
    
    public void checkCollision(PowerUp b2) {
        if(spr.getBoundingRectangle().overlaps(b2.spr.getBoundingRectangle())){
        	// rebote
            if (getXSpeed() ==0) setXSpeed(getXSpeed() + b2.getXSpeed()/2);
            if (b2.getXSpeed() ==0) b2.setXSpeed(b2.getXSpeed() + getXSpeed()/2);
        	setXSpeed(- getXSpeed());
            b2.setXSpeed(-b2.getXSpeed());
            
            if (getySpeed() ==0) setySpeed(getySpeed() + b2.getySpeed()/2);
            if (b2.getySpeed() ==0) b2.setySpeed(b2.getySpeed() + getySpeed()/2);
            setySpeed(- getySpeed());
            b2.setySpeed(- b2.getySpeed()); 
        }
    }
    
    
    public void update() {
		x += getXSpeed();
        y += getySpeed();

        if (x+getXSpeed() < 0 || x+getXSpeed()+spr.getWidth() > Gdx.graphics.getWidth())
        	setXSpeed(getXSpeed() * -1);
        if (y+getySpeed() < 0 || y+getySpeed()+spr.getHeight() > Gdx.graphics.getHeight())
        	setySpeed(getySpeed() * -1);
        spr.setPosition(x, y);
	}
    

	 public int getySpeed() {
		return ySpeed;
	 }
	
	 public int getXSpeed() {
		return xSpeed;
	 }
		
	 public void setXSpeed(int xSpeed) {
		this.xSpeed = xSpeed;
	 }
		
	 public void setySpeed(int ySpeed) {
		this.ySpeed = ySpeed;
	 }
	
	 public Rectangle getArea() {
		return spr.getBoundingRectangle();
	}


}
