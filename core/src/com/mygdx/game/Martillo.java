package com.mygdx.game;


import java.util.Random;
public class Martillo extends AbPowerUp {
	private int masVida;
	
	public Martillo(String name,int seg,int masVida) {
		super(name,seg);
		this.masVida = masVida;
	}
	
	public int getMasVida() {
		return masVida;
	}
	
	public void aplicarPot(NaveAbstract nave) {
		//Se llamara a una funcion que aumente la salud de la nave, asi como demas implementaciones
	}
}
