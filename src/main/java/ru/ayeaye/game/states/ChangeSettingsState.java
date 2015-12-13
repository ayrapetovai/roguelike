package ru.ayeaye.game.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class ChangeSettingsState extends MenuState {

	public static final int ID = StateIdGenerator.getNextId();

	@Override
	public int getID() {
		return ID;
	}

	@Override
	public String getHeader() {
		return "Settings";
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		addMenuOption("[T]oggle fullscrean",
				() -> {
					if (container.getInput().isKeyPressed(Input.KEY_T))
						container.setFullscreen(!container.isFullscreen());
				});
		addMenuOption("[R]eturn to main menu", 
				() -> {
					if (container.getInput().isKeyPressed(Input.KEY_R))
						game.enterState(MainMenuState.ID);
				});
	}

}
