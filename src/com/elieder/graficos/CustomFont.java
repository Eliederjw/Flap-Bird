package com.elieder.graficos;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

public class CustomFont {
	
	private InputStream stream;
	private Font customFont;
	
	public CustomFont(String path) {
		stream = ClassLoader.getSystemClassLoader().getResourceAsStream(path);	
		
		try {
			customFont = Font.createFont(Font.TRUETYPE_FONT, stream);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
			
	}
	
	public Font getFont() {		
		return customFont;
	}
}
