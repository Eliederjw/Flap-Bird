package com.elieder.graficos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.elieder.main.Game;

public class UI {

	public void render(Graphics g) {
		g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD, 18));
		g.drawString("Score: " + Game.score, 20, 20);
	}
	
}
