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

    public Manager(NaveJugador nave, Sound explosionSound, Sound herida, NaveEnem naveEnem) {
    	  this.nave = nave;
    	  this.naveEnem = naveEnem;
    	  this.explosionSound = explosionSound;
    	  this.herida = herida;
    	  this.balas = new ArrayList<>();
    	  this.score = 0;
    }
    
    public void balasM(SpriteBatch batch,ArrayList<Ball2> balls1, ArrayList<Ball2> balls2, ArrayList<Bullet> balas) {
    	for (int i = 0; i < balas.size(); i++) {
            Bullet b = balas.get(i);
            b.update();
            //arreglo de asteroides
            for (int j = 0; j < balls1.size(); j++) {    
              if (b.checkCollision(balls1.get(j))) {          
            	 explosionSound.play();
            	 balls1.remove(j);
            	 balls2.remove(j);
            	 j--;
            	 score +=10;
              }
  	        }
            
          //manejo de colision con nave
            if(b.checkCollisionNave(nave)) {
            	 herida.play();   // no se por que no suena
            }
            
          //manejo de colision con nave enemiga
            if(b.checkCollisionNave(naveEnem)) {
            	 herida.play();   // no se por que no suena
            }

            
            b.draw(batch);
            if (b.isDestroyed()) {
                balas.remove(b);
                i--; //para no saltarse 1 tras eliminar del arraylist
            }
            
            for (Ball2 ball : balls1) {
		          ball.update();
		      }
            
            for (int k=0;k<balls1.size();k++) {
		    	Ball2 ball1 = balls1.get(k);   
		        for (int j=0;j<balls2.size();j++) {
		          Ball2 ball2 = balls2.get(j); 
		          if (k<j) {
		        	  ball1.checkCollision(ball2);
		     
		          }
		        }
		    }
    	}
    	 for (Bullet b : balas) {       
 	          b.draw(batch);
 	      }
 	      
 	     nave.manejarColisionesA(batch, balls1, balls2, balas);
    }

}
