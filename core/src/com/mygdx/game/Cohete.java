package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Cohete extends PowerUp {
	private int disparos;
	
	public void apply(NaveJugador nave) {
    	nave.activarPotenciador(this);
    	nave.setNaveTexturaConCohete();
	}
	
	 
	 public void draw(SpriteBatch batch) {
	    	spr.draw(batch);
	    	
	 }
	
	 
	 public void update() {
			// TODO Auto-generated method stub
			x += getXSpeed();
	        y += getySpeed();

	        if (x+getXSpeed() < 0 || x+getXSpeed()+spr.getWidth() > Gdx.graphics.getWidth())
	        	setXSpeed(getXSpeed() * -1);
	        if (y+getySpeed() < 0 || y+getySpeed()+spr.getHeight() > Gdx.graphics.getHeight())
	        	setySpeed(getySpeed() * -1);
	        spr.setPosition(x, y);
		}
	 
	 
	//rebota entre cohetes
	 public void checkCollision(PowerUp b2) {
	        if(spr.getBoundingRectangle().overlaps(b2.spr.getBoundingRectangle())){
	        	// rebote
	            if (getXSpeed() ==0) setXSpeed(getXSpeed() + b2.getXSpeed()/2);
	            if (b2.getXSpeed() ==0) b2.setXSpeed(b2.getXSpeed() + getXSpeed()/2);
	        	setXSpeed(- getXSpeed());
	            b2.setXSpeed(-b2.getXSpeed());
	            
	            //que hace aca
	            if (getySpeed() ==0) setySpeed(getySpeed() + b2.getySpeed()/2);
	            if (b2.getySpeed() ==0) b2.setySpeed(b2.getySpeed() + getySpeed()/2);
	            setySpeed(- getySpeed());
	            b2.setySpeed(- b2.getySpeed()); 
	        }
	    }
	 
	 public int getDisparos() {
			return disparos;
	 }
	
	 public int getySpeed() {
		// TODO Auto-generated method stub
		return ySpeed;
	 }

	 public int getXSpeed() {
		// TODO Auto-generated method stub
		return xSpeed;
	 }
		
	 public void setXSpeed(int xSpeed) {
		// TODO Auto-generated method stub
		this.xSpeed = xSpeed;
	 }
		
	 public void setySpeed(int ySpeed) {
		// TODO Auto-generated method stub
		this.ySpeed = ySpeed;
	 }

	 public Rectangle getArea() {
		return spr.getBoundingRectangle();
	 }





}
