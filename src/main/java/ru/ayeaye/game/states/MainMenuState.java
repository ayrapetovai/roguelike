package ru.ayeaye.game.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class MainMenuState extends MenuState {

	public static int ID = StateIdGenerator.getNextId();

	@Override
	public int getID() {
		return ID;
	}

	@Override
	public String getHeader() {
		return "Main menu";
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		addMenuOption("[S]tart new game",
				() -> {
					if (container.getInput().isKeyPressed(Input.KEY_S))
						game.enterState(PlayState.ID);
				});
		addMenuOption("[L]oad game", 
				() -> {
					if (container.getInput().isKeyPressed(Input.KEY_L))
						game.enterState(LoadGameState.ID);
				});
		addMenuOption("[C]hange settings", 
				() -> {
					if (container.getInput().isKeyPressed(Input.KEY_C))
						game.enterState(ChangeSettingsState.ID);
				});
		addMenuOption("[E]xit to desktop", 
				() -> {
					if (container.getInput().isKeyPressed(Input.KEY_E))
						container.exit();
				});
	}

}
