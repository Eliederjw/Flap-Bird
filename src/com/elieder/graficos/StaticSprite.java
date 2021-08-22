package com.elieder.graficos;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class StaticSprite {

	
	BufferedImage sprite;
	
	public StaticSprite(String path) {
		try {
			sprite = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public BufferedImage getSprite() {
		return sprite;
		
	}
	
	
}
