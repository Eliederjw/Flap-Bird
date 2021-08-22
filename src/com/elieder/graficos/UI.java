package com.elieder.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import com.elieder.main.Game;


public class UI {
	private boolean renderText = true;
	private int textFrame = 0, textMaxFrames = 30;

	public void render(Graphics g) {
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD, 18));
		g.drawString("Score: " + Game.score, 20, 20);
		
		switch (Game.gameState) {
		case Game.STARTSCREEN:
			
			String text = "<Press SPACE to Start";
			int textX = (Game.WIDTH*Game.SCALE)/2;
			int textY = (Game.HEIGHT*Game.SCALE)/2 + 50;
			
			if (renderText == true) {
				renderText("Arial", Font.BOLD, 25, text, "center", textX, textY, Color.white, g);
			}
			animateText();
			break;
			
		case Game.GAME_OVER:
			
			text = "<Press SPACE to Restart>";
			textX = (Game.WIDTH*Game.SCALE)/2;
			textY = (Game.HEIGHT*Game.SCALE)/2 + 180;
			
			if (renderText == true) {
				renderText("Arial", Font.BOLD, 25, text, "center", textX, textY, Color.white, g);
			}
			animateText();
			break;			
			}
	}
	
	private void renderText(String font, int style, int size, String text, String align, int x, int y, Color color, Graphics g) {
		int textAlignment = 0;
		g.setColor(color);
		g.setFont(new Font(font, style, size));
		
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
	
	private void animateText() {
		textFrame++;
		if(textFrame == textMaxFrames) {
			renderText = !renderText;
			textFrame = 0;
		}
	}
	
	
}
