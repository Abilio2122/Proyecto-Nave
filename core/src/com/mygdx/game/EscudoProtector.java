package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class EscudoProtector extends PowerUp {
	public EscudoProtector(int x, int y, int size, int xSpeed, int ySpeed, Texture tx) {
		super(x, y, size, xSpeed, ySpeed, tx);
	}


    public void apply(NaveJugador nave) {
        nave.activarPotenciador(this);
    	nave.setNaveTexturaConEscudo();
    }

    
    
}