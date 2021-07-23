package com.elieder.graficos;

import java.awt.image.BufferedImage;

public class Animation {
	
	private int frames = 0, maxFrames = 0;
	private int index = 0, maxIndex;
	private BufferedImage[] sprites;
	
	public Animation(BufferedImage[] sprites, int speed) {
		this.sprites = sprites;
		this.maxFrames = speed;
		this.maxIndex = sprites.length-1;
		
		
	}
	
	public BufferedImage play() {
		frames++;
		if (frames == maxFrames) {
			index++;
			frames = 0;
			if (index == maxIndex) index = 0;
		}
		
		return sprites[index];
	}
}
