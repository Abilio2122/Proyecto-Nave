package com.mygdx.game;


import java.util.Random;
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
	
	public void aplicarPot(NaveAbstract nave) {
		//Se llamara a una funcion que aumente la salud de la nave, asi como demas implementaciones
	}
}
