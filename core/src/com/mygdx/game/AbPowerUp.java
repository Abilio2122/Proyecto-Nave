package com.mygdx.game;

import java.util.Random;

public class AbPowerUp implements PowerUp{ //Clase general para los potenciadores
	private String name;
	private int seg;
	
	public AbPowerUp(String name,int seg) {
		this.name = name;
		this.seg = seg;
	}
	
	public String getName() {
		return name;
	}
	
	public int getSeg() {
		return seg;
	}
	
	public void aplicarPot(NaveAbstract nave) {
		//aca se hara lo necesario para implemetnarlo
	}
	
}
