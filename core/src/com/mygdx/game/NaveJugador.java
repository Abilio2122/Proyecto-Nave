package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
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
	private float disparoIntervalo;

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
    public void draw(SpriteBatch batch, PantallaJuego juego,MovimientoEstrategia str) {
        float x = spr.getX();
        float y = spr.getY();
        if (!herido) {
        	str.procesarEntrada(x, y, xVel, yVel, spr);
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
    
  
    //recibe cohete
    public boolean checkCollisione(Cohete l) {
        if(l.getArea().overlaps(spr.getBoundingRectangle())){
            activarPotenciador(l);
            spr.setTexture(texturaConCohete);
            return true;
        }
        return false;
    }
    
    // recibe escudo
    public boolean checkCollisione(EscudoProtector e) {
        if(e.getArea().overlaps(spr.getBoundingRectangle())){
        	activarPotenciador(e);
            spr.setTexture(texturaConEscudo);
            return true;
        }
        
        return false;
    }
    
    //manejo colision con asteroides
    public void manejarColisionesA(SpriteBatch batch,ArrayList<Ball2> balls1, ArrayList<Ball2> balls2, ArrayList<Bullet> balas) {
        for (int i = 0; i < balls1.size(); i++) {
            Ball2 b = balls1.get(i);
            b.draw(batch);

            // Perdió vida o game over
            if (checkCollisionNave(b)) {
                // Asteroide se destruye con el choque
                balls1.remove(i);
                balls2.remove(i);
                i--;
            }
        }
    }
    
  //colision con asteroides (parte de manejo de colisiones de asteroides)
    public boolean checkCollision(Ball2 b) {
        if (!herido && b.getArea().overlaps(spr.getBoundingRectangle())) {
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
    
   
    public void verificarGameOver(SpaceNavigation game, int score, ArrayList<Ball2> balls1, int ronda, int velXAsteroides, int velYAsteroides, int velXEscudo, int velYEscudo, int cantEscudo, int velXCohete, int velYCohete, int cantMisil, int cantAsteroides) {
        if (estaDestruido()) {
            if (score > game.getHighScore()) {
                game.setHighScore(score);
            }
            PantallaGameOver pantallaGameOver = new PantallaGameOver(game);
            pantallaGameOver.resize(1200, 800);
            game.setScreen(pantallaGameOver);
            
        }
        

        // Nivel completado
        if (balls1.size() == 0) {
            PantallaJuego pantallaJuego = new PantallaJuego(game, ronda + 1, getVidas(), score,
                    velXAsteroides + 1, velYAsteroides + 1, velXEscudo, velYEscudo, cantEscudo,
                    velXCohete, velYCohete, cantMisil, cantAsteroides + 2,
                    tieneEscudoActivo(), tienePotenciadorCohete());
            pantallaJuego.resize(1200, 800);
            game.setScreen(pantallaJuego);
            
        }
    }
    
    
    
    public boolean checkCollisionNave(Ball2 b) {
        if (!herido && b.getArea().overlaps(spr.getBoundingRectangle())) {
            //consecuencias de la nave
            if(tieneEscudoActivo()== false) {
                vidas--;
                herido = true;
                tiempoHerido = tiempoHeridoMax;
                sonidoHerido.play();
            } else {
                desactivarEscudo();
                spr.setTexture(texturaSinEscudo);
            }
            if (vidas <= 0)
                destruida = true;
            return true;
        }

        return false;
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
    
    //sobrecarga de metodos 
    public void activarPotenciador(Cohete cohete) {
        tieneCohete = true;
    }
    
    public void activarPotenciador(EscudoProtector escudoProtector) {
		escudoActivo = true;
		
	}
    
    public boolean tienePotenciadorCohete() {
        return tieneCohete;
    }
    
    public void desactivarCohete() {
    	tieneCohete = false;
    }
    
    public void setDisparoIntervalo(float disparoIntervalo) {
        this.disparoIntervalo = disparoIntervalo;
    }
    
    public void setNaveTexturaConCohete() {
        spr.setTexture(texturaConCohete);
    }

    public Sprite getSprite() {
        return sprite;
    }

}