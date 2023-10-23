package com.mygdx.game;

public class Cohete implements PowerUp {
	private String name;
	private int seg;
	private int disparos;
	
	public Cohete(String name,int seg,int disparos) {
		this.name = name;
		this.seg = seg;
		this.disparos = disparos;
	}
	
	public int getDisparos() {
		return disparos;
	}
	
	
	public void apply(NaveJugador nave) {
		
	}
}
