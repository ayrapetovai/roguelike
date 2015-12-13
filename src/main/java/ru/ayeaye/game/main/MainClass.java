package ru.ayeaye.game.main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import ru.ayeaye.game.display.ImageConstants;

public class MainClass {
	
	public static void main(String[] args) throws SlickException {
		
		//TODO: ����� �������, ����� ���������� java.library.path ������������ �� ����, �������� ���:
//		File f = new File("lib/native");
//		System.setProperty("java.library.path", f.getAbsolutePath());
		
		boolean fullscrean = false;
//		boolean fullscrean = true;
		
//		int width = 640;
//		int height = 480;
		
//		int width = 1920;
//		int height = 1080;

		int width = 1360;
		int height = 768;
		
		AppGameContainer container = new AppGameContainer(new Application(), width, height, fullscrean);
		ImageConstants.cellHeightInPixels = width / 40;
		ImageConstants.cellWidthInPixels = width / 40;
		container.setTargetFrameRate(200);
		container.setMaximumLogicUpdateInterval(200);
		container.setShowFPS(true);
		container.start();
		
	}

}
