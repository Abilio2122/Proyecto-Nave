package com.mygdx.game;
import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Potenciador {
	private boolean tienePotenciador;
	
	public void usarPowerUps(NaveJugador nave,boolean escudoA,boolean coheteA) {
        // Crea instancias de EscudoProtector y Cohete
		PowerUp escudo = new EscudoProtector(0, 0, 0, 0, 0, new Texture(Gdx.files.internal("escudo.png")));
	    PowerUp cohete = new Cohete(0, 0, 0, 0, 0, new Texture(Gdx.files.internal("Misil.png")));


        // Aplica los power-ups a la nave
	    if(escudoA) {
	    	escudo.apply(nave);
	    }
	    
	    if(coheteA) {
	    	cohete.apply(nave);
	    }
    }

/*COHETE*/
    public void generarCohetes(ArrayList<Cohete> Misil1, ArrayList<Cohete> Misil2, int cantMisil, int velXCohete, int velYCohete){
        Random q = new Random();
        for (int i = 0; i < cantMisil; i++) {
            Cohete cohete = new instanciación(q.nextInt((int)Gdx.graphics.getWidth()),
                    150 + q.nextInt((int)Gdx.graphics.getHeight() - 80),
                    50 + q.nextInt(10), velXCohete + q.nextInt(4), velYCohete + q.nextInt(4),
                    new Texture(Gdx.files.internal("Misil.png")));
            Misil1.add(cohete);
            Misil2.add(cohete);
        }
    }
    
    public void comprobarColisionCohetes(ArrayList<Cohete> misil1, ArrayList<Cohete> misil2) {
        for(int i = 0 ; i < misil1.size() ; i++) {
            Cohete m1 = misil1.get(i);
            for(int j = 0 ; j < misil2.size() ; j++) {
                Cohete m2 = misil2.get(i);
                if(i<j) {
                    m1.checkCollision(m2);
                }
            }
        }
    }

    public void comprobarColisionNaveConCohetes(SpriteBatch batch, NaveJugador nave, ArrayList<Cohete> misil1, ArrayList<Cohete> misil2) {
        for (int i = 0; i < misil1.size(); i++) {
            Cohete b = misil1.get(i);
            b.draw(batch);

            if (nave.checkCollisione(b)) {
                // Cohete se destruye con el choque             
                misil1.remove(i);
                misil2.remove(i);
                i--;
            }
        }
    }
    
    
    public void interactuarBalasConCohete(ArrayList<Bullet> balas, ArrayList<Cohete> misil1, ArrayList<Cohete> misil2) {
        for (int i = 0; i < balas.size(); i++) {
            Bullet b = balas.get(i);
            b.update();
            for (int j = 0; j < misil1.size(); j++) {
                if (b.checkCollision(misil1.get(j))) {
                    misil1.remove(j);
                    misil2.remove(j);
                    j--;
                }
            }
            if (b.isDestroyed()) {
                balas.remove(b);
                i--;
            }
        }
    }
    
    
    
/*ESCUDO*/
    public void generarEscudos(ArrayList<EscudoProtector> escudo1, ArrayList<EscudoProtector> escudo2, int cantEscudo, int velXEscudo, int velYEscudo) {
        Random e = new Random();
        for (int i = 0; i < cantEscudo; i++) {
            EscudoProtector escudo = new EscudoProtector(e.nextInt((int)Gdx.graphics.getWidth()),
                    150 + e.nextInt((int)Gdx.graphics.getHeight() - 80),
                    50 + e.nextInt(10), velXEscudo + e.nextInt(4), velYEscudo + e.nextInt(4),
                    new Texture(Gdx.files.internal("escudo.png")));
            escudo1.add(escudo);
            escudo2.add(escudo);
        }
    }
    
    public void comprobarColisionEscudos(ArrayList<EscudoProtector> escudo1, ArrayList<EscudoProtector> escudo2) {
        for (int i = 0; i < escudo1.size(); i++) {
            EscudoProtector e1 = escudo1.get(i);
            for (int j = 0; j < escudo2.size(); j++) {
                EscudoProtector e2 = escudo2.get(j);
                if (i < j) {
                    e1.checkCollision(e2);
                }
            }
        }
    }
    
    public void comprobarColisionNaveConEscudos(SpriteBatch batch, NaveJugador nave, ArrayList<EscudoProtector> escudo1, ArrayList<EscudoProtector> escudo2) {
        for (int i = 0; i < escudo1.size(); i++) {
            EscudoProtector b = escudo1.get(i);
            b.draw(batch);

            if (nave.checkCollisione(b)) {
                // El escudo se destruye con el choque
                escudo1.remove(i);
                escudo2.remove(i);
                i--;
            }
        }
    }
    
    public void interactuarBalasConEscudos(ArrayList<Bullet> balas, ArrayList<EscudoProtector> escudo1, ArrayList<EscudoProtector> escudo2) {
        for (int i = 0; i < balas.size(); i++) {
            Bullet b = balas.get(i);
            b.update();
            for (int j = 0; j < escudo1.size(); j++) {
                if (b.checkCollision(escudo1.get(j))) {
                    escudo1.remove(j);
                    escudo2.remove(j);
                    j--;
                    
                }
            }
            if (b.isDestroyed()) {
                balas.remove(b);
                i--;
            }
        }
    }


    public void activarPotenciador() {
    	tienePotenciador = true;
    }

    

}
