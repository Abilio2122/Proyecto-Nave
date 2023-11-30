package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class NaveEnemBuilder implements NaveBuilder{
	
	private int x;
    private int y;
    private Texture textura;
    private Sound soundChoque;
    private Sound soundBala;
    private Texture texturaBala;
    private float disparoIntervalo;
    private NaveAbstract naveConstruida;

	@Override
	 public void buildPosicion(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void buildTextura(Texture tx) {
        this.textura = tx;
    }

    @Override
    public void buildSonidos(Sound soundChoque, Sound soundBala) {
        this.soundChoque = soundChoque;
        this.soundBala = soundBala;
    }

    @Override
    public void buildBalaTexture(Texture txBala) {
        this.texturaBala = txBala;
    }

    @Override
    public void buildDisparoIntervalo(float disparoIntervalo) {
        this.disparoIntervalo = disparoIntervalo;
    }

	@Override
	public void buildNave() {
		NaveEnem naveEnem = new NaveEnem(x, y, textura, soundChoque, texturaBala, soundBala);
		naveEnem.setDisparoIntervalo(disparoIntervalo);
		naveConstruida = naveEnem;
	}

	@Override
	public NaveAbstract getNave() {
		// TODO Auto-generated method stub
		return naveConstruida;
	}



}
