package com.elieder.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.elieder.entities.Entity;
import com.elieder.main.Game;

public class ScoreBoard extends Entity{
	
	private int boardCenterX;
	private int boardCenterY;
	private StaticSprite staticSprite;

	public ScoreBoard(int x, int y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		
		staticSprite = new StaticSprite("/ScoreBoard.png");
		
	}
	
	public void tick() {
		depth = 0;
	}
	
	public void render (Graphics g) {
		
		switch (Game.gameState) {
		
		case Game.GAME_OVER:
			renderBoard(g);
			g.setColor(new Color(197, 142, 80));
			g.setFont(new Font("Arial", Font.PLAIN, 10));
			g.drawString("Score: " + Game.score, boardCenterX + 5, boardCenterY + 20);
			g.drawString("Best Score: " + Game.bestScore, boardCenterX + 5, boardCenterY + 40);
		}
	}
	
	private void renderBoard(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		centerBoard();
		g2.drawImage(staticSprite.getSprite(), boardCenterX, boardCenterY, null);
	}
	
	private void centerBoard() {
		boardCenterX = x - width/2;
		boardCenterY = y - height/2;
	}

}
