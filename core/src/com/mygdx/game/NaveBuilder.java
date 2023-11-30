package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;

public interface NaveBuilder {
	void buildPosicion(int x, int y);
    void buildTextura(Texture tx);
    void buildSonidos(Sound soundChoque, Sound soundBala);
    void buildBalaTexture(Texture txBala);
    void buildDisparoIntervalo(float disparoIntervalo);
    void buildNave(); 
    NaveAbstract getNave();

}
