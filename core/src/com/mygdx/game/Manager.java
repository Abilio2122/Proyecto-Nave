package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.Iterator;

public class Manager {
	private  ArrayList<Ball2> balls1 = new ArrayList<>();// Hay 2 arrayList para manejar las coliciones 
	private  ArrayList<Ball2> balls2 = new ArrayList<>();
	private  ArrayList<Bullet> balas = new ArrayList<>();
    private NaveJugador nave;
    private NaveEnem naveEnem;
    private Sound explosionSound;
    private Sound herida;
    private int score;

    public Manager(NaveJugador nave, Sound explosionSound, Sound herida, NaveEnem naveEnem, ArrayList<Ball2> balls1, ArrayList<Ball2> balls2) {
    	  this.nave = nave;
    	  this.naveEnem = naveEnem;
    	  this.explosionSound = explosionSound;
    	  this.herida = herida;
    	  this.balas = new ArrayList<>();
    	  this.balls1 = balls1;
          this.balls2 = balls2;
    	  this.score = 0;
    }
    
    public void balasM(SpriteBatch batch,ArrayList<Ball2> balls1, ArrayList<Ball2> balls2, ArrayList<Bullet> balas) {
    	Iterator<Bullet> iterator = balas.iterator();
        while (iterator.hasNext()) {
            Bullet b = iterator.next();
            b.update();

            // Comprobar colisión con asteroides
            Iterator<Ball2> asteroidIterator = balls1.iterator();
            while (asteroidIterator.hasNext()) {
                Ball2 asteroid = asteroidIterator.next();
                if (b.checkCollision(asteroid)) {
                    explosionSound.play();
                    asteroidIterator.remove();
                    balls2.remove(asteroid);
                    iterator.remove();
                    score += 10;
                }
            }

            // Comprobar colisión con nave
            if (b.checkCollisionNave(nave)) {
                herida.play();
                iterator.remove();
            }

            // Comprobar colisión con nave enemiga
            if (b.checkCollisionNave(naveEnem)) {
                herida.play();
                iterator.remove();
            }

            b.draw(batch);
        }

        // Actualizar posiciones de asteroides
        for (Ball2 asteroid : balls1) {
            asteroid.update();
        }

        // Manejar colisiones de nave
        nave.manejarColisionesA(batch, balls1, balls2, balas);

     
    }

}
