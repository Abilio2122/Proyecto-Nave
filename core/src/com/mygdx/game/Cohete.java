package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class Cohete extends PowerUp {
	private int disparos;
	
	public Cohete(int x, int y, int size, int xSpeed, int ySpeed, Texture tx) {
		super(x, y, size, xSpeed, ySpeed, tx);
	}

	
	public void apply(NaveJugador nave) {
    	nave.activarPotenciador(this);
    	nave.setNaveTexturaConCohete();
	}
	
	 
	 public int getDisparos() {
			return disparos;
	 }

}
