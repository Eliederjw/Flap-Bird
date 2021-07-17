package com.elieder.entities;

import java.awt.image.BufferedImage;

public class Player extends Entity{
	
	public boolean right, up, left, down;	

	public Player(int x, int y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);		

	}

	public void tick() {

		
	}

}
