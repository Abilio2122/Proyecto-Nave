package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Bullet {

	private int xSpeed;
	private int ySpeed;
	private boolean destroyed = false;
	private Sprite spr;
	private NaveJugador Nave;
	
	    public Bullet(float x, float y, int xSpeed, int ySpeed, Texture tx, NaveJugador nave) {
	    	spr = new Sprite(tx);
	    	spr.setPosition(x, y);
	        this.xSpeed = xSpeed;
	        this.ySpeed = ySpeed;
	        this.Nave = nave;
	        
	    }
	    public void update() {
	        spr.setPosition(spr.getX()+xSpeed, spr.getY()+ySpeed);
	        if (spr.getX() < 0 || spr.getX()+spr.getWidth() > Gdx.graphics.getWidth()) {
	            destroyed = true;
	        }
	        if (spr.getY() < 0 || spr.getY()+spr.getHeight() > Gdx.graphics.getHeight()) {
	        	destroyed = true;
	        }
	        
	    }
	    
	    public void draw(SpriteBatch batch) {
	    	spr.draw(batch);
	    }
	    
	    public boolean checkCollision(Ball2 b2) {
	        if(spr.getBoundingRectangle().overlaps(b2.getArea())){
	        	// Se destruyen ambos
	            this.destroyed = true;
	            return true;
	
	        }
	        return false;
	    }
	    
	    public boolean checkCollisionNave(NaveJugador nave) {
	        if(spr.getBoundingRectangle().overlaps(nave.getArea())){
	        	this.destroyed = true;
	        	int vidas=nave.getVidas();
	        	vidas--;
	        	nave.setVidas(vidas);
	            if (vidas <= 0)
	                nave.destruida = true;
	        }
	        return false;
	    }
	    
	    public boolean checkCollision(EscudoProtector b2) {
	        if(spr.getBoundingRectangle().overlaps(b2.getArea())&& Nave!= null){
	        	// Se destruyen ambos
	            this.destroyed = true;
	            
	         // Aplicar el efecto del EscudoProtector en la nave
	            Nave.aplicarEscudoProtector(b2);
	            
	            
	            // Cambiar la textura de la nave a la versión con escudo
	            Nave.setNaveTexturaConEscudo();
	            return true;
	            
	
	        }
	        return false;
	    }
	    
	    
	    public boolean isDestroyed() {return destroyed;}
	
}
