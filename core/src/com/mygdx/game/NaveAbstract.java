package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

	public abstract class NaveAbstract {
		protected boolean destruida = false;
	    protected int vidas = 3;
	    protected float xVel = 0;
	    protected float yVel = 0;
	    protected Sprite spr;
	    protected Sound sonidoHerido;
	    protected Sound soundBala;
	    protected Texture txBala;
	    protected boolean herido = false;
	    protected int tiempoHeridoMax=10;
	    protected int tiempoHerido;
	    protected long lastShotTime;
	    protected float disparoIntervalo;

	    public NaveAbstract(int vidas, float xVel, float yVel, Texture tx, Sound soundChoque, Texture txBala, Sound soundBala) {
	        this.vidas = vidas;
	        this.xVel = xVel;
	        this.yVel = yVel;
	        sonidoHerido = soundChoque;
	        this.soundBala = soundBala;
	        this.txBala = txBala;
	        spr = new Sprite(tx);
	        lastShotTime = System.currentTimeMillis();
	    }

	    public abstract void draw(SpriteBatch batch, PantallaJuego juego);

	    public abstract void disparar(PantallaJuego juego);
	    
	    
	    public boolean estaDestruido() {
	        return !herido && destruida;
	    }

	    public boolean estaHerido() {
	        return herido;
	    }

	    public int getX() {
	        return (int) spr.getX();
	    }

	    public int getY() {
	        return (int) spr.getY();
	    }

	    public int getVidas() {
	        return vidas;
	    }

	    public void setVidas(int vidas2) {
	        vidas = vidas2;
	    }
	    
	    public Rectangle getArea() {
	    	return spr.getBoundingRectangle();
		}
}