package com.mygdx.game;


import java.util.Random;
import com.badlogic.gdx.utils.Timer;

public class Martillo implements PowerUp{
	private String name;
	private int seg;
	private int masVida;
	
	public Martillo(String name,int seg,int masVida) {
		this.name = name;
		this.seg = seg;
		this.masVida = masVida;
	}
	public String getName() {
		return name;
	}
	
	public int getSeg() {
		return seg;
	}
	
	public int getMasVida() {
		return masVida;
	}
	
	public void apply(NaveJugador nave) {
		int vidaAct = nave.getVidas();
		int vidasFaltantes = 3 - vidaAct;
		
		if(vidasFaltantes > 0) {
			nave.setVidas(Math.min(3, vidaAct + vidasFaltantes));
		}
		
		nave.setInvulnerable(true);
		long tiempoActual = System.currentTimeMillis();

        // Calcular el tiempo en el que se desactivará la invulnerabilidad (5 segundos después)
        final long tiempoDesactivacion = tiempoActual + 5000; // 5000 milisegundos (5 segundos)

        // Crear un bucle para comprobar el tiempo actual y desactivar la invulnerabilidad cuando sea necesario
        while (System.currentTimeMillis() < tiempoDesactivacion) {
            // Espera hasta que se alcance el tiempo de desactivación
        }

        // Desactivar la invulnerabilidad
        nave.setInvulnerable(false);
		
	}
}
