package com.mygdx.game;

public class Cohete extends AbPowerUp {
private int disparos;
	
	public Cohete(String name,int seg,int disparos) {
		super(name,seg);
		this.disparos = disparos;
	}
	
	public int getDisparos() {
		return disparos;
	}
	
	
	public void aplicarPot(NaveAbstract nave) {
		
	}
}
