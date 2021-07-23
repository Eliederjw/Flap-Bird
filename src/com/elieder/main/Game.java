package com.elieder.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;

import com.elieder.entities.Entity;
import com.elieder.entities.Player;
import com.elieder.graficos.Spritesheet;
import com.elieder.graficos.UI;
import com.elieder.world.GroundGenerator;
import com.elieder.world.TubeGenerator;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener, MouseMotionListener {
	
	private static final long serialVersionUID = 1L;

	public static JFrame frame;
	
	private Thread thread;
	private boolean isRunning = true;
	public static final int WIDTH = 240;
	public static final int HEIGHT = 160;
	public static final int SCALE = 3;
	
	public static int gameSpeed = 1;
	
	public static int spriteSize = 16;
	private BufferedImage image;
	
	public static List<Entity> entities;
	public static Spritesheet spritesheet;	
	public static Player player;
	
	public static TubeGenerator tubeGenerator;
	public static GroundGenerator groundGenerator;
	
	public UI ui;
	
	public static int score = 0;
		
	
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
		groundGenerator = new GroundGenerator();
		ui = new UI();
		entities = new ArrayList<Entity>();	
		createPlayer();
	
	}
	
	public void createPlayer() {
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
		
		tubeGenerator.tick();
		groundGenerator.tick();
		
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.tick();
		}		
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
		
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
		
		g.dispose();
		g = bs.getDrawGraphics();			
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null );
		ui.render(g);	 					
		
		bs.show();
	}

	// KEYBOARD EVENTS	
	
	@Override
	public void keyTyped(KeyEvent e) {
			
		
	}

	@Override
	public void keyPressed(KeyEvent e) {	
		
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			player.isPressed = true;
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