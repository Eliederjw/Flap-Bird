package com.elieder.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;

import com.elieder.entities.Entity;
import com.elieder.entities.Ground;
import com.elieder.entities.Player;
import com.elieder.graficos.CustomFont;
import com.elieder.graficos.ScoreBoard;
import com.elieder.graficos.Spritesheet;
import com.elieder.graficos.UI;
import com.elieder.world.GroundGenerator;
import com.elieder.world.TubeGenerator;
import com.elieder.world.World;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener, MouseMotionListener {
	
	private static final long serialVersionUID = 1L;

	public static JFrame frame;
	
	private Thread thread;
	private boolean isRunning = true;
	public static final int WIDTH = 240;
	public static final int HEIGHT = 160;
	public static final int SCALE = 3;
	
	public static final int INICIAL_GAME_SPEED = 1;
	public static int gameSpeed = INICIAL_GAME_SPEED;
	
	public static final int GAME = 0, STARTSCREEN = 1, GAME_OVER = 2;
	public static int gameState = GAME;
	
	public static int spriteSize = 16;
	private BufferedImage image;
	
	public static List<Entity> entities;
	public static Spritesheet spritesheet;
	public static Player player;
	
	public static ScoreBoard scoreBoard;
	
	public static TubeGenerator tubeGenerator;
	public static GroundGenerator groundGenerator;
	
	public static List<Ground> grounds;
	
	public UI ui;
	
	public static int score = 0;
	public static int bestScore = 0;
	
	public InputStream stream; 
	public static Font flappyBirdFont;
		
	
	public Game() {		
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		initFrame();
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		
//		Inicializando objetos		
		spritesheet = new Spritesheet("/Spritesheet.png");
		tubeGenerator = new TubeGenerator();
		groundGenerator = new GroundGenerator("/Ground.png");
		ui = new UI();

		entities = new ArrayList<Entity>();
		
		grounds = new ArrayList<Ground>();
		
		loadGame();		
	
		gameState = STARTSCREEN;
		
		stream = ClassLoader.getSystemClassLoader().getResourceAsStream("/tw-cen-mt-condensed-3.ttf");
		
		try {
			flappyBirdFont = Font.createFont(Font.TRUETYPE_FONT, stream);
		} catch (FontFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void loadGame() {

		createPlayer();
		scoreBoard = new ScoreBoard(WIDTH/2, HEIGHT/2, 120, 80, 0, null);
		entities.add(scoreBoard);
		gameSpeed = INICIAL_GAME_SPEED;
	}
	
	private static void createPlayer() {
		player = new Player(WIDTH/2 - 30, HEIGHT/2 - (spriteSize/2), spriteSize, spriteSize, 2, spritesheet.getSprite(0, 0, spriteSize, spriteSize));
		entities.add(player);			
	
	}
	
	public void initFrame() {
		frame = new JFrame("Flap Bird");
		frame.add(this);
		
		frame.setResizable(false);
		frame.pack();					
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
	}
		
	public static void main(String[] args) {
	 Game game = new Game();
	 game.start();
	 
	}
	
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		
		requestFocus();
		
		while(isRunning) {
			long now = System.nanoTime();
			delta+= (now - lastTime) / ns;
			lastTime = now;
			if (delta >=1) {
				tick();
				render();
				frames++;
				delta--;
			}
			
			if (System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: " +frames);
				frames = 0;
				timer+=1000;
			}
		}
		
		stop();
	}

	public void tick() {
		
		switch (gameState) {
		
		case GAME:		
		tubeGenerator.tick();
		break;
		
		}
		
		groundGenerator.tick();
		
		tickEntities();
		tickGrounds();
		
		
	}

	public void render () {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(122, 102, 255));
		g.fillRect(0, 0, WIDTH, HEIGHT);		
		
//		Render entities
		Collections.sort(entities, Entity.nodeSorter);
		
		renderEntities(g);
		renderGrounds(g);
		
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null );
		ui.render(g);
		
		bs.show();
	}
	
	private void tickEntities() {
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.tick();
		}	
	}
	
	private void tickGrounds() {		
		for (int i = 0; i < grounds.size(); i++) {
			Ground gr = grounds.get(i);
			gr.tick();
		}	
	}

	private void renderEntities(Graphics g) {
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
		
	}
	
	private void renderGrounds(Graphics g) {
		for (int i = 0; i < grounds.size(); i++) {
			Ground gr = grounds.get(i);
			gr.render(g);
		}
	}
	
	// KEYBOARD EVENTS	
	
	@Override
	public void keyTyped(KeyEvent e) {
			
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		switch (gameState) {
		case GAME:
			if (e.getKeyCode() == KeyEvent.VK_SPACE) player.isPressed = true;
			break;
			
		case STARTSCREEN:
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				gameState = GAME;
				player.isPressed = true;
			}
			break;
			
		case GAME_OVER:
			if (e.getKeyCode() == KeyEvent.VK_SPACE) World.restartGame();
			break; 
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			player.isPressed = false;
		}	
	}
		
	// MOUSE EVENTS
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override	
	public void mousePressed(MouseEvent e) {
	
	}	

	@Override
	
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseDragged(MouseEvent e) {
		
	}

	public void mouseMoved(MouseEvent e) {
		
	}

}
