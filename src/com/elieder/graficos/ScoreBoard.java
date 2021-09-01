package com.elieder.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.stream.Stream;

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
			Color textColor = new Color(200, 149, 82);
			Color scoreColor = new Color(250, 250, 250);
			Font font = new Font("Arial", Font.PLAIN, 9);
			Font scoreFont = new Font("Arial", Font.BOLD, 12);
			renderBoard(g);
			renderText(font, "Score", "center", x , boardCenterY + 15, textColor, g);
			renderText(font, "Best Score", "center", x, boardCenterY + 48, textColor, g);
			renderText(scoreFont, Integer.toString(Game.score), "center", x, boardCenterY + 30, scoreColor, g);
			renderText(scoreFont, Integer.toString(Game.bestScore), "center", x, boardCenterY + 63, scoreColor, g);
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
	
	private void renderText(Font font, String text, String align, int x, int y, Color color, Graphics g) {
		int textAlignment = 0;
		g.setColor(color);
		g.setFont(font);
		
		Rectangle2D stringBound = g.getFontMetrics().getStringBounds(text, g);
		
		switch (align) {
		case "center":
			textAlignment = (int) stringBound.getCenterX();
			break;
		case "left":
			textAlignment = 0;
			break;
		case "right":
			textAlignment = (int) stringBound.getWidth();
		}
		
		g.drawString(text, x - textAlignment, y);
	}

}
