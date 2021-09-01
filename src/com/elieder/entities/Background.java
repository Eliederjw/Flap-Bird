package com.elieder.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.elieder.graficos.StaticSprite;
import com.elieder.main.Game;

public class Background {
	
	private StaticSprite sprite;
	
	public Background(String path) {
		
		sprite = new StaticSprite(path);
		
	}

	public void tick() {
		
	}
	
	public void render (Graphics g) {
		int spriteHeight = sprite.getSprite().getHeight() + Ground.groundHeight;
		g.drawImage(sprite.getSprite(), 0, Game.HEIGHT - spriteHeight, null);
	}
}
