package com.elieder.world;

import java.awt.image.BufferedImage;

import com.elieder.entities.Entity;
import com.elieder.entities.Ground;
import com.elieder.entities.Tube;
import com.elieder.graficos.StaticSprite;
import com.elieder.main.Game;

public class GroundGenerator {

	public int time = 0;
	public int targetTime = 60;
	public int tubeGap = 50;
	
	private StaticSprite staticSprite;
	private BufferedImage sprite;
	
	private double maxNumberOfTiles;
	private static int numberOfTiles = 0;
	
	private int groundPositionY;
	private int groundPositionX = 0;
	
	private int lastGroundX;
	
	public GroundGenerator(String path) {
		
		staticSprite = new StaticSprite(path);
		maxNumberOfTiles = (int) (Math.ceil((double)Game.WIDTH / (double)staticSprite.getSprite().getWidth()));
		
	}
	
	public void tick() {
		
		sprite = staticSprite.getSprite();
		
		int groundHeight = sprite.getHeight();
		int groundWidth = sprite.getWidth();
		
		if (Game.grounds.size() > 0) lastGroundX = Game.grounds.get(Game.grounds.size()-1).getX();
		else lastGroundX = 0;
		groundPositionY = Game.HEIGHT - groundHeight;
		groundPositionX = lastGroundX + groundWidth;
		
		switch (Game.gameState) {
		
		case Game.STARTSCREEN, Game.GAME:
		
			
			if (numberOfTiles <= maxNumberOfTiles) {
				Ground ground = new Ground(groundPositionX, groundPositionY, groundWidth, groundHeight, Game.gameSpeed, sprite);
				Game.grounds.add(ground);
				numberOfTiles++;
			}
		break;
		
		case Game.GAME_OVER:
			numberOfTiles = 0;
		break;
		}
		
	}
	
	public static void tileRemoved() {
		numberOfTiles--;
	}
}
