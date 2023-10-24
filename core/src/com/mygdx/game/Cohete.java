package com.mygdx.game;

public class Cohete implements PowerUp {
	private String name;
	private int seg;
	private int disparos;
	private int xSpeed;
	private int ySpeed;
	
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
	
	public void setXSpeed(int xSpeed) {
		// TODO Auto-generated method stub
		this.xSpeed = xSpeed;
	}
	
	public void setySpeed(int ySpeed) {
		// TODO Auto-generated method stub
		this.ySpeed = ySpeed;
	}

}
