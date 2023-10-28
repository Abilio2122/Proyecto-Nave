package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class NaveJugador extends NaveAbstract {
	
	private boolean escudoActivo = false;
	private boolean tieneCohete = false;
	private int disparosConCohete = 0;
	 private Sprite sprite;

	private Texture texturaSinEscudo = new Texture(Gdx.files.internal("MainShip3.png"));
	private Texture texturaConEscudo = new Texture(Gdx.files.internal("MainShip4.png"));
	private Texture texturaConCohete = new Texture(Gdx.files.internal("MainShip5.png"));
	
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
            if (tienePotenciadorCohete()) {
                // Dispara múltiples balas con una separación
                for (int i = 0; i < 2; i++) {
                    float offset = 20 * (i - 0.5f); // Ajusta el offset para crear una separación
                    Bullet bala = new Bullet(spr.getX() + spr.getWidth() / 2 - 5 + offset, spr.getY() + spr.getHeight() - 5, 0, 3, txBala,this);
                    bala.setBalaFromNave();
                    juego.agregarBala(bala);
                }
                soundBala.play();
                
                disparosConCohete++; // Incrementa el contador de disparos con cohete.
                
                if (disparosConCohete >= 5) {
                    desactivarCohete();
                    spr.setTexture(texturaSinEscudo); // Cambia la textura de la nave al final del efecto.
                }
            } else {
                // Si no tiene el potenciador "Cohete", dispara una sola bala.
                Bullet bala = new Bullet(spr.getX() + spr.getWidth() / 2 - 5, spr.getY() + spr.getHeight() - 5, 0, 3, txBala,this);
                bala.setBalaFromNave();
                juego.agregarBala(bala);
                soundBala.play();
            }
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
    
    
    
    public boolean checkCollisione(Cohete l) {
        if(l.getArea().overlaps(spr.getBoundingRectangle())){
        	// rebote
            if (xVel ==0) xVel += l.getXSpeed()/2;
            if (l.getXSpeed() ==0) l.setXSpeed(l.getXSpeed() + (int)xVel/2);
            xVel = - xVel;
            l.setXSpeed(-l.getXSpeed());
            
            if (yVel ==0) yVel += l.getySpeed()/2;
            if (l.getySpeed() ==0) l.setySpeed(l.getySpeed() + (int)yVel/2);
            yVel = - yVel;
            l.setySpeed(- l.getySpeed());
            
            activarPotenciador(l);
            spr.setTexture(texturaConCohete);
            return true;
        }
        return false;
    }
    

    public void activarPotenciador(Cohete cohete) {
        tieneCohete = true;
    }
    
    public boolean tienePotenciadorCohete() {
        return tieneCohete;
    }
    
    public void desactivarCohete() {
    	tieneCohete = false;
    }
    
    public void setNaveTexturaConCohete() {
        spr.setTexture(texturaConCohete);
    }

    public Sprite getSprite() {
        return sprite;
    }

}
