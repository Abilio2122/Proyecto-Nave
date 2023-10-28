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
	
	private  ArrayList<Cohete> Misil1 = new ArrayList<>();
	private  ArrayList<Cohete> Misil2 = new ArrayList<>();
	
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
		
		
		batch = game.getBatch();
		camera = new OrthographicCamera();	
		camera.setToOrtho(false, 800, 640);
		
		//inicializar assets; musica de fondo y efectos de sonido
		explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));
		explosionSound.setVolume(1,0.2f);
		
		herida = Gdx.audio.newSound(Gdx.files.internal("ay.mp3"));
		herida.setVolume(1,0.25f);
		
		gameMusic = Gdx.audio.newMusic(Gdx.files.internal("noMeConoce.wav")); //
		
		gameMusic.setLooping(true);
		gameMusic.setVolume(0.75f);
		gameMusic.play();
		
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
	    potenciador.generarCohetes(Misil1, Misil2, cantMisil, velXCohete, velYCohete);
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
	    	  
	    	  // parece se que las balas y los asteroides se ven en un arreglo horizontal que describen su movimiento posible
	    	  
	    	  //arreglo de balas
	    	  for (int i = 0; i < balas.size(); i++) {
		            Bullet b = balas.get(i);
		            b.update();
		            //arreglo de asteroides
		            for (int j = 0; j < balls1.size(); j++) {    
		              if (b.checkCollision(balls1.get(j))) {          
		            	 explosionSound.play();
		            	 balls1.remove(j);
		            	 balls2.remove(j);
		            	 j--;
		            	 score +=10;
		              }
		  	        }
		            
		          //manejo de colision con nave
		            if(b.checkCollisionNave(nave)) {
		            	 herida.play();   // no se por que no suena
		            }
		            
		          //manejo de colision con nave enemiga
		            if(b.checkCollisionNave(naveEnem)) {
		            	 herida.play();   // no se por que no suena
		            }

		            
		         //   b.draw(batch);
		            if (b.isDestroyed()) {
		                balas.remove(b);
		                i--; //para no saltarse 1 tras eliminar del arraylist
		            }
		            
		      }
		      //actualizar movimiento de asteroides dentro del area
		      for (Ball2 ball : balls1) {
		          ball.update();
		      }
		      
		      for (int i=0;i<escudo1.size();i++) {
		    	  escudo1.get(i).update();
		      }
		      
		      for(int i = 0 ; i < Misil1.size() ; i++) {
		    	  Misil1.get(i).update();
		      }
		      //interaccion bala con escudos
		      
	    	  potenciador.interactuarBalasConEscudos(balas, escudo1, escudo2);
	    	  potenciador.interactuarBalasConCohete(balas, Misil1, Misil2);
		      
		      //colisiones entre asteroides y sus rebotes  
		      for (int i=0;i<balls1.size();i++) {
		    	Ball2 ball1 = balls1.get(i);   
		        for (int j=0;j<balls2.size();j++) {
		          Ball2 ball2 = balls2.get(j); 
		          if (i<j) {
		        	  ball1.checkCollision(ball2);
		     
		          }
		        }
		      } 
		      
		      //Colision entre escudos ////
		      potenciador.comprobarColisionEscudos(escudo1, escudo2);
		      
		      
		    //Colision entre Cohetes   /**/
		      potenciador.comprobarColisionCohetes(Misil1, Misil2);
		     
		      
	      }
	      
	      //dibujar balas
	     for (Bullet b : balas) {       
	          b.draw(batch);
	      }
	      nave.draw(batch, this);
	      
	      if(!naveEnem.estaDestruido()) {
	    	  naveEnem.draw(batch, this);
	      }
	      //dibujar asteroides y manejar colision con nave
	      for (int i = 0; i < balls1.size(); i++) {
	    	    Ball2 b=balls1.get(i);
	    	    b.draw(batch);
		          //perdió vida o game over
	              if (nave.checkCollision(b)) {
		            //asteroide se destruye con el choque             
	            	 balls1.remove(i);
	            	 balls2.remove(i);
	            	 i--;
              }   	  
  	        }
	      
	      //nave choca con escudo  /////
	      potenciador.comprobarColisionNaveConEscudos(batch,nave, escudo1, escudo2);
	      
	    //nave choca con cohete  /**/
	      potenciador.comprobarColisionNaveConCohetes(batch,nave,Misil1,Misil2);
	      
	      
	      if (nave.estaDestruido()) {
  			if (score > game.getHighScore())
  				game.setHighScore(score);
	    	Screen ss = new PantallaGameOver(game);
  			ss.resize(1200, 800);
  			game.setScreen(ss);
  			dispose();
  		  }
	      batch.end();
	      //nivel completado
	      if (balls1.size()==0) {
			Screen ss = new PantallaJuego(game,ronda+1, nave.getVidas(), score, 
					velXAsteroides+1, velYAsteroides+1,velXEscudo,velYEscudo,cantEscudo, velXCohete, velYCohete, cantMisil, cantAsteroides+2,nave.tieneEscudoActivo(), nave.tienePotenciadorCohete());
			ss.resize(1200, 800);
			game.setScreen(ss);
			dispose();
	      }
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
