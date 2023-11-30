package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;


public class PantallaMenu implements Screen {

	private SpaceNavigation game;
	private OrthographicCamera camera;
	private Texture texture;

	public PantallaMenu(SpaceNavigation game) {
		this.game = game;
        
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1200, 800);
		texture = new Texture(Gdx.files.internal("fondo.jpg"));
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0.001f, 0.001f, 0.001f, 1);

		camera.update();
		game.getBatch().setProjectionMatrix(camera.combined);

		game.getBatch().begin();
		game.getBatch().draw(texture, 0, 0, texture.getWidth(), texture.getHeight());
		
		game.getFont().draw(game.getBatch(), "Bienvenido a Space Navigation !", 140, 400);
		game.getFont().draw(game.getBatch(), "Pincha en cualquier lado o presiona cualquier tecla para comenzar ...", 100, 300);
	
		game.getBatch().end();

		if (Gdx.input.isTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
			Screen ss = new PantallaJuego(game,1,3,0,1,1,1,1,1,1,1,3,10,false,false);
			ss.resize(1200, 800);
			game.setScreen(ss);
			dispose();
		}
	}
	
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
   
}