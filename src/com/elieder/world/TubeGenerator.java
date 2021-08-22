package com.elieder.world;

import java.awt.image.BufferedImage;

import com.elieder.entities.Entity;
import com.elieder.entities.Tube;
import com.elieder.graficos.StaticSprite;
import com.elieder.main.Game;

public class TubeGenerator {
		
	public int time = 0;
	public int targetTime = 60;
	public int tubeGap = 50 ;
	
	
	public void tick() {
		time ++;
		
		if(time == 75) {
			// Criar tubo resetar o contador
			int upperTubeHeight = Entity.rand.nextInt(80) + 5;
			Tube upperTube = new Tube(Game.WIDTH, 0, 20, upperTubeHeight, Game.gameSpeed, null, Tube.UP);
			
			int lowerTubeHeight = Game.HEIGHT - upperTubeHeight - tubeGap;
			Tube lowerTube = new Tube(Game.WIDTH, Game.HEIGHT - lowerTubeHeight, 20, lowerTubeHeight, Game.gameSpeed, null, Tube.DOWN);
					
			Game.entities.add(upperTube);
			Game.entities.add(lowerTube);
			
			time = 0;
		}		
		
	}
}
