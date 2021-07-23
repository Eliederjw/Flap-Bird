package com.elieder.world;

import com.elieder.entities.Entity;
import com.elieder.entities.Tube;
import com.elieder.main.Game;

public class TubeGenerator {
		
	public int time = 0;
	public int targetTime = 60;
	public int tubeGap = 50 ;
	
	public void tick() {
		time ++;
		
		if(time == 75) {
			// Criar tubo resetar o contador
			int upperTubeHeight = Entity.rand.nextInt(90) + 5;
			Tube upperTube = new Tube(Game.WIDTH, 0, 20, upperTubeHeight, Game.gameSpeed, null);
			
			int lowerTubeHeight = Game.HEIGHT - upperTubeHeight - tubeGap;
			Tube lowerTube = new Tube(Game.WIDTH, Game.HEIGHT - lowerTubeHeight, 20, lowerTubeHeight, Game.gameSpeed, null);
					
			Game.entities.add(upperTube);
			Game.entities.add(lowerTube);
			
			time = 0;			
		}
	}	
}
