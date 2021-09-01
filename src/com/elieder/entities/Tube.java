package com.elieder.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.elieder.graficos.StaticSprite;
import com.elieder.main.Game;

public class Tube extends Entity {
	
	public static final int UP = 0, DOWN = 1;

	private StaticSprite staticSpriteDown;
	private StaticSprite staticSpriteUp;
	
	private int pipePosition;
	
	private boolean scored = false;
	
	public Tube(int x, int y, int width, int height, double speed, BufferedImage sprite, int position) {
		super(x, y, width, height, speed, sprite);
		
		staticSpriteDown = new StaticSprite("/PipeDown.png");
		staticSpriteUp = new StaticSprite("/PipeUp.png");
		
		pipePosition = position;
		
		
	}
	
	public void tick() {
			
		depth = 2;
		setMask();
		
		x-=Game.gameSpeed;
		
		if (x +(Game.spriteSize/2) == Game.player.x & scored == false) {
			Game.score++;
			scored = true;
			if (Game.score > Game.bestScore) {
				Game.bestScore++;
			}
		}
		
		if (x+width <= 0) {
			Game.entities.remove(this);
			return;
		}
	}
	
	public void render(Graphics g) {
		
		
	
		switch (pipePosition) {
		case UP:
			sprite = staticSpriteUp.getSprite();
			
			g.drawImage(sprite, x, y - (sprite.getHeight()) + height, null);			
			break;
		case DOWN:
			sprite = staticSpriteDown.getSprite();
			g.drawImage(sprite, x, y, null);
			break;
		}		
				
//		renderMask(g);
		
	}
	
	private void renderMask(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0, 0, 255, 125));;
		g2.fillRect(maskX, maskY, maskW, maskH);
	}
			
	private void setMask() {
		maskX = x;
		maskY = y;
		maskW = width;
		maskH= height;
	}

}
