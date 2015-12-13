package ru.ayeaye.game.display;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class ImageConstants {
//	public static final int cellWidthInPixels = 32;
//	public static final int cellHeightInPixels = 32;
	
	public static final int originalCellWidthInPixels = 32;
	public static final int originalCellHeightInPixels = 32;
	
	
	private static ImageConstants instance;
	public static ImageConstants getInstance() {
		if (instance == null) {
			instance = new ImageConstants();
		}
		return instance;
	}
	
	public static int cellWidthInPixels = 64;
	public static int cellHeightInPixels = 64;
	
	public Image selected;
	public SpriteSheet grassSheet;
	public Image goblin;
	public Image demon;
	public Image minorDemon;
	public Image grass;
	
	private ImageConstants() {
		try {
			selected = new Image("img/selected.png").getScaledCopy(cellWidthInPixels, cellHeightInPixels);
			grassSheet = new SpriteSheet("img/grass.png", originalCellWidthInPixels, originalCellHeightInPixels);
			grass = grassSheet.getSprite((int)(Math.random() * 12), 0).getScaledCopy(cellWidthInPixels, cellHeightInPixels);
			goblin = new Image("img/goblin.png").getScaledCopy(cellWidthInPixels, cellHeightInPixels);
			demon = new Image("img/demon.png").getScaledCopy(cellWidthInPixels, cellHeightInPixels);
			minorDemon = new Image("img/minorDemon.png").getScaledCopy(cellWidthInPixels, cellHeightInPixels);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

}
