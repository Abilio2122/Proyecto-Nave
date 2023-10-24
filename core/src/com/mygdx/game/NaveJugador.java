package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class NaveJugador extends NaveAbstract {
	private boolean invulnerable = false;
	private boolean escudoActivo = false;
	private Texture texturaSinEscudo = new Texture(Gdx.files.internal("MainShip3.png"));
	private Texture texturaConEscudo = new Texture(Gdx.files.internal("MainShip4.png"));
	
    public NaveJugador(int x, int y, Texture tx, Sound soundChoque, Texture txBala, Sound soundBala) {
        super(3, 0, 0, tx, soundChoque, txBala, soundBala);
        spr = new Sprite(tx);
        spr.setPosition(x, y);
        spr.setBounds(x, y, 45, 45);
    }

    @Override
    public void draw(SpriteBatch batch, PantallaJuego juego) {
        float x = spr.getX();
        float y = spr.getY();
        if (!herido) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) xVel--;
            if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) xVel++;
            if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) yVel--;
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) yVel++;

            if (x + xVel < 0 || x + xVel + spr.getWidth() > Gdx.graphics.getWidth())
                xVel *= -1;
            if (y + yVel < 0 || y + yVel + spr.getHeight() > Gdx.graphics.getHeight())
                yVel *= -1;

            spr.setPosition(x + xVel, y + yVel);
            
            disparar(juego);
            
            spr.draw(batch);
        } else {
            spr.setX(spr.getX() + MathUtils.random(-2, 2));
            spr.draw(batch);
            spr.setX(x);
            tiempoHerido--;
            if (tiempoHerido <= 0) herido = false;
        }
        
    }

    @Override
    public void disparar(PantallaJuego juego) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            Bullet bala = new Bullet(spr.getX() + spr.getWidth() / 2 - 5, spr.getY() + spr.getHeight() - 5, 0, 3, txBala, this);
            juego.agregarBala(bala);
            soundBala.play();
        }
    }

    public boolean checkCollision(Ball2 b) {
        if (!herido && b.getArea().overlaps(spr.getBoundingRectangle())) {
        	
        	// cambia la direccion de movimiento por colision entre nave y ball2
            if (xVel == 0) xVel += b.getXSpeed() / 2;
            if (b.getXSpeed() == 0) b.setXSpeed(b.getXSpeed() + (int) xVel / 2);
            //se invierten las direcciones entre ambos
            xVel = -xVel;
            b.setXSpeed(-b.getXSpeed());
            
            //lo mismo pero en vertical
            if (yVel == 0) yVel += b.getySpeed() / 2;
            if (b.getySpeed() == 0) b.setySpeed(b.getySpeed() + (int) yVel / 2);
            yVel = -yVel;
            b.setySpeed(-b.getySpeed());
            
            //consecuencias de la nave
            if(tieneEscudoActivo()== false) {
            	vidas--;
            	herido = true;
            	tiempoHerido = tiempoHeridoMax;
            	sonidoHerido.play();
            }else {
            	desactivarEscudo();
            	spr.setTexture(texturaSinEscudo);
            }
            if (vidas <= 0)
                destruida = true;
            return true;
        }
     
        return false;
    }
    
    public boolean checkCollisione(EscudoProtector e) {
        if(e.getArea().overlaps(spr.getBoundingRectangle())){
        	// rebote
            if (xVel ==0) xVel += e.getXSpeed()/2;
            if (e.getXSpeed() ==0) e.setXSpeed(e.getXSpeed() + (int)xVel/2);
            xVel = - xVel;
            e.setXSpeed(-e.getXSpeed());
            
            if (yVel ==0) yVel += e.getySpeed()/2;
            if (e.getySpeed() ==0) e.setySpeed(e.getySpeed() + (int)yVel/2);
            yVel = - yVel;
            e.setySpeed(- e.getySpeed());
            
            aplicarEscudoProtector(e);
            spr.setTexture(texturaConEscudo);
            return true;
        }
        
        return false;
    }
    
	public void aplicarEscudoProtector(EscudoProtector escudoProtector) {
		escudoActivo = true;
		
	}
	
    public void desactivarEscudo() {
        escudoActivo = false;
    }
    
    public boolean tieneEscudoActivo() {
        return escudoActivo;
    }
    
    public void setNaveTexturaConEscudo() {
        spr.setTexture(texturaConEscudo);
    }
    
    /*
    public boolean checkCollisionBala(Bullet bala) {
    	if (!herido && bala.getArea().overlaps(spr.getBoundingRectangle())) {
    		
    	}
    }
    
    public void verificarColisionesConBalas(ArrayList<Bullet> balas) {
        for (int i = 0; i < balas.size(); i++) {
            Bullet bala = balas.get(i);
            if (checkCollisionBala(bala)) {
                // La bala colisionó con la nave del jugador
                balas.remove(i);
                i--;
                // Lógica adicional, como reducir vidas del jugador, reproducir sonidos, etc.
            }
        }
    }
    */
    
    /*
    public boolean getInvulnerable() {
    	return invulnerable;
    }
    
    public void setInvulnerable(boolean invulnerable) {
    	this.invulnerable = invulnerable;
    }
    */
    
    
    public Rectangle getArea() {
    	return spr.getBoundingRectangle();
    }
    
}
