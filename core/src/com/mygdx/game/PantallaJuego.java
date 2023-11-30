package com.mygdx.game;
import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class PantallaJuego implements Screen {

	private SpaceNavigation game;
	private Manager mm;
	private OrthographicCamera camera;	
	private SpriteBatch batch;
	private Sound explosionSound;
	private Sound herida;
	private Music gameMusic;
	private int score;
	private int ronda;
	private int velXAsteroides; 
	private int velYAsteroides; 
	private int cantAsteroides;
	private NaveEnem naveEnem;
	private NaveJugador nave;
	
	private int velXEscudo; 
	private int velYEscudo; 
	private int cantEscudo;
	private EscudoProtector p;
	boolean escudoA;
	private Potenciador potenciador;
	
	private int velXCohete; 
	private int velYCohete; 
	private int cantMisil;
	private Cohete j;
	boolean coheteA;
	
	private  ArrayList<Ball2> balls1 = new ArrayList<>();// Hay 2 arrayList para manejar las coliciones 
	private  ArrayList<Ball2> balls2 = new ArrayList<>();
	private  ArrayList<Bullet> balas = new ArrayList<>();
	
	private  ArrayList<EscudoProtector> escudo1 = new ArrayList<>();
	private  ArrayList<EscudoProtector> escudo2 = new ArrayList<>();
	
	private  ArrayList<Cohete> misil1 = new ArrayList<>();
	private  ArrayList<Cohete> misil2 = new ArrayList<>();

	private MovimientoEstrategia estrategia;

	public PantallaJuego(SpaceNavigation game, int ronda, int vidas, int score,  
			int velXAsteroides, int velYAsteroides,int velXEscudo, int velYEscudo,int cantEscudo,int velXCohete ,int velYCohete, int cantMisil, int cantAsteroides, boolean escudoA, boolean coheteA) {
		this.game = game;
		this.ronda = ronda;
		this.score = score;
		this.velXAsteroides = velXAsteroides;
		this.velYAsteroides = velYAsteroides;
		this.cantAsteroides = cantAsteroides;
		this.velXEscudo=velXEscudo;
		this.velYEscudo=velYEscudo;
		this.cantEscudo=cantEscudo;
		this.potenciador = new Potenciador();
		setStrategy();
		
		
		batch = game.getBatch();
		camera = new OrthographicCamera();	
		camera.setToOrtho(false, 800, 640);
		
		//inicializar assets; musica de fondo y efectos de sonido
		explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));
		explosionSound.setVolume(1,0.2f);
		
		herida = Gdx.audio.newSound(Gdx.files.internal("ay.mp3"));
		herida.setVolume(1,0.25f);
		
		gameMusic = Gdx.audio.newMusic(Gdx.files.internal("noMeConoce.wav"));
		gameMusic.setLooping(true);
		gameMusic.setVolume(0.75f);
		gameMusic.play();;
		
		
		
	    // cargar imagen de la nave, 64x64   
	    nave = new NaveJugador(Gdx.graphics.getWidth()/2-50,30,new Texture(Gdx.files.internal("MainShip3.png")),
	    				Gdx.audio.newSound(Gdx.files.internal("hurt.ogg")), 
	    				new Texture(Gdx.files.internal("Rocket2.png")), 
	    				Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3")));
	    
	    
	    
	    //Ve si tiene el escudo activo
	    
	    potenciador.usarPowerUps(nave, escudoA, coheteA); //Se usa aquí la interfaz
	    
        nave.setVidas(vidas);
        
		
		//crear NaveEnem
		naveEnem = new NaveEnem(Gdx.graphics.getWidth()/2-50,600,new Texture(Gdx.files.internal("NaveMala.png")),
				Gdx.audio.newSound(Gdx.files.internal("hurt.ogg")), 
				new Texture(Gdx.files.internal("Rocket2.png")), 
				Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3"))); 
		naveEnem.setVidas(1);
		
        //crear asteroides
        Random r = new Random();
	    for (int i = 0; i < cantAsteroides; i++) {
	        Ball2 bb = new Ball2(r.nextInt((int)Gdx.graphics.getWidth()),
	  	            50+r.nextInt((int)Gdx.graphics.getHeight()-50),
	  	            20+r.nextInt(10), velXAsteroides+r.nextInt(4), velYAsteroides+r.nextInt(4), 
	  	            new Texture(Gdx.files.internal("aGreyMedium4.png")));	   
	  	    balls1.add(bb);
	  	    balls2.add(bb);
	  	}
	    
	    //Crear Escudos
	    potenciador.generarEscudos(escudo1, escudo2, cantEscudo, velXEscudo, velYEscudo);
	    
	    
	  //Crear Cohetes  
	    potenciador.generarCohetes(misil1, misil2, cantMisil, velXCohete, velYCohete);
	    
	    mm = new Manager(nave,explosionSound,herida, naveEnem, balls1,balls2,balas);
	}
    
	public void dibujaEncabezado() {
		CharSequence str = "Vidas: "+nave.getVidas()+" Ronda: "+ronda;
		if (naveEnem.getViva()) {
	        CharSequence str2 = "Vidas Enemigo: " + naveEnem.getVidas();
	        game.getFont().draw(batch, str2, 10, 780);
	    }
		game.getFont().getData().setScale(2f);		
		game.getFont().draw(batch, str, 10, 30);
		game.getFont().draw(batch, "Score:"+this.score, Gdx.graphics.getWidth()-150, 30);
		game.getFont().draw(batch, "HighScore:"+game.getHighScore(), Gdx.graphics.getWidth()/2-100, 30);
	}
		
	@Override
	public void render(float delta) {
		  Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
          batch.begin();
		  dibujaEncabezado();
		  
	      if (!nave.estaHerido()) {
 // colisiones entre balas y asteroides y su destruccion  
	    	 
	    	  mm.balasM(batch,balls1, balls2, balas);
	    	  
		      
		      for (int i=0;i<escudo1.size();i++) {
		    	  escudo1.get(i).update();
		      }
		      
		      for(int i = 0 ; i < misil1.size() ; i++) {
		    	  misil1.get(i).update();
		      }
		      
		      //interaccion bala con escudos	      
	    	  potenciador.interactuarBalasConEscudos(balas, escudo1, escudo2);
	    	  potenciador.interactuarBalasConCohete(balas, misil1, misil2);
		      
		      //Colision entre escudos ////
		      potenciador.comprobarColisionEscudos(escudo1, escudo2);
		     
		    //Colision entre Cohetes   /**/
		      potenciador.comprobarColisionCohetes(misil1, misil2);
		      
	      }      
	      nave.draw(batch, this, estrategia);
	      
	      if(!naveEnem.estaDestruido()) {
	    	  naveEnem.draw(batch, this, estrategia);
	      }
	      
	      //nave choca con escudo  /////
	      potenciador.comprobarColisionNaveConEscudos(batch,nave, escudo1, escudo2);
	      
	    //nave choca con cohete  /**/
	      potenciador.comprobarColisionNaveConCohetes(batch,nave,misil1,misil2); 
	      
	      nave.verificarGameOver(game, score, balls1, ronda, velXAsteroides, velYAsteroides, velXEscudo, velYEscudo, cantEscudo, velXCohete, velYCohete, cantMisil, cantAsteroides);
	      batch.end();
	      
	}

	public void setStrategy() { 
        MovimientoEstrategiaManager manager = MovimientoEstrategiaManager.getInstance();
        estrategia = manager.getEstrategia();
    }

	
    public boolean agregarBala(Bullet bb) {
    	return balas.add(bb);
    }
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		gameMusic.play();
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
		this.explosionSound.dispose();
		this.gameMusic.dispose();
		
		
	}
	
}
