package com.elieder.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.elieder.graficos.StaticSprite;
import com.elieder.main.Game;
import com.elieder.world.GroundGenerator;

public class Ground extends Entity{
		

	public Ground(int x, int y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		
		
	}
	
	public void tick() {
		depth = 1;
		setMask();
		
		x-=Game.gameSpeed;
			
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, x, y, null);
		
//		renderMask(g);		
	
	}
	
	public void removeGround() {
		GroundGenerator.tileRemoved();
		Game.grounds.remove(this);
	}
	
	private void renderMask(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(255, 0, 0, 127));
		g2.fillRect(maskX, maskY, width, height);
		
	}
	
	private void setMask() {
		maskX = x;
		maskY = y;
		maskW = width;
		maskH= height;
	}
	
}
