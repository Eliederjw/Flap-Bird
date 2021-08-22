package com.elieder.world;

import com.elieder.entities.Entity;
import com.elieder.entities.Ground;
import com.elieder.entities.Tube;
import com.elieder.main.Game;

public class GroundGenerator {

	public int time = 0;
	public int targetTime = 60;
	public int tubeGap = 50;
	private static int groundWidthTotal;
	
	public void tick() {
		
		int groundHeight = 10;
		int groundHPosition = Game.HEIGHT - groundHeight;
		
		switch (Game.gameState) {
		
		case Game.STARTSCREEN, Game.GAME:
			
		if (groundWidthTotal < Game.WIDTH + 75  ) {
			while (groundWidthTotal < Game.WIDTH + 75) {
				Ground ground = new Ground(groundWidthTotal, groundHPosition, 75, groundHeight, Game.gameSpeed, null);
				groundWidthTotal+=ground.getWidth();
				
				Game.entities.add(ground);
				
				time = 0;		
				
			}				
		}	
		break;
		
		case Game.GAME_OVER:
			groundWidthTotal = 0;
			
		break;
		}
		
	}
	
	public static void setGroundwidth(int i) {
		groundWidthTotal-=i;
	}
}
