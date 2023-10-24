package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class NaveEnem extends NaveAbstract {
	
	private float xSpeed;
	
    public NaveEnem(int x, int y, Texture tx, Sound soundChoque, Texture txBala, Sound soundBala) {
        super(1, 0, 0, tx, soundChoque, txBala, soundBala);
        spr = new Sprite(tx);
        spr.setPosition(x, y);
        spr.setBounds(x, y, 45, 45);
        disparoIntervalo = 2.0f; // Intervalo de disparo en segundos
        // Inicializar la velocidad horizontal
        xSpeed = 2.0f; // Ajusta la velocidad según tus necesidades
    }

    @Override
    public void draw(SpriteBatch batch, PantallaJuego juego) {
        float x = spr.getX();
        float y = spr.getY();
        if (!herido) {
            // Lógica de movimiento de la nave enemiga aquí
        	// Actualizar la posición horizontal en cada fotograma
            x += xSpeed;

            // Comprobar si es tiempo de disparar
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastShotTime >= disparoIntervalo * 1000) {
                disparar(juego);
                lastShotTime = currentTime;
            }

            // Verificar los límites de pantalla para invertir la dirección
            if (x < 0 || x + spr.getWidth() > Gdx.graphics.getWidth()) {
                xSpeed = -xSpeed; // Invertir la dirección si alcanza los límites
            }

            spr.setPosition(x, y);
            spr.draw(batch);
            // Por ejemplo, mover la nave hacia abajo en cada frame

            // Comprobar si es tiempo de disparar
            long currentTime1 = System.currentTimeMillis();
            if (currentTime1 - lastShotTime >= disparoIntervalo * 1000) {
                disparar(juego); // Realizar disparo
                lastShotTime = currentTime1; // Actualizar el tiempo del último disparo
            }

            spr.setPosition(x, y);
            spr.draw(batch);
        } else {
            // Lógica de vibración de la nave herida
            spr.setX(spr.getX() + MathUtils.random(-2, 2));
            spr.draw(batch);
            spr.setX(x);
            tiempoHerido--;
            if (tiempoHerido <= 0) herido = false;
        }
    }

    @Override
    public void disparar(PantallaJuego juego) {
        Bullet bala = new Bullet(spr.getX() + spr.getWidth() / 2 - 5, spr.getY() + spr.getHeight() - 5, 0, -3, txBala);
        juego.agregarBala(bala);
        soundBala.play();
    }


}
