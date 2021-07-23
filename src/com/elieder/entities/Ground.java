package com.elieder.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.elieder.main.Game;
import com.elieder.world.GroundGenerator;

public class Ground extends Entity{

	public Ground(int x, int y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		// TODO Auto-generated constructor stub
	}
	
	public void tick() {
		depth = 1;
		setMask(); 
		
		x-=Game.gameSpeed;		
		
		if (x+width <= 0) {
			Game.entities.remove(this);
			GroundGenerator.setGroundwidth(width);
			return;
		}
	}
	
	public void render(Graphics g) {
		g.setColor(Color.orange);
		g.fillRect(x, y, width, height);
		renderMask(this, new Color(0, 0, 255), g);
	}
	
	private void setMask() {
		maskX = x;
		maskY = y;
		maskW = width;
		maskH= height;
	}
	
}
