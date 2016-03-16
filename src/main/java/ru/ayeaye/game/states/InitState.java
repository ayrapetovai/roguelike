package ru.ayeaye.game.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import ru.ayeaye.game.display.ImageConstants;

public class InitState extends MenuState {
	
	public static int ID = StateIdGenerator.getNextId();
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		game.enterState(MainMenuState.ID);
		ImageConstants.fontHeightInPixels = g.getFont().getHeight("A");
		ImageConstants.fontWidthInPixels = g.getFont().getWidth("A");
	}
	
	@Override
	public String getHeader() {
		return null;
	}

	@Override
	public int getID() {
		return ID;
	}

}
