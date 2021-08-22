package com.elieder.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.elieder.graficos.Animation;
import com.elieder.graficos.ScoreBoard;
import com.elieder.main.Game;
import com.elieder.world.World;

public class Player extends Entity{
	
	private BufferedImage[] playerSprites;
	
	private Graphics2D g2;
	
	private Animation playerAnimation;
	
	public boolean isPressed = true;
	
	private final int NORMAL = 0, HIT = 1, ONGROUND = 2;
	
	public int playerState = NORMAL;
	
	private boolean oneShot = true;
	
	private final int GRAVITY = 2;
	
	private double speedStrength = 0;
	
	private int fallingTime = -1; 
	
	private double degrees = 0;
	
	
	public Player(int x, int y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		
		playerSprites = new BufferedImage[3];
		
		for (int i = 0; i < playerSprites.length; i++) {
			playerSprites[i] = Game.spritesheet.getSprite(i*Game.spriteSize, 0, Game.spriteSize, Game.spriteSize);
		}
		
		playerAnimation = new Animation (playerSprites, 4);
		
		
		

	}

	public void tick() {
				
		setMask();
		
		switch (Game.gameState) {
		case Game.GAME:
			
			switch (playerState){
			case NORMAL:			
				
				if(isPressed && oneShot == true) {
					speed = 6;
					speedStrength = speed;
					oneShot = false;
				}else { 			
					isPressed = false;
					oneShot = true;
					
				}
				speedStrength = speedStrength * 0.95;
				
				if (speedStrength - Math.floor(speed) < 0.1) speed = Math.floor(speedStrength);
				
				y += GRAVITY-speed;
				
				if (y < 0) {
					playerState = HIT;
					return;
				}
				
				// Testar colisão
				for (int i = 0; i < Game.entities.size(); i++) {
					Entity e = Game.entities.get(i);
					if(e instanceof Tube || e instanceof Ground) {
						if (Entity.isColliding(this, e)) {
							playerState = HIT;
							return;
						}
					}
				}
				break;
				
//==========================================
				
			case HIT:
				Game.gameSpeed = 0;
				speed = -1;
				y += GRAVITY-speed;
				
				for (int i = 0; i < Game.entities.size(); i++) {
					Entity e = Game.entities.get(i);
					if(e instanceof Ground) {
						if (Entity.isColliding(this, e)) {
							playerState = ONGROUND;
							return;
						}
					}
				}
				
				break;
				
//==========================================
			case ONGROUND:	
				Game.gameState = Game.GAME_OVER;
				break;
				
			}
			break;
			//==========================================
		}
		
		
		
		
	}

	public void render(Graphics g) {
		
		depth = 0;
		
		g2 = (Graphics2D) g;
		renderMask(this, new Color(255, 0, 0), g);
		
		animate();
		
		switch (Game.gameState) {
		case Game.GAME:
			
			switch (playerState) {
			case NORMAL:
				
				
				if (speed <= 0) {
					g2.drawImage(sprite, this.getX(), this.getY(), null);
					fallingTime++;
					
				} else {
					rotate(-30);
					fallingTime = -1;
				}	
				break;
				
			case HIT, ONGROUND:
				rotate(90);
			fallingTime = -1;
			break;	
			}
			break;
			
	case Game.STARTSCREEN:
		g2.drawImage(sprite, this.getX(), this.getY(), null);
		break;	
		
	case Game.GAME_OVER:
		rotate(90);
		break;
		}
		
		
	}
	
	private void animate() {
		sprite = playerAnimation.play();
	}

	private void rotate(int d) {
		degrees = Math.toRadians(d);
		g2.rotate(degrees, this.getX() + width/2, this.getY() + height/2);
		g2.drawImage(sprite, this.getX(), this.getY(), null);
		g2.rotate(degrees*-1, this.getX() + width/2, this.getY() + height/2);
	}
	
	private void setMask() {
		maskW = 13;
		maskH = 13;
		
		// Center the mask
		maskX = x-(maskW/2) + (Game.spriteSize/2);
		maskY = y-(maskH/2) + (Game.spriteSize/2);
	}
}
